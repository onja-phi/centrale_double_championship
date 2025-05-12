package org.example.fifa_central.repository;

import org.example.fifa_central.dto.ClubRankingDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClubRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClubRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ClubRankingDto> findBestClubs(int top) {
        return jdbcTemplate.query("""
            SELECT c.id, c.name, c.acronym, c.stadium, cs.ranking_points,
                   cs.scored_goals, cs.conceded_goals, cs.difference_goals,
                   cs.clean_sheet_number
            FROM club c
            JOIN club_statistics cs ON c.id = cs.club_id
            ORDER BY cs.ranking_points DESC,
                     cs.difference_goals DESC,
                     cs.clean_sheet_number DESC
            LIMIT ?
        """, new BeanPropertyRowMapper<>(ClubRankingDto.class), top);
    }
}


