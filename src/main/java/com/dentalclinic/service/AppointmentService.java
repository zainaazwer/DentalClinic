package com.dentalclinic.service;

import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.dao.PatientDAO;
import com.dentalclinic.dao.TreatmentDAO;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Treatment;

import java.sql.SQLException;
import java.util.List;

public class AppointmentService {

    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private TreatmentDAO treatmentDAO;


    public AppointmentService() {
        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();
        treatmentDAO = new TreatmentDAO();
    }


    // Register Appointment
    public boolean registerAppointment(Appointment appointment) throws SQLException {

        if (appointment == null) {
            return false;
        }

        if (appointment.getPatientId() <= 0 ||
            appointment.getAppointmentDate() == null ||
            appointment.getAppointmentTime() == null ||
            appointment.getTreatmentType() == null ||
            appointment.getTreatmentType().trim().isEmpty()) {

            return false;
        }

        return appointmentDAO.createAppointment(appointment);
    }


    // Get appointment by ID
    public Appointment getAppointmentById(int appointmentId) throws SQLException {

        if (appointmentId <= 0) {
            return null;
        }

        return appointmentDAO.getAppointmentById(appointmentId);
    }


    // Get appointments by patient
    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {

        if (patientId <= 0) {
            return null;
        }

        return appointmentDAO.getAppointmentsByPatientId(patientId);
    }


    // Get all appointments
    public List<Appointment> getAllAppointments() throws SQLException {

        return appointmentDAO.getAllAppointments();
    }


    // Get patient details
    public Patient getPatientById(int patientId) throws SQLException {

        if (patientId <= 0) {
            return null;
        }

        return patientDAO.getPatientById(patientId);
    }


    // Create patient
    public boolean createPatient(Patient patient) throws SQLException {

        if (patient == null ||
            patient.getFullName() == null ||
            patient.getFullName().trim().isEmpty()) {

            return false;
        }

        return patientDAO.createPatient(patient);
    }


    // Get all treatments
    public List<Treatment> getAllTreatments() throws SQLException {

        return treatmentDAO.getAllTreatments();
    }


    // Get treatment by ID
    public Treatment getTreatmentById(int treatmentId) throws SQLException {

        if (treatmentId <= 0) {
            return null;
        }

        return treatmentDAO.getTreatmentById(treatmentId);
    }
}