package org.example.fifa_central.service;

import org.example.fifa_central.dto.*;
import org.example.fifa_central.model.Club;
import org.example.fifa_central.model.Player;
import org.example.fifa_central.model.PlayerPosition;
import org.example.fifa_central.repository.SynchronizationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SynchronizationService {
    private final RestTemplate restTemplate;
    private final SynchronizationRepository repository;

    // Récupérer l'ensemble des URLs d'API depuis les propriétés
    @Value("${external.api.urls}")
    private String externalApiUrls;

    public SynchronizationService(RestTemplate restTemplate, SynchronizationRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public void synchronizeData() {
        // Split des URLs par la virgule et itération sur chaque URL
        String[] apiUrls = externalApiUrls.split(",");
        for (String apiUrl : apiUrls) {
            synchronizeDataFromChampionship(apiUrl.trim()); // .trim() pour enlever les espaces potentiels
        }
    }

    private void synchronizeDataFromChampionship(String apiUrl) {
        try {
            SeasonDto[] seasons = restTemplate.exchange(
                    apiUrl + "/seasons",
                    HttpMethod.GET,
                    null,
                    SeasonDto[].class
            ).getBody();
            if (seasons == null) return;

            for (SeasonDto season : seasons) {
                String seasonDate = season.getYear() + "-01-01";

                PlayerDto[] players = restTemplate.exchange(
                        apiUrl + "/players",
                        HttpMethod.GET,
                        null,
                        PlayerDto[].class
                ).getBody();

                if (players != null) {
                    for (PlayerDto dto : players) {
                        String clubId = null;
                        String coachName = null;
                        String coachNationality = null;

                        if (dto.getClub() != null) {
                            clubId = dto.getClub().getId();
                            if (dto.getClub().getCoach() != null) {
                                coachName = dto.getClub().getCoach().getName();
                                coachNationality = dto.getClub().getCoach().getNationality();
                            }
                        }

                        // 🔸 Enregistrer le club
                        if (clubId != null) {
                            Club club = new Club();
                            club.setId(clubId);
                            club.setName(dto.getClub().getName());
                            club.setAcronym(dto.getClub().getAcronym());
                            club.setYearCreation(dto.getClub().getYearCreation());
                            club.setStadium(dto.getClub().getStadium());
                            /*club.setCoachName(coachName);
                            club.setCoachNationality(coachNationality); */
                            club.setChampionship(dto.getClub().getChampionship());

                            repository.saveClubData(club);
                        }

                        Player player = new Player();
                        player.setId(dto.getId());
                        player.setName(dto.getName());
                        player.setNumber(dto.getNumber());
                        player.setAge(dto.getAge());
                        player.setPosition(PlayerPosition.valueOf(dto.getPosition()));
                        player.setNationality(dto.getNationality());
                        player.setClubId(clubId);
                        repository.savePlayerData(player);

                        // Statistiques joueur
                        try {
                            PlayerStatisticsDto statsDto = restTemplate.exchange(
                                    apiUrl + "/players/" + dto.getId() + "/statistics/" + seasonDate,
                                    HttpMethod.GET,
                                    null,
                                    PlayerStatisticsDto.class
                            ).getBody();

                            if (statsDto != null) {
                                repository.savePlayerStatistics(
                                        dto.getId(),
                                        statsDto.getScoredGoals(),
                                        statsDto.getPlayingTime().getValue(),
                                        statsDto.getPlayingTime().getDurationUnit()
                                );
                            }
                        } catch (Exception ex) {
                            System.err.printf("Erreur stats joueur [%s] saison %s%n", dto.getId(), seasonDate);
                        }
                    }
                }

                try {
                    ClubStatisticsDto[] clubStats = restTemplate.exchange(
                            apiUrl + "/clubs/statistics/" + seasonDate + "?hasToBeClassified=true",
                            HttpMethod.GET,
                            null,
                            ClubStatisticsDto[].class
                    ).getBody();

                    if (clubStats != null) {
                        List<Integer> differences = Arrays.stream(clubStats)
                                .peek(repository::saveClubStatistics)
                                .map(ClubStatisticsDto::getDifferenceGoals)
                                .sorted()
                                .collect(Collectors.toList());

                        double median = computeMedian(differences);
                        repository.updateChampionshipRankings(seasonDate, median, 0);
                    }
                } catch (Exception ex) {
                    System.err.printf("Erreur stats clubs saison %s%n", seasonDate);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to synchronize from API: " + apiUrl, e);
        }
    }

    public double computeMedian(List<Integer> differences) {
        int size = differences.size();
        if (size % 2 == 0) {
            return (differences.get(size / 2 - 1) + differences.get(size / 2)) / 2.0;
        } else {
            return differences.get(size / 2);
        }
    }

}
