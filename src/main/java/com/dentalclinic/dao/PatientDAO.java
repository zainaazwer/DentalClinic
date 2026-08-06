package com.dentalclinic.dao;

import com.dentalclinic.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Create a patient
    public boolean createPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patient (fullName, address, phoneNumber) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getFullName());
            pstmt.setString(2, patient.getAddress());
            pstmt.setString(3, patient.getPhoneNumber());

            return pstmt.executeUpdate() > 0;
        }
    }

    // Get patient by ID
    public Patient getPatientById(int patientId) throws SQLException {
        String sql = "SELECT * FROM patient WHERE patientId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patientId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient();

                    patient.setPatientId(rs.getInt("patientId"));
                    patient.setFullName(rs.getString("fullName"));
                    patient.setAddress(rs.getString("address"));
                    patient.setPhoneNumber(rs.getString("phoneNumber"));

                    return patient;
                }
            }
        }

        return null;
    }

    // Get all patients
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();

        String sql = "SELECT * FROM patient ORDER BY fullName";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient patient = new Patient();

                patient.setPatientId(rs.getInt("patientId"));
                patient.setFullName(rs.getString("fullName"));
                patient.setAddress(rs.getString("address"));
                patient.setPhoneNumber(rs.getString("phoneNumber"));

                patients.add(patient);
            }
        }

        return patients;
    }

    // Update patient
    public boolean updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patient SET fullName = ?, address = ?, phoneNumber = ? WHERE patientId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getFullName());
            pstmt.setString(2, patient.getAddress());
            pstmt.setString(3, patient.getPhoneNumber());
            pstmt.setInt(4, patient.getPatientId());

            return pstmt.executeUpdate() > 0;
        }
    }
}