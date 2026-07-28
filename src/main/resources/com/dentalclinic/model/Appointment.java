package com.dentalclinic.model;

import java.io.Serializable;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int appointmentId;
    private int patientId;
    private String patientName;
    private String dentistName;
    private String treatmentType;
    private String appointmentDate;
    private String appointmentTime;

    public Appointment() {}

    // Getters and Setters
    public int getAppointmentId() {
    	return appointmentId; 
    }
    
    public void setAppointmentId(int appointmentId) {
    	this.appointmentId = appointmentId;
    }
    
    public int getPatientId() {
    	return patientId;
    }
    
    public void setPatientId(int patientId) {
    	this.patientId = patientId;
    }
    
    public String getPatientName() {
    	return patientName;
    }
    
    public void setPatientName(String patientName) {
    	this.patientName = patientName;
    }
    
    public String getDentistName() {
    	return dentistName;
    }
    
    public void setDentistName(String dentistName) {
    	this.dentistName = dentistName;
    }
    
    public String getTreatmentType() {
    	return treatmentType;
    }
    
    public void setTreatmentType(String treatmentType) {
    	this.treatmentType = treatmentType;
    }
    
    public String getAppointmentDate() {
    	return appointmentDate;
    }
    
    public void setAppointmentDate(String appointmentDate) {
    	this.appointmentDate = appointmentDate;
    }
    
    public String getAppointmentTime() {
    	return appointmentTime;
    }
    
    public void setAppointmentTime(String appointmentTime) {
    	this.appointmentTime = appointmentTime;
    }
    
}