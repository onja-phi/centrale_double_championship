package org.example.fifa_central.service;

import org.example.fifa_central.model.DurationUnit;
import org.example.fifa_central.model.PlayerRanking;
import org.example.fifa_central.repository.PlayerRankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerRankingService {

    private final PlayerRankingRepository playerRankingRepository;

    @Autowired
    public PlayerRankingService(PlayerRankingRepository playerRankingRepository) {
        this.playerRankingRepository = playerRankingRepository;
    }

    public List<PlayerRanking> getBestPlayers(int top, DurationUnit unit) {
        return playerRankingRepository.getBestPlayers(top, unit);
    }
}


