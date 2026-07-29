package com.dentalclinic.dao;

import com.dentalclinic.model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Create Appointment
    public boolean createAppointment(Appointment appointment) throws SQLException {

        String sql = "INSERT INTO Appointment (appointmentDate, appointmentTime, treatmentType, patientId) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDate(1, Date.valueOf(appointment.getAppointmentDate()));
            pstmt.setTime(2, Time.valueOf(appointment.getAppointmentTime()));
            pstmt.setString(3, appointment.getTreatmentType());
            pstmt.setInt(4, appointment.getPatientId());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    appointment.setAppointmentId(rs.getInt(1));
                }
                return true;
            }
        }

        return false;
    }

    // Get Appointment by ID
    public Appointment getAppointmentById(int appointmentId) throws SQLException {

        String sql = "SELECT * FROM Appointment WHERE appointmentId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointmentId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Appointment appointment = new Appointment();

                appointment.setAppointmentId(rs.getInt("appointmentId"));
                appointment.setAppointmentDate(rs.getDate("appointmentDate").toString());
                appointment.setAppointmentTime(rs.getTime("appointmentTime").toString());
                appointment.setTreatmentType(rs.getString("treatmentType"));
                appointment.setPatientId(rs.getInt("patientId"));

                return appointment;
            }
        }

        return null;
    }

    // Get Appointments for a Patient
    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM Appointment WHERE patientId = ? ORDER BY appointmentDate";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patientId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Appointment appointment = new Appointment();

                appointment.setAppointmentId(rs.getInt("appointmentId"));
                appointment.setAppointmentDate(rs.getDate("appointmentDate").toString());
                appointment.setAppointmentTime(rs.getTime("appointmentTime").toString());
                appointment.setTreatmentType(rs.getString("treatmentType"));
                appointment.setPatientId(rs.getInt("patientId"));

                appointments.add(appointment);
            }
        }

        return appointments;
    }

    // Get All Appointments
    public List<Appointment> getAllAppointments() throws SQLException {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM Appointment ORDER BY appointmentDate";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Appointment appointment = new Appointment();

                appointment.setAppointmentId(rs.getInt("appointmentId"));
                appointment.setAppointmentDate(rs.getDate("appointmentDate").toString());
                appointment.setAppointmentTime(rs.getTime("appointmentTime").toString());
                appointment.setTreatmentType(rs.getString("treatmentType"));
                appointment.setPatientId(rs.getInt("patientId"));

                appointments.add(appointment);
            }
        }

        return appointments;
    }
}