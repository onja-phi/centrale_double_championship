package org.example.fifa_central.controller;

import org.example.fifa_central.dto.PlayerRankingDto;
import org.example.fifa_central.service.PlayerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bestPlayers")
public class PlayerController {
    private final PlayerService playerService;

    @Value("${api.key:default-key}")
    private String expectedApiKey;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerRankingDto>> getBestPlayers(
            @RequestParam(defaultValue = "5") int top,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {

        if (!"default-key".equals(expectedApiKey) && !expectedApiKey.equals(apiKey)) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(playerService.getBestPlayers(top));
    }
}