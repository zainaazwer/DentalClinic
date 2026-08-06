package com.dentalclinic.dao;

import com.dentalclinic.model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    // Create Bill
    public boolean createBill(Bill bill) throws SQLException {

        String sql =
                "INSERT INTO bill "
                + "(billDate, appointmentId, patientId, patientName, "
                + "patientContact, treatmentType, treatmentCost, "
                + "consultationFee, totalAmount, amountPaid, paymentMethod) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn =
                    DatabaseConnection.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS)) {

            // Bill date
            if(bill.getBillDate() != null
                    && !bill.getBillDate().isEmpty()){

                pstmt.setDate(
                        1,
                        Date.valueOf(
                                bill.getBillDate())
                );

            } else {

                pstmt.setDate(
                        1,
                        new java.sql.Date(
                                System.currentTimeMillis())
                );

            }

            pstmt.setInt(
                    2,
                    bill.getAppointmentId()
            );

            pstmt.setInt(
                    3,
                    bill.getPatientId()
            );

            pstmt.setString(
                    4,
                    bill.getPatientName()
            );

            pstmt.setString(
                    5,
                    bill.getPatientContact()
            );

            pstmt.setString(
                    6,
                    bill.getTreatmentType()
            );

            pstmt.setDouble(
                    7,
                    bill.getTreatmentCost()
            );

            pstmt.setDouble(
                    8,
                    bill.getConsultationFee()
            );

            pstmt.setDouble(
                    9,
                    bill.getTotalAmount()
            );

            pstmt.setDouble(
                    10,
                    bill.getAmountPaid()
            );

            pstmt.setString(
                    11,
                    bill.getPaymentMethod()
            );

            int rows =
                    pstmt.executeUpdate();

            if(rows > 0){

                ResultSet rs =
                        pstmt.getGeneratedKeys();

                if(rs.next()){

                    bill.setBillId(
                            rs.getInt(1)
                    );

                }

                return true;

            }

        }

        return false;
    }

    // Get Bill By ID
    public Bill getBillById(int billId)
            throws SQLException {

        String sql =
                "SELECT * FROM bill WHERE billId=?";

        try(Connection conn =
                    DatabaseConnection.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql)){

            pstmt.setInt(1,billId);

            ResultSet rs =
                    pstmt.executeQuery();

            if(rs.next()){

                return extractBill(rs);

            }

        }

        return null;

    }

    // Get All Bills
    public List<Bill> getAllBills()
            throws SQLException {

        List<Bill> bills =
                new ArrayList<>();

        String sql =
                "SELECT * FROM bill ORDER BY billDate DESC";

        try(Connection conn =
                    DatabaseConnection.getConnection();

            Statement stmt =
                    conn.createStatement();

            ResultSet rs =
                    stmt.executeQuery(sql)){

            while(rs.next()){

                bills.add(
                        extractBill(rs)
                );

            }

        }

        return bills;
    }

    private Bill extractBill(ResultSet rs)
            throws SQLException {

        Bill bill =
                new Bill();

        bill.setBillId(
                rs.getInt("billId")
        );

        bill.setBillDate(
                rs.getDate("billDate").toString()
        );

        bill.setAppointmentId(
                rs.getInt("appointmentId")
        );

        bill.setPatientId(
                rs.getInt("patientId")
        );

        bill.setPatientName(
                rs.getString("patientName")
        );

        bill.setPatientContact(
                rs.getString("patientContact")
        );

        bill.setTreatmentType(
                rs.getString("treatmentType")
        );

        bill.setTreatmentCost(
                rs.getDouble("treatmentCost")
        );

        bill.setConsultationFee(
                rs.getDouble("consultationFee")
        );

        bill.setTotalAmount(
                rs.getDouble("totalAmount")
        );

        bill.setAmountPaid(
                rs.getDouble("amountPaid")
        );

        bill.setPaymentMethod(
                rs.getString("paymentMethod")
        );

        return bill;
    }
    
 // Update Payment Details
    public boolean updatePayment(int billId,
                                 double amountPaid,
                                 String paymentMethod)
            throws SQLException {

        String sql = "UPDATE bill "
                   + "SET amountPaid = ?, paymentMethod = ? "
                   + "WHERE billId = ?";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setDouble(1, amountPaid);

            pstmt.setString(2, paymentMethod);

            pstmt.setInt(3, billId);


            int rows = pstmt.executeUpdate();


            return rows > 0;

        }
    }
}