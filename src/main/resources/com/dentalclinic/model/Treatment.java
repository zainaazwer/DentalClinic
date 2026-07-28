package com.dentalclinic.model;

import java.io.Serializable;
import java.math.double;

public class Treatment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int treatmentId;
    private String treatmentName;
    private String description;
    private double treatmentCost;
    private double consultationFee;

    public Treatment() {}

    public Treatment(int treatmentId, String treatmentName, String description, double treatmentCost, double consultationFee) {
        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.description = description;
        this.treatmentCost = treatmentCost;
        this.consultationFee = consultationFee;
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
    
    public double getConsultationFee() {
    	return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
    	this.consultationFee = consultationFee;
    }
    
    public double getTotalCost() {
        return treatmentCost != null && consultationFee != null ? 
               treatmentCost.add(consultationFee) : double.ZERO;
    }
}