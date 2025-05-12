package org.example.fifa_central.controller;

import lombok.RequiredArgsConstructor;
import org.example.fifa_central.dto.ClubRankingDto;
import org.example.fifa_central.service.ClubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bestClubs")
public class ClubController {
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<ClubRankingDto>> getBestClubs(
            @RequestParam(defaultValue = "5") int top) {
        return ResponseEntity.ok(clubService.getBestClubs(top));
    }
}
