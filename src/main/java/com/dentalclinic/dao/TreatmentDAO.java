package com.dentalclinic.dao;

import com.dentalclinic.model.Treatment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {

    // Get all treatments
    public List<Treatment> getAllTreatments() throws SQLException {

        List<Treatment> treatments = new ArrayList<>();

        String sql = "SELECT * FROM Treatment ORDER BY treatmentName";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Treatment treatment = new Treatment();

                treatment.setTreatmentId(rs.getInt("treatmentId"));
                treatment.setTreatmentName(rs.getString("treatmentName"));
                treatment.setDescription(rs.getString("description"));
                treatment.setTreatmentCost(rs.getDouble("treatmentCost"));

                treatments.add(treatment);
            }
        }

        return treatments;
    }

    // Get treatment by ID
    public Treatment getTreatmentById(int treatmentId) throws SQLException {

        String sql = "SELECT * FROM Treatment WHERE treatmentId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, treatmentId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Treatment treatment = new Treatment();

                treatment.setTreatmentId(rs.getInt("treatmentId"));
                treatment.setTreatmentName(rs.getString("treatmentName"));
                treatment.setDescription(rs.getString("description"));
                treatment.setTreatmentCost(rs.getDouble("treatmentCost"));

                return treatment;
            }
        }

        return null;
    }

    // Get treatment by name
    public Treatment getTreatmentByName(String treatmentName) throws SQLException {

        String sql = "SELECT * FROM Treatment WHERE treatmentName = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treatmentName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Treatment treatment = new Treatment();

                treatment.setTreatmentId(rs.getInt("treatmentId"));
                treatment.setTreatmentName(rs.getString("treatmentName"));
                treatment.setDescription(rs.getString("description"));
                treatment.setTreatmentCost(rs.getDouble("treatmentCost"));

                return treatment;
            }
        }

        return null;
    }

    // Add a new treatment
    public boolean createTreatment(Treatment treatment) throws SQLException {

        String sql = "INSERT INTO Treatment (treatmentName, description, treatmentCost) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treatment.getTreatmentName());
            pstmt.setString(2, treatment.getDescription());
            pstmt.setDouble(3, treatment.getTreatmentCost());

            return pstmt.executeUpdate() > 0;
        }
    }

    // Update treatment
    public boolean updateTreatment(Treatment treatment) throws SQLException {

        String sql = "UPDATE Treatment SET treatmentName = ?, description = ?, treatmentCost = ? WHERE treatmentId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, treatment.getTreatmentName());
            pstmt.setString(2, treatment.getDescription());
            pstmt.setDouble(3, treatment.getTreatmentCost());
            pstmt.setInt(4, treatment.getTreatmentId());

            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete treatment
    public boolean deleteTreatment(int treatmentId) throws SQLException {

        String sql = "DELETE FROM Treatment WHERE treatmentId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, treatmentId);

            return pstmt.executeUpdate() > 0;
        }
    }
}