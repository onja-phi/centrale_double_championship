package org.example.fifa_central.service;

import org.example.fifa_central.dto.PlayerRankingDto;
import org.example.fifa_central.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerRankingDto> getBestPlayers(int top) {
        try {
            return playerRepository.findBestPlayers(top);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des meilleurs joueurs: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}