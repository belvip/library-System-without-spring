package com.library.system.model;

public class HandleDelayAndPenalties {
    private double penaltyRatePerDay;

    // Constructeur
    public HandleDelayAndPenalties(double penaltyRatePerDay) {
        this.penaltyRatePerDay = penaltyRatePerDay;
    }

    // Getter et Setter
    public double getPenaltyRatePerDay() {
        return penaltyRatePerDay;
    }

    public void setPenaltyRatePerDay(double penaltyRatePerDay) {
        this.penaltyRatePerDay = penaltyRatePerDay;
    }
}
