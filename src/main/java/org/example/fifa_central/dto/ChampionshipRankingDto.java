package org.example.fifa_central.dto;

public class ChampionshipRankingDto {
    private int rank;
    private String championship;
    private double differenceGoalsMedian;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getChampionship() {
        return championship;
    }

    public void setChampionship(String championship) {
        this.championship = championship;
    }

    public double getDifferenceGoalsMedian() {
        return differenceGoalsMedian;
    }

    public void setDifferenceGoalsMedian(double differenceGoalsMedian) {
        this.differenceGoalsMedian = differenceGoalsMedian;
    }
}

