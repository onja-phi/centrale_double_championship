package org.example.fifa_central.model;

public class PlayerRanking {
    private String id;
    private String name;
    private int number;
    private String position;
    private String nationality;
    private int age;
    private int scoredGoals;
    private PlayingTime playingTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public PlayingTime getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(PlayingTime playingTime) {
        this.playingTime = playingTime;
    }
}

