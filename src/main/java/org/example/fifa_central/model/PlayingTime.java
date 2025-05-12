package org.example.fifa_central.model;

public class PlayingTime {
    private int value;
    private DurationUnit unit;

    public PlayingTime(int value, DurationUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DurationUnit getUnit() {
        return unit;
    }

    public void setUnit(DurationUnit unit) {
        this.unit = unit;
    }
}


