package org.example.fifa_central.dto;

public class PlayerStatisticsDto {
    private int scoredGoals;
    private PlayingTimeDto playingTime;

    public int getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public PlayingTimeDto getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(PlayingTimeDto playingTime) {
        this.playingTime = playingTime;
    }
}