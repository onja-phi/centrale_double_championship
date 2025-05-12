package org.example.fifa_central.repository;

import org.example.fifa_central.dto.PlayerRankingDto;
import org.example.fifa_central.dto.PlayingTimeDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRepository {
    private final JdbcTemplate jdbcTemplate;

    public PlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PlayerRankingDto> findBestPlayers(int top) {
        return jdbcTemplate.query("""
            SELECT p.id, p.name, p.number, p.position, p.nationality, p.age,
                   ps.scored_goals, ps.playing_time_value, ps.playing_time_unit,
                   c.name AS club_name
            FROM player p
            JOIN player_statistics ps ON p.id = ps.player_id
            LEFT JOIN club c ON p.club_id = c.id
            ORDER BY ps.scored_goals DESC, ps.playing_time_value DESC
            LIMIT ?
        """, (rs, rowNum) -> {
            PlayerRankingDto dto = new PlayerRankingDto();
            dto.setId(rs.getString("id"));
            dto.setName(rs.getString("name"));
            dto.setNumber(rs.getInt("number"));
            dto.setPosition(rs.getString("position"));
            dto.setNationality(rs.getString("nationality"));
            dto.setAge(rs.getInt("age"));
            dto.setScoredGoals(rs.getInt("scored_goals"));
            dto.setClubName(rs.getString("club_name"));

            Double playingTimeValue = rs.getDouble("playing_time_value");
            if (rs.wasNull()) playingTimeValue = null;

            String playingTimeUnit = rs.getString("playing_time_unit");
            PlayingTimeDto pt = new PlayingTimeDto();
            pt.setValue(playingTimeValue);
            pt.setDurationUnit(playingTimeUnit);
            dto.setPlayingTime(pt);

            return dto;
        }, top);
    }
}
