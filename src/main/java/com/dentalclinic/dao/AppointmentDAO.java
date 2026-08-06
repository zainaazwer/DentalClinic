package com.dentalclinic.dao;

import com.dentalclinic.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Create Appointment
    public boolean createAppointment(Appointment appointment) throws SQLException {

        String sql = "INSERT INTO appointment "
                + "(appointmentDate, appointmentTime, treatmentType, patientId, patientName, dentistName) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDate(1, Date.valueOf(appointment.getAppointmentDate()));

            String time = appointment.getAppointmentTime();

            if (time != null && time.matches("\\d{2}:\\d{2}")) {
                time += ":00";
            }

            pstmt.setTime(2, Time.valueOf(time));

            pstmt.setString(3, appointment.getTreatmentType());
            pstmt.setInt(4, appointment.getPatientId());
            pstmt.setString(5, appointment.getPatientName());
            pstmt.setString(6, appointment.getDentistName());

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

        String sql = "SELECT * FROM appointment WHERE appointmentId = ?";

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
                appointment.setPatientName(rs.getString("patientName"));
                appointment.setDentistName(rs.getString("dentistName"));

                return appointment;
            }
        }

        return null;
    }

    // Get Appointments by Patient ID
    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointment WHERE patientId = ? ORDER BY appointmentDate";

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
                appointment.setPatientName(rs.getString("patientName"));
                appointment.setDentistName(rs.getString("dentistName"));

                appointments.add(appointment);
            }
        }

        return appointments;
    }

    // Get All Appointments
    public List<Appointment> getAllAppointments() throws SQLException {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointment ORDER BY appointmentDate, appointmentTime";

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
                appointment.setPatientName(rs.getString("patientName"));
                appointment.setDentistName(rs.getString("dentistName"));

                appointments.add(appointment);
            }
        }

        return appointments;
    }
}