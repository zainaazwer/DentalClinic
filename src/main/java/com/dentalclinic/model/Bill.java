package com.dentalclinic.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    private int billId;
    private String billDate;

    // Appointment
    private int appointmentId;

    // Patient Information
    private int patientId;
    private String patientName;
    private String patientContact;

    // Treatment Information
    private String treatmentType;

    // Financial Details
    private double treatmentCost;
    private double consultationFee;
    private double totalAmount;
    private double amountPaid;

    // Payment Information
    private String paymentMethod;
    private String paymentDate;

    public static final String PAYMENT_CASH = "CASH";
    public static final String PAYMENT_CARD = "CARD";

    public static final double DEFAULT_CONSULTATION_FEE = 50.00;

    public Bill() {
        this.billDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        this.consultationFee = DEFAULT_CONSULTATION_FEE;
        this.treatmentCost = 0.00;
        this.totalAmount = 0.00;
        this.amountPaid = 0.00;
    }

    public Bill(int appointmentId, int billId) {
        this();

        this.appointmentId = appointmentId;
        this.billId = billId;
    }

    // Business Methods
    // Calculate total bill amount
    public double calculateTotal() {
        this.totalAmount = treatmentCost + consultationFee;
        return this.totalAmount;
    }

    public double calculateBalance() {
        return totalAmount - amountPaid;
    }

    // Getters and Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

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

    public String getPatientContact() {
        return patientContact;
    }

    public void setPatientContact(String patientContact) {
        this.patientContact = patientContact;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethodDisplay() {

        if (paymentMethod == null) {
            return "";
        }

        switch (paymentMethod) {

            case PAYMENT_CASH:
                return "Cash";

            case PAYMENT_CARD:
                return "Card";

            default:
                return paymentMethod;
        }
    }

    public String getFormattedTotal() {
        return String.format("$%.2f", totalAmount);
    }

    public String getFormattedBalance() {
        return String.format("$%.2f", calculateBalance());
    }

    @Override
    public String toString() {

        return "Bill{" +
                "billId=" + billId +
                ", patientName='" + patientName + '\'' +
                ", treatmentType='" + treatmentType + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

