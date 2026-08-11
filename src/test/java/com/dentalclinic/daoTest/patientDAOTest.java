package com.dentalclinic.daoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.dentalclinic.dao.DatabaseConnection;
import com.dentalclinic.dao.PatientDAO;
import com.dentalclinic.model.Patient;

public class patientDAOTest {

    // CREATE PATIENT
    @Test
    void testCreatePatient() throws Exception {

        Patient patient = new Patient(
                1,
                "John Silva",
                "Colombo",
                "0771234567"
        );

        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeUpdate())
                .thenReturn(1);

        try (MockedStatic<DatabaseConnection> mockedConnection =
                     mockStatic(DatabaseConnection.class)) {

            mockedConnection
                    .when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            PatientDAO patientDAO = new PatientDAO();

            boolean result =
                    patientDAO.createPatient(patient);

            assertTrue(result);

            verify(statement)
                    .setString(1, "John Silva");

            verify(statement)
                    .setString(2, "Colombo");

            verify(statement)
                    .setString(3, "0771234567");

            verify(statement)
                    .executeUpdate();
        }
    }


    // CREATE PATIENT - FAILURE
    @Test
    void testCreatePatientFails() throws Exception {

        Patient patient = new Patient(
                1,
                "John Silva",
                "Colombo",
                "0771234567"
        );

        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeUpdate())
                .thenReturn(0);

        try (MockedStatic<DatabaseConnection> mockedConnection =
                     mockStatic(DatabaseConnection.class)) {

            mockedConnection
                    .when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            PatientDAO patientDAO = new PatientDAO();

            boolean result =
                    patientDAO.createPatient(patient);

            assertFalse(result);
        }
    }


    // GET PATIENT BY ID
    @Test
    void testGetPatientById() throws Exception {

        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt("patientId"))
                .thenReturn(1);

        when(resultSet.getString("fullName"))
                .thenReturn("John Weera");

        when(resultSet.getString("address"))
                .thenReturn("Colombo");

        when(resultSet.getString("phoneNumber"))
                .thenReturn("0771234567");

        try (MockedStatic<DatabaseConnection> mockedConnection =
                     mockStatic(DatabaseConnection.class)) {

            mockedConnection
                    .when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            PatientDAO patientDAO = new PatientDAO();

            Patient patient =
                    patientDAO.getPatientById(1);

            assertNotNull(patient);

            assertEquals(
                    1,
                    patient.getPatientId()
            );

            assertEquals(
                    "John Weera",
                    patient.getFullName()
            );

            assertEquals(
                    "Colombo",
                    patient.getAddress()
            );

            assertEquals(
                    "0771234567",
                    patient.getPhoneNumber()
            );

            verify(statement)
                    .setInt(1, 1);

            verify(statement)
                    .executeQuery();
        }
    }


    // GET PATIENT BY ID - NOT FOUND
    @Test
    void testGetPatientByIdNotFound() throws Exception {

        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);

        try (MockedStatic<DatabaseConnection> mockedConnection =
                     mockStatic(DatabaseConnection.class)) {

            mockedConnection
                    .when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            PatientDAO patientDAO = new PatientDAO();

            Patient patient =
                    patientDAO.getPatientById(999);

            assertNull(patient);
        }
    }


    // GET ALL PATIENTS
    @Test
    void testGetAllPatients() throws Exception {

        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.createStatement())
                .thenReturn(statement);

        when(statement.executeQuery(anyString()))
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getInt("patientId"))
                .thenReturn(1)
                .thenReturn(2);

        when(resultSet.getString("fullName"))
                .thenReturn("John Weera")
                .thenReturn("Jenny Perera");

        when(resultSet.getString("address"))
                .thenReturn("Colombo")
                .thenReturn("Kandy");

        when(resultSet.getString("phoneNumber"))
                .thenReturn("0771234567")
                .thenReturn("0712345678");

        try (MockedStatic<DatabaseConnection> mockedConnection =
                     mockStatic(DatabaseConnection.class)) {

            mockedConnection
                    .when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            PatientDAO patientDAO = new PatientDAO();

            List<Patient> patients =
                    patientDAO.getAllPatients();

            assertNotNull(patients);

            assertEquals(
                    2,
                    patients.size()
            );

            assertEquals(
                    "John Weera",
                    patients.get(0).getFullName()
            );

            assertEquals(
                    "Jenny Perera",
                    patients.get(1).getFullName()
            );

            assertEquals(
                    "Colombo",
                    patients.get(0).getAddress()
            );

            assertEquals(
                    "0771234567",
                    patients.get(0).getPhoneNumber()
            );
        }
    }


    // GET ALL PATIENTS - EMPTY
    @Test
    void testGetAllPatientsEmpty() throws Exception {

        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.createStatement())
                .thenReturn(statement);

        when(statement.executeQuery(anyString()))
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(false);

        try (MockedStatic<DatabaseConnection> mockedConnection =
                     mockStatic(DatabaseConnection.class)) {

            mockedConnection
                    .when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            PatientDAO patientDAO = new PatientDAO();

            List<Patient> patients =
                    patientDAO.getAllPatients();

            assertNotNull(patients);

            assertTrue(
                    patients.isEmpty()
            );
        }
    }


    // UPDATE PATIENT
    @Test
    void testUpdatePatient() throws Exception {

        Patient patient = new Patient(
                1,
                "John Silva Updated",
                "Colombo",
                "0711111111"
        );

        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeUpdate())
                .thenReturn(1);

        try (MockedStatic<DatabaseConnection> mockedConnection =
                     mockStatic(DatabaseConnection.class)) {

            mockedConnection
                    .when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            PatientDAO patientDAO = new PatientDAO();

            boolean result =
                    patientDAO.updatePatient(patient);

            assertTrue(result);

            // CORRECTED VALUE
            verify(statement)
                    .setString(
                            1,
                            "John Silva Updated"
                    );

            verify(statement)
                    .setString(
                            2,
                            "Colombo"
                    );

            verify(statement)
                    .setString(
                            3,
                            "0711111111"
                    );

            verify(statement)
                    .setInt(
                            4,
                            1
                    );

            verify(statement)
                    .executeUpdate();
        }
    }


    // UPDATE PATIENT - FAILURE
    @Test
    void testUpdatePatientFails() throws Exception {

        Patient patient = new Patient(
                1,
                "John Weera",
                "Colombo",
                "0771234567"
        );

        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);

        when(statement.executeUpdate())
                .thenReturn(0);

        try (MockedStatic<DatabaseConnection> mockedConnection =
                     mockStatic(DatabaseConnection.class)) {

            mockedConnection
                    .when(DatabaseConnection::getConnection)
                    .thenReturn(connection);

            PatientDAO patientDAO = new PatientDAO();

            boolean result =
                    patientDAO.updatePatient(patient);

            assertFalse(result);
        }
    }
}