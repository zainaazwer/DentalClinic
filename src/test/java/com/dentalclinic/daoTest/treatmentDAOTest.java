package com.dentalclinic.daoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dentalclinic.dao.DatabaseConnection;
import com.dentalclinic.dao.TreatmentDAO;
import com.dentalclinic.model.Treatment;

public class treatmentDAOTest {

    private TreatmentDAO treatmentDAO;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {

        treatmentDAO = new TreatmentDAO();

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        statement = mock(Statement.class);
        resultSet = mock(ResultSet.class);
    }

    // GET ALL TREATMENTS - SUCCESS
    @Test
    void testGetAllTreatmentsSuccess() throws Exception {

        when(connection.createStatement())
                .thenReturn(statement);

        when(statement.executeQuery(
                "SELECT * FROM Treatment ORDER BY treatmentName"))
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getInt("treatmentId"))
                .thenReturn(1)
                .thenReturn(2);

        when(resultSet.getString("treatmentName"))
                .thenReturn("Cleaning")
                .thenReturn("Filling");

        when(resultSet.getString("description"))
                .thenReturn("Dental cleaning")
                .thenReturn("Tooth filling");

        when(resultSet.getDouble("treatmentCost"))
                .thenReturn(50.00)
                .thenReturn(100.00);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            List<Treatment> treatments =
                    treatmentDAO.getAllTreatments();


            assertNotNull(treatments);

            assertEquals(2, treatments.size());

            assertEquals(
                    "Cleaning",
                    treatments.get(0).getTreatmentName()
            );

            assertEquals(
                    50.00,
                    treatments.get(0).getTreatmentCost()
            );

