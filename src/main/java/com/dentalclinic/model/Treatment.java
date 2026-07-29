package com.dentalclinic.model;

import java.io.Serializable;

public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;

    private int treatmentId;
    private String treatmentName;
    private String description;
    private double treatmentCost;

    // Default Constructor
    public Treatment() {
    }

    public Treatment(int treatmentId, String treatmentName,
                     String description, double treatmentCost) {

        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.description = description;
        this.treatmentCost = treatmentCost;
    }

    // Getters and Setters
    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "treatmentId=" + treatmentId +
                ", treatmentName='" + treatmentName + '\'' +
                ", description='" + description + '\'' +
                ", treatmentCost=" + treatmentCost +
                '}';
    }
}

