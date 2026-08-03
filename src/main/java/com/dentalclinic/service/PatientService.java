package com.dentalclinic.service;

import com.dentalclinic.dao.PatientDAO;
import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Appointment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientService {

    private static PatientService instance;

    private PatientDAO patientDAO;
    private AppointmentDAO appointmentDAO;


    private PatientService() {
        patientDAO = new PatientDAO();
        appointmentDAO = new AppointmentDAO();
    }


    public static synchronized PatientService getInstance() {

        if (instance == null) {
            instance = new PatientService();
        }

        return instance;
    }


    // Register patient
    public boolean registerPatient(Patient patient) throws SQLException {

        if (!validatePatient(patient)) {
            return false;
        }

        return patientDAO.createPatient(patient);
    }


    // Validate patient
    public boolean validatePatient(Patient patient) {

        if (patient == null) {
            return false;
        }

        if (patient.getFullName() == null ||
            patient.getFullName().trim().isEmpty()) {

            return false;
        }


        if (patient.getPhoneNumber() == null ||
            patient.getPhoneNumber().trim().isEmpty()) {

            return false;
        }


        return true;
    }


    // Get patient by ID
    public Patient getPatientById(int patientId) throws SQLException {

        if (patientId <= 0) {
            return null;
        }

        return patientDAO.getPatientById(patientId);
    }


    // Get all patients
    public List<Patient> getAllPatients() throws SQLException {

        return patientDAO.getAllPatients();
    }


    // Search patient by name
    public List<Patient> searchPatientsByName(String name) throws SQLException {

        List<Patient> results = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            return results;
        }


        String search = name.toLowerCase();


        for (Patient patient : patientDAO.getAllPatients()) {

            if (patient.getFullName()
                    .toLowerCase()
                    .contains(search)) {

                results.add(patient);
            }
        }

        return results;
    }


    // Search patient by phone number
    public Patient getPatientByPhoneNumber(String phoneNumber) throws SQLException {

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }


        for (Patient patient : patientDAO.getAllPatients()) {

            if (patient.getPhoneNumber()
                    .equals(phoneNumber)) {

                return patient;
            }
        }

        return null;
    }


    // Update patient
    public boolean updatePatient(Patient patient) throws SQLException {

        if (!validatePatient(patient)) {
            return false;
        }


        return patientDAO.updatePatient(patient);
    }


    // Get patient appointments
    public List<Appointment> getPatientAppointments(int patientId)
            throws SQLException {

        if (patientId <= 0) {
            return new ArrayList<>();
        }


        return appointmentDAO.getAppointmentsByPatientId(patientId);
    }


    // Get appointment count
    public int getPatientAppointmentCount(int patientId)
            throws SQLException {

        List<Appointment> appointments =
                getPatientAppointments(patientId);


        return appointments.size();
    }


    // Check patient exists
    public boolean patientExists(int patientId)
            throws SQLException {

        return getPatientById(patientId) != null;
    }


    // Total patient count
    public int getTotalPatientCount()
            throws SQLException {

        return patientDAO.getAllPatients().size();
    }


    public PatientStatistics getPatientStatistics(int patientId)
            throws SQLException {

        Patient patient = getPatientById(patientId);


        if (patient == null) {
            return null;
        }


        List<Appointment> appointments =
                getPatientAppointments(patientId);


        PatientStatistics stats = new PatientStatistics();

        stats.setPatient(patient);
        stats.setTotalAppointments(appointments.size());


        return stats;
    }



    public static class PatientStatistics {

        private Patient patient;
        private int totalAppointments;


        public Patient getPatient() {
            return patient;
        }


        public void setPatient(Patient patient) {
            this.patient = patient;
        }


        public int getTotalAppointments() {
            return totalAppointments;
        }


        public void setTotalAppointments(int totalAppointments) {
            this.totalAppointments = totalAppointments;
        }
    }
}