            assertEquals(
                    "Filling",
                    treatments.get(1).getTreatmentName()
            );
        }
    }


    // GET ALL TREATMENTS - EMPTY
    @Test
    void testGetAllTreatmentsEmpty() throws Exception {

        when(connection.createStatement())
                .thenReturn(statement);

        when(statement.executeQuery(
                "SELECT * FROM Treatment ORDER BY treatmentName"))
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            List<Treatment> treatments =
                    treatmentDAO.getAllTreatments();


            assertNotNull(treatments);

            assertTrue(treatments.isEmpty());
        }
    }

    // GET TREATMENT BY ID - SUCCESS
    @Test
    void testGetTreatmentByIdSuccess() throws Exception {

        when(connection.prepareStatement(
                "SELECT * FROM Treatment WHERE treatmentId = ?"))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt("treatmentId"))
                .thenReturn(1);

        when(resultSet.getString("treatmentName"))
                .thenReturn("Cleaning");

        when(resultSet.getString("description"))
                .thenReturn("Dental cleaning");

        when(resultSet.getDouble("treatmentCost"))
                .thenReturn(50.00);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            Treatment treatment =
                    treatmentDAO.getTreatmentById(1);


            assertNotNull(treatment);

            assertEquals(1, treatment.getTreatmentId());

            assertEquals(
                    "Cleaning",
                    treatment.getTreatmentName()
            );

            assertEquals(
                    "Dental cleaning",
                    treatment.getDescription()
            );

            assertEquals(
                    50.00,
                    treatment.getTreatmentCost()
            );

            verify(preparedStatement)
                    .setInt(1, 1);
        }
    }

    // GET TREATMENT BY ID - NOT FOUND
    @Test
    void testGetTreatmentByIdNotFound() throws Exception {

        when(connection.prepareStatement(
                "SELECT * FROM Treatment WHERE treatmentId = ?"))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            Treatment treatment =
                    treatmentDAO.getTreatmentById(999);


            assertNull(treatment);

            verify(preparedStatement)
                    .setInt(1, 999);
        }
    }

    // GET TREATMENT BY NAME - SUCCESS
    @Test
    void testGetTreatmentByNameSuccess() throws Exception {

        when(connection.prepareStatement(
                "SELECT * FROM Treatment WHERE treatmentName = ?"))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt("treatmentId"))
                .thenReturn(1);

        when(resultSet.getString("treatmentName"))
                .thenReturn("Cleaning");

        when(resultSet.getString("description"))
                .thenReturn("Dental cleaning");

        when(resultSet.getDouble("treatmentCost"))
                .thenReturn(50.00);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            Treatment treatment =
                    treatmentDAO.getTreatmentByName("Cleaning");


            assertNotNull(treatment);

            assertEquals(
                    "Cleaning",
                    treatment.getTreatmentName()
            );

            assertEquals(
                    50.00,
                    treatment.getTreatmentCost()
            );

            verify(preparedStatement)
                    .setString(1, "Cleaning");
        }
    }

    // GET TREATMENT BY NAME - NOT FOUND
    @Test
    void testGetTreatmentByNameNotFound() throws Exception {

        when(connection.prepareStatement(
                "SELECT * FROM Treatment WHERE treatmentName = ?"))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            Treatment treatment =
                    treatmentDAO.getTreatmentByName(
                            "Unknown Treatment"
                    );


            assertNull(treatment);

            verify(preparedStatement)
                    .setString(1, "Unknown Treatment");
        }
    }

    // CREATE TREATMENT - SUCCESS
    @Test
    void testCreateTreatmentSuccess() throws Exception {

        Treatment treatment = new Treatment();

        treatment.setTreatmentName("Cleaning");
        treatment.setDescription("Dental cleaning");
        treatment.setTreatmentCost(50.00);


        when(connection.prepareStatement(
                "INSERT INTO Treatment " +
                "(treatmentName, description, treatmentCost) " +
                "VALUES (?, ?, ?)"))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    treatmentDAO.createTreatment(treatment);


            assertTrue(result);

            verify(preparedStatement)
                    .setString(1, "Cleaning");

            verify(preparedStatement)
                    .setString(2, "Dental cleaning");

            verify(preparedStatement)
                    .setDouble(3, 50.00);

            verify(preparedStatement)
                    .executeUpdate();
        }
    }

    // CREATE TREATMENT - FAILURE
    @Test
    void testCreateTreatmentFailure() throws Exception {

        Treatment treatment = new Treatment();

        treatment.setTreatmentName("Cleaning");
        treatment.setDescription("Dental cleaning");
        treatment.setTreatmentCost(50.00);


        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(0);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    treatmentDAO.createTreatment(treatment);


            assertFalse(result);
        }
    }

    // UPDATE TREATMENT - SUCCESS
    @Test
    void testUpdateTreatmentSuccess() throws Exception {

        Treatment treatment = new Treatment();

        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Filling");
        treatment.setDescription("Tooth filling");
        treatment.setTreatmentCost(100.00);


        when(connection.prepareStatement(
                "UPDATE Treatment SET treatmentName = ?, " +
                "description = ?, treatmentCost = ? " +
                "WHERE treatmentId = ?"))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    treatmentDAO.updateTreatment(treatment);


            assertTrue(result);

            verify(preparedStatement)
                    .setString(1, "Filling");

            verify(preparedStatement)
                    .setString(2, "Tooth filling");

            verify(preparedStatement)
                    .setDouble(3, 100.00);

            verify(preparedStatement)
                    .setInt(4, 1);
        }
    }

    // UPDATE TREATMENT - FAILURE
    @Test
    void testUpdateTreatmentFailure() throws Exception {

        Treatment treatment = new Treatment();

        treatment.setTreatmentId(999);
        treatment.setTreatmentName("Unknown");
        treatment.setDescription("Unknown treatment");
        treatment.setTreatmentCost(0.00);


        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(0);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    treatmentDAO.updateTreatment(treatment);


            assertFalse(result);
        }
    }

    // DELETE TREATMENT - SUCCESS
    @Test
    void testDeleteTreatmentSuccess() throws Exception {

        when(connection.prepareStatement(
                "DELETE FROM Treatment WHERE treatmentId = ?"))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(1);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    treatmentDAO.deleteTreatment(1);


            assertTrue(result);

            verify(preparedStatement)
                    .setInt(1, 1);

            verify(preparedStatement)
                    .executeUpdate();
        }
    }

    // DELETE TREATMENT - FAILURE
    @Test
    void testDeleteTreatmentFailure() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate())
                .thenReturn(0);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            boolean result =
                    treatmentDAO.deleteTreatment(999);


            assertFalse(result);
        }
    }

    // DATABASE ERROR
    @Test
    void testGetTreatmentByIdDatabaseError() throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenThrow(
                        new SQLException("Database connection error")
                );


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                    .thenReturn(connection);


            assertThrows(
                    SQLException.class,
                    () -> treatmentDAO.getTreatmentById(1)
            );
        }
    }
}