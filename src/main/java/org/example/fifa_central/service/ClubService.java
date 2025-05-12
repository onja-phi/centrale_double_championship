package org.example.fifa_central.service;

import org.example.fifa_central.dto.ClubRankingDto;
import org.example.fifa_central.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public List<ClubRankingDto> getBestClubs(int top) {
        List<ClubRankingDto> clubs = clubRepository.findBestClubs(top);
        int rank = 1;
        for (ClubRankingDto c : clubs) {
            c.setRank(rank++);
        }
        return clubs;
    }
}

