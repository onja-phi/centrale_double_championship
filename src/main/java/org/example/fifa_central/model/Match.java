package org.example.fifa_central.model;

import java.time.LocalDateTime;

public class Match {
    private String id;
    private String homeClubId;
    private String awayClubId;
    private int homeGoals;
    private int awayGoals;
    private LocalDateTime date;

    public Match(String id, String homeClubId, String awayClubId, int homeGoals, int awayGoals, LocalDateTime date) {
        this.id = id;
        this.homeClubId = homeClubId;
        this.awayClubId = awayClubId;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeClubId() {
        return homeClubId;
    }

    public void setHomeClubId(String homeClubId) {
        this.homeClubId = homeClubId;
    }

    public String getAwayClubId() {
        return awayClubId;
    }

    public void setAwayClubId(String awayClubId) {
        this.awayClubId = awayClubId;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

