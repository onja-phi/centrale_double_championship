package org.example.fifa_central.model;

public class Coach {
    private String id;
    private String name;
    private String nationality;

    public Coach(String id, String name, String nationality) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
    }

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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Coach() {
    }

    public Coach(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }
}
