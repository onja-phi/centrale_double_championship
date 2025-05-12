package org.example.fifa_central.repository;

import org.example.fifa_central.model.DurationUnit;
import org.example.fifa_central.model.PlayerRanking;
import org.example.fifa_central.model.PlayingTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRankingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlayerRankingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PlayerRanking> getBestPlayers(int top, DurationUnit unit) {
        String query =
                "SELECT p.id, p.name, p.number, p.position, p.nationality, p.age, " +
                        "SUM(ps.scored_goals) AS scored_goals, " +
                        "SUM(ps.playing_time_value) AS total_playing_time " +
                        "FROM player p " +
                        "JOIN player_statistics ps ON p.id = ps.player_id " +
                        "GROUP BY p.id, p.name, p.number, p.position, p.nationality, p.age " +
                        "ORDER BY scored_goals DESC, total_playing_time DESC " +
                        "LIMIT ?";


        return jdbcTemplate.query(query, (rs, rowNum) -> {
            PlayerRanking playerRanking = new PlayerRanking();
            playerRanking.setId(rs.getString("id"));
            playerRanking.setName(rs.getString("name"));
            playerRanking.setNumber(rs.getInt("number"));
            playerRanking.setPosition(rs.getString("position"));
            playerRanking.setNationality(rs.getString("nationality"));
            playerRanking.setAge(rs.getInt("age"));
            playerRanking.setScoredGoals(rs.getInt("scored_goals"));

            PlayingTime playingTime = new PlayingTime(rs.getInt("total_playing_time"), unit);
            playerRanking.setPlayingTime(playingTime);

            return playerRanking;
        }, top);
    }

}
