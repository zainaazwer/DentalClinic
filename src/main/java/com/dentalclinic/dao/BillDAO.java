package com.dentalclinic.dao;

import com.dentalclinic.model.Bill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    // Create Bill
    public boolean createBill(Bill bill) throws SQLException {

        String sql = "INSERT INTO Bill "
                + "(billDate, appointmentId, patientId, patientName, patientNumber, patientContact, "
                + "treatmentType, treatmentDescription, treatmentCost, consultationFee, "
                + "totalAmount, amountPaid, paymentMethod, paymentDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDate(1, Date.valueOf(bill.getBillDate()));
            pstmt.setInt(2, bill.getAppointmentId());
            pstmt.setInt(3, bill.getPatientId());
            pstmt.setString(4, bill.getPatientName());
            pstmt.setString(5, bill.getPatientNumber());
            pstmt.setString(6, bill.getPatientContact());
            pstmt.setString(7, bill.getTreatmentType());
            pstmt.setString(8, bill.getTreatmentDescription());
            pstmt.setDouble(9, bill.getTreatmentCost());
            pstmt.setDouble(10, bill.getConsultationFee());
            pstmt.setDouble(11, bill.getTotalAmount());
            pstmt.setDouble(12, bill.getAmountPaid());
            pstmt.setString(13, bill.getPaymentMethod());
            pstmt.setDate(14, Date.valueOf(bill.getPaymentDate()));

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    bill.setBillId(rs.getInt(1));
                }

                return true;
            }
        }

        return false;
    }


    // Get Bill by ID
    public Bill getBillById(int billId) throws SQLException {

        String sql = "SELECT * FROM Bill WHERE billId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, billId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractBill(rs);
            }
        }

        return null;
    }


    // Get all Bills
    public List<Bill> getAllBills() throws SQLException {

        List<Bill> bills = new ArrayList<>();

        String sql = "SELECT * FROM Bill ORDER BY billDate DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bills.add(extractBill(rs));
            }
        }

        return bills;
    }


    // Update Payment Details
    public boolean updatePayment(int billId, double amountPaid, String paymentMethod) throws SQLException {

        String sql = "UPDATE Bill SET amountPaid = ?, paymentMethod = ?, paymentDate = CURRENT_DATE "
                + "WHERE billId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amountPaid);
            pstmt.setString(2, paymentMethod);
            pstmt.setInt(3, billId);

            return pstmt.executeUpdate() > 0;
        }
    }


    private Bill extractBill(ResultSet rs) throws SQLException {

        Bill bill = new Bill();

        bill.setBillId(rs.getInt("billId"));
        bill.setBillDate(rs.getDate("billDate").toString());
        bill.setAppointmentId(rs.getInt("appointmentId"));
        bill.setPatientId(rs.getInt("patientId"));

        bill.setPatientName(rs.getString("patientName"));
        bill.setPatientNumber(rs.getString("patientNumber"));
        bill.setPatientContact(rs.getString("patientContact"));

        bill.setTreatmentType(rs.getString("treatmentType"));
        bill.setTreatmentDescription(rs.getString("treatmentDescription"));
        bill.setTreatmentCost(rs.getDouble("treatmentCost"));

        bill.setConsultationFee(rs.getDouble("consultationFee"));
        bill.setTotalAmount(rs.getDouble("totalAmount"));

        bill.setAmountPaid(rs.getDouble("amountPaid"));
        bill.setPaymentMethod(rs.getString("paymentMethod"));
        bill.setPaymentDate(rs.getDate("paymentDate").toString());

        return bill;
    }
}