package org.example.fifa_central.dto;

import lombok.Data;

@Data
public class PlayingTimeDto {
    private Double value;
    private String durationUnit;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }
}

