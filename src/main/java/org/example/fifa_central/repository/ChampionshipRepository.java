package org.example.fifa_central.repository;

import org.example.fifa_central.dto.ChampionshipRankingDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChampionshipRepository {
    private final JdbcTemplate jdbcTemplate;

    public ChampionshipRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ChampionshipRankingDto> findRankings() {
        return jdbcTemplate.query("""
            SELECT championship, difference_goals_median
            FROM championship_rankings
        """, new BeanPropertyRowMapper<>(ChampionshipRankingDto.class));
    }
}

