package org.example.fifa_central.controller;

import org.example.fifa_central.dto.ChampionshipRankingDto;
import org.example.fifa_central.service.ChampionshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/championshipRankings")
public class ChampionshipController {
    private final ChampionshipService championshipService;

    public ChampionshipController(ChampionshipService championshipService) {
        this.championshipService = championshipService;
    }

    @GetMapping
    public ResponseEntity<List<ChampionshipRankingDto>> getChampionshipRankings() {
        return ResponseEntity.ok(championshipService.getRankings());
    }
}
