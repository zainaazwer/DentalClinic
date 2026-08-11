package com.dentalclinic.daoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dentalclinic.dao.BillDAO;
import com.dentalclinic.dao.DatabaseConnection;
import com.dentalclinic.model.Bill;

public class billDAOTest {

    private BillDAO billDAO;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    private Bill bill;

    @BeforeEach
    void setUp() {

        billDAO = new BillDAO();

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        statement = mock(Statement.class);
        resultSet = mock(ResultSet.class);

        bill = new Bill();

        bill.setBillId(1);
        bill.setBillDate("2026-08-11");
        bill.setAppointmentId(10);
        bill.setPatientId(5);
        bill.setPatientName("Jenny Perera");
        bill.setPatientContact("0771234567");
        bill.setTreatmentType("Dental Cleaning");
        bill.setTreatmentCost(100.00);
        bill.setConsultationFee(50.00);
        bill.setTotalAmount(150.00);
        bill.setAmountPaid(100.00);
        bill.setPaymentMethod(Bill.PAYMENT_CASH);
    }

    // CREATE BILL - SUCCESS
    @Test
    void testCreateBillSuccess() throws Exception {

        when(connection.prepareStatement(
                anyString(),
                eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);

        when(preparedStatement.getGeneratedKeys())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt(1))
                .thenReturn(25);

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result = billDAO.createBill(bill);

            assertTrue(result);

            assertEquals(25, bill.getBillId());

            verify(preparedStatement)
                    .setDate(
                            eq(1),
                            eq(Date.valueOf("2026-08-11"))
                    );

            verify(preparedStatement)
                    .setInt(2, 10);

            verify(preparedStatement)
                    .setInt(3, 5);

            verify(preparedStatement)
                    .setString(4, "Jenny Perera");

            verify(preparedStatement)
                    .setString(5, "0771234567");

            verify(preparedStatement)
                    .setString(6, "Dental Cleaning");

            verify(preparedStatement)
                    .setDouble(7, 100.00);

            verify(preparedStatement)
                    .setDouble(8, 50.00);

            verify(preparedStatement)
                    .setDouble(9, 150.00);

            verify(preparedStatement)
                    .setDouble(10, 100.00);

            verify(preparedStatement)
                    .setString(11, Bill.PAYMENT_CASH);

            verify(preparedStatement)
                    .executeUpdate();

            verify(preparedStatement)
                    .getGeneratedKeys();
        }
    }

    // CREATE BILL - FAILURE
    @Test
    void testCreateBillFailure() throws Exception {

        when(connection.prepareStatement(
                anyString(),
                eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(0);

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result = billDAO.createBill(bill);

            assertFalse(result);

            verify(preparedStatement)
                    .executeUpdate();

            verify(preparedStatement, never())
                    .getGeneratedKeys();
        }
    }

 
    // CREATE BILL - DATABASE ERROR
    @Test
    void testCreateBillSQLException() throws Exception {

        when(connection.prepareStatement(
                anyString(),
                eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenThrow(
                        new SQLException("Database error")
                );

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            assertThrows(
                    SQLException.class,
                    () -> billDAO.createBill(bill)
            );
        }
    }

    // GET BILL BY ID - SUCCESS
    @Test
    void testGetBillByIdSuccess() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        mockBillResultSet();

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            Bill result =
                    billDAO.getBillById(1);

            assertNotNull(result);

            assertEquals(1, result.getBillId());
            assertEquals(10, result.getAppointmentId());
            assertEquals(5, result.getPatientId());
            assertEquals("Jenny Perera", result.getPatientName());
            assertEquals("0771234567", result.getPatientContact());
            assertEquals(
                    "Dental Cleaning",
                    result.getTreatmentType()
            );

            assertEquals(
                    100.00,
                    result.getTreatmentCost(),
                    0.001
            );

            assertEquals(
                    50.00,
                    result.getConsultationFee(),
                    0.001
            );

            assertEquals(
                    150.00,
                    result.getTotalAmount(),
                    0.001
            );

            assertEquals(
                    100.00,
                    result.getAmountPaid(),
                    0.001
            );

            assertEquals(
                    Bill.PAYMENT_CASH,
                    result.getPaymentMethod()
            );

            verify(preparedStatement)
                    .setInt(1, 1);

            verify(preparedStatement)
                    .executeQuery();
        }
    }

    // GET BILL BY ID - NOT FOUND
    @Test
    void testGetBillByIdNotFound() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            Bill result =
                    billDAO.getBillById(999);

            assertNull(result);

            verify(preparedStatement)
                    .setInt(1, 999);

            verify(preparedStatement)
                    .executeQuery();
        }
    }

    // GET BILL BY ID - DATABASE ERROR
    @Test
    void testGetBillByIdSQLException() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenThrow(
                        new SQLException("Database error")
                );

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            assertThrows(
                    SQLException.class,
                    () -> billDAO.getBillById(1)
            );
        }
    }

    // GET ALL BILLS - SUCCESS
    @Test
    void testGetAllBills() throws Exception {

        when(connection.createStatement())
                .thenReturn(statement);

        when(statement.executeQuery(anyString()))
                .thenReturn(resultSet);

   
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

 
        when(resultSet.getInt("billId"))
                .thenReturn(1)
                .thenReturn(2);

        when(resultSet.getDate("billDate"))
                .thenReturn(
                        Date.valueOf("2026-08-11"),
                        Date.valueOf("2026-08-10")
                );

        when(resultSet.getInt("appointmentId"))
                .thenReturn(10)
                .thenReturn(11);

        when(resultSet.getInt("patientId"))
                .thenReturn(5)
                .thenReturn(6);

        when(resultSet.getString("patientName"))
                .thenReturn(
                        "Jenny Perera",
                        "John Smith"
                );

        when(resultSet.getString("patientContact"))
                .thenReturn(
                        "0771234567",
                        "0712345678"
                );

        when(resultSet.getString("treatmentType"))
                .thenReturn(
                        "Dental Cleaning",
                        "Dental Filling"
                );

        when(resultSet.getDouble("treatmentCost"))
                .thenReturn(
                        100.00,
                        150.00
                );

        when(resultSet.getDouble("consultationFee"))
                .thenReturn(
                        50.00,
                        50.00
                );

        when(resultSet.getDouble("totalAmount"))
                .thenReturn(
                        150.00,
                        200.00
                );

        when(resultSet.getDouble("amountPaid"))
                .thenReturn(
                        100.00,
                        200.00
                );

        when(resultSet.getString("paymentMethod"))
                .thenReturn(
                        Bill.PAYMENT_CASH,
                        Bill.PAYMENT_CARD
                );

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            List<Bill> bills =
                    billDAO.getAllBills();

            assertNotNull(bills);

            assertEquals(2, bills.size());

            // First bill
            assertEquals(
                    1,
                    bills.get(0).getBillId()
            );

            assertEquals(
                    "Jenny Perera",
                    bills.get(0).getPatientName()
            );

            assertEquals(
                    "Dental Cleaning",
                    bills.get(0).getTreatmentType()
            );

            assertEquals(
                    150.00,
                    bills.get(0).getTotalAmount(),
                    0.001
            );

            // Second bill
            assertEquals(
                    2,
                    bills.get(1).getBillId()
            );

            assertEquals(
                    "John Smith",
                    bills.get(1).getPatientName()
            );

            assertEquals(
                    "Dental Filling",
                    bills.get(1).getTreatmentType()
            );

            assertEquals(
                    200.00,
                    bills.get(1).getTotalAmount(),
                    0.001
            );

            verify(statement)
                    .executeQuery(
                            "SELECT * FROM bill ORDER BY billDate DESC"
                    );
        }
    }

    // GET ALL BILLS - EMPTY
    @Test
    void testGetAllBillsEmpty() throws Exception {

        when(connection.createStatement())
                .thenReturn(statement);

        when(statement.executeQuery(anyString()))
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            List<Bill> bills =
                    billDAO.getAllBills();

            assertNotNull(bills);

            assertTrue(bills.isEmpty());
        }
    }

    // UPDATE PAYMENT - SUCCESS
    @Test
    void testUpdatePaymentSuccess() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    billDAO.updatePayment(
                            1,
                            125.00,
                            Bill.PAYMENT_CARD
                    );

            assertTrue(result);

            verify(preparedStatement)
                    .setDouble(1, 125.00);

            verify(preparedStatement)
                    .setString(
                            2,
                            Bill.PAYMENT_CARD
                    );

            verify(preparedStatement)
                    .setInt(3, 1);

            verify(preparedStatement)
                    .executeUpdate();
        }
    }


    // UPDATE PAYMENT - FAILURE
    @Test
    void testUpdatePaymentFailure() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(0);

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    billDAO.updatePayment(
                            999,
                            100.00,
                            Bill.PAYMENT_CASH
                    );

            assertFalse(result);
        }
    }


    // UPDATE PAYMENT - DATABASE ERROR
    @Test
    void testUpdatePaymentSQLException() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenThrow(
                        new SQLException("Database error")
                );

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            assertThrows(
                    SQLException.class,
                    () -> billDAO.updatePayment(
                            1,
                            100.00,
                            Bill.PAYMENT_CASH
                    )
            );
        }
    }

    // CREATE BILL - WITHOUT BILL DATE
    @Test
    void testCreateBillWithoutBillDate() throws Exception {

        bill.setBillDate(null);

        when(connection.prepareStatement(
                anyString(),
                eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);

        when(preparedStatement.getGeneratedKeys())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt(1))
                .thenReturn(50);

        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    billDAO.createBill(bill);

            assertTrue(result);

            assertEquals(
                    50,
                    bill.getBillId()
            );

            verify(preparedStatement)
                    .setDate(
                            eq(1),
                            any(Date.class)
                    );
        }
    }


    // HELPER METHOD FOR SINGLE BILL
    private void mockBillResultSet()
            throws SQLException {

        when(resultSet.getInt("billId"))
                .thenReturn(1);

        when(resultSet.getDate("billDate"))
                .thenReturn(
                        Date.valueOf("2026-08-11")
                );

        when(resultSet.getInt("appointmentId"))
                .thenReturn(10);

        when(resultSet.getInt("patientId"))
                .thenReturn(5);

        when(resultSet.getString("patientName"))
                .thenReturn("Jenny Perera");

        when(resultSet.getString("patientContact"))
                .thenReturn("0771234567");

        when(resultSet.getString("treatmentType"))
                .thenReturn("Dental Cleaning");

        when(resultSet.getDouble("treatmentCost"))
                .thenReturn(100.00);

        when(resultSet.getDouble("consultationFee"))
                .thenReturn(50.00);

        when(resultSet.getDouble("totalAmount"))
                .thenReturn(150.00);

        when(resultSet.getDouble("amountPaid"))
                .thenReturn(100.00);

        when(resultSet.getString("paymentMethod"))
                .thenReturn(Bill.PAYMENT_CASH);
    }
}
