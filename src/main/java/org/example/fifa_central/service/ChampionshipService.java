package org.example.fifa_central.service;

import org.example.fifa_central.dto.ChampionshipRankingDto;
import org.example.fifa_central.repository.ChampionshipRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;

    public ChampionshipService(ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
    }

    public List<ChampionshipRankingDto> getRankings() {
        List<ChampionshipRankingDto> rankings = championshipRepository.findRankings();
        rankings.sort(Comparator.comparingDouble(ChampionshipRankingDto::getDifferenceGoalsMedian));
        int rank = 1;
        for (ChampionshipRankingDto dto : rankings) {
            dto.setRank(rank++);
        }
        return rankings;
    }
}

