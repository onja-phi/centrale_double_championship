package org.example.fifa_central.repository;

import org.example.fifa_central.dto.ClubStatisticsDto;
import org.example.fifa_central.model.Coach;
import org.example.fifa_central.model.Player;
import org.example.fifa_central.model.Club;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class SynchronizationRepository {
    private final JdbcTemplate jdbcTemplate;

    public SynchronizationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveClubData(Club club) {


        String championshipString = (club.getChampionship() != null)
                ? club.getChampionship().name()
                : "PREMIER_LEAGUE";

        jdbcTemplate.update("""
        INSERT INTO club (id, name, acronym, year_creation, stadium, coach_id, championship)
        VALUES (?, ?, ?, ?, ?, ?, ?::championship)
        ON CONFLICT (id) DO UPDATE SET
            name = EXCLUDED.name,
            acronym = EXCLUDED.acronym,
            year_creation = EXCLUDED.year_creation,
            stadium = EXCLUDED.stadium,
            coach_id = EXCLUDED.coach_id,
            championship = EXCLUDED.championship
    """,
                club.getId(),
                club.getName(),
                club.getAcronym(),
                club.getYearCreation(),
                club.getStadium(),
                club.getCoachId(),
                championshipString
        );
    }

    public void savePlayerData(Player player) {
        jdbcTemplate.update("""
    INSERT INTO player (id, name, number, position, nationality, age, club_id)
    VALUES (?, ?, ?, CAST(? AS player_position), ?, ?, ?)
    ON CONFLICT (id) DO UPDATE SET
        name = EXCLUDED.name,
        number = EXCLUDED.number,
        position = EXCLUDED.position,
        nationality = EXCLUDED.nationality,
        age = EXCLUDED.age,
        club_id = EXCLUDED.club_id
""",
                player.getId(),
                player.getName(),
                player.getNumber(),
                player.getPosition().toString(),
                player.getNationality(),
                player.getAge(),
                player.getClubId()
        );
    }


    public void updateChampionshipRankings(String championship, Double differenceGoalsMedian, Integer rank) {
        jdbcTemplate.update("""
            INSERT INTO championship_rankings (championship, difference_goals_median, rank)
            VALUES (CAST(? AS championship), ?, ?)
            ON CONFLICT (championship) DO UPDATE SET
            difference_goals_median = EXCLUDED.difference_goals_median,
            rank = EXCLUDED.rank
            """,
                championship,
                differenceGoalsMedian,
                rank
        );
    }

    public void savePlayerStatistics(String playerId, int goals, double timeValue, String timeUnit) {
        try {
            int currentSeason = 2024;

            Integer existingId = null;
            try {
                existingId = jdbcTemplate.queryForObject(
                        "SELECT id FROM player_statistics WHERE player_id = ? AND season_year = ?",
                        Integer.class, playerId, currentSeason);
            } catch (Exception e) {
                System.out.println();
            }

            if (existingId != null) {
                jdbcTemplate.update("""
                UPDATE player_statistics 
                SET scored_goals = ?, playing_time_value = ?, playing_time_unit = ?
                WHERE id = ?
            """, goals, timeValue, timeUnit, existingId);
            } else {
                jdbcTemplate.update("""
                INSERT INTO player_statistics (player_id, season_year, scored_goals, playing_time_value, playing_time_unit)
                VALUES (?, ?, ?, ?, ?)
            """, playerId, currentSeason, goals, timeValue, timeUnit);
            }
        } catch (Exception e) {
            System.err.println("Erreur stats joueur [" + playerId + "] saison 2024: " + e.getMessage());
        }
    }

    public void saveClubStatistics(ClubStatisticsDto stat) {
        jdbcTemplate.update("""
    INSERT INTO club_statistics (club_id, ranking_points, scored_goals, conceded_goals, difference_goals, clean_sheet_number)
    VALUES (?, ?, ?, ?, ?, ?)
    ON CONFLICT (club_id) DO UPDATE SET
        ranking_points = EXCLUDED.ranking_points,
        scored_goals = EXCLUDED.scored_goals,
        conceded_goals = EXCLUDED.conceded_goals,
        difference_goals = EXCLUDED.difference_goals,
        clean_sheet_number = EXCLUDED.clean_sheet_number
    """,
                stat.getId(),
                stat.getRankingPoints(),
                stat.getScoredGoals(),
                stat.getConcededGoals(),
                stat.getDifferenceGoals(),
                stat.getCleanSheetNumber()
        );
    }
}