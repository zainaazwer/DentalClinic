package com.dentalclinic.daoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.dao.DatabaseConnection;
import com.dentalclinic.model.Appointment;

public class appointmentDAOTest {

    private AppointmentDAO appointmentDAO;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {

        appointmentDAO = new AppointmentDAO();

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        statement = mock(Statement.class);
        resultSet = mock(ResultSet.class);
    }

    // CREATE APPOINTMENT - SUCCESS
    @Test
    void testCreateAppointmentSuccess() throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(1);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");


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
                .thenReturn(100);


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            boolean result =
                    appointmentDAO.createAppointment(appointment);


            assertTrue(result);

            assertEquals(
                    100,
                    appointment.getAppointmentId()
            );


            verify(preparedStatement)
                    .setDate(
                            eq(1),
                            eq(Date.valueOf("2026-08-15"))
                    );

            verify(preparedStatement)
                    .setTime(
                            eq(2),
                            eq(Time.valueOf("10:30:00"))
                    );

            verify(preparedStatement)
                    .setString(3, "Cleaning");

            verify(preparedStatement)
                    .setInt(4, 1);

            verify(preparedStatement)
                    .setString(5, "Karan Silva");

            verify(preparedStatement)
                    .setString(6, "Dr. Perera");

            verify(preparedStatement)
                    .executeUpdate();
        }
    }

    // CREATE APPOINTMENT - FAILURE
    @Test
    void testCreateAppointmentFailure() throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(1);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");


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


            boolean result =
                    appointmentDAO.createAppointment(appointment);


            assertFalse(result);

            verify(preparedStatement)
                    .executeUpdate();
        }
    }

    // CREATE APPOINTMENT - DATABASE ERROR
    @Test
    void testCreateAppointmentDatabaseError()
            throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(1);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");


        when(connection.prepareStatement(
                anyString(),
                eq(Statement.RETURN_GENERATED_KEYS)))
                .thenThrow(
                        new SQLException("Database connection error")
                );


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            assertThrows(
                    SQLException.class,
                    () -> appointmentDAO
                            .createAppointment(appointment)
            );
        }
    }

    // GET APPOINTMENT BY ID - FOUND
    @Test
    void testGetAppointmentByIdFound()
            throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt("appointmentId"))
                .thenReturn(10);

        when(resultSet.getDate("appointmentDate"))
                .thenReturn(Date.valueOf("2026-08-15"));

        when(resultSet.getTime("appointmentTime"))
                .thenReturn(Time.valueOf("10:30:00"));

        when(resultSet.getString("treatmentType"))
                .thenReturn("Cleaning");

        when(resultSet.getInt("patientId"))
                .thenReturn(1);

        when(resultSet.getString("patientName"))
                .thenReturn("Karan Silva");

        when(resultSet.getString("dentistName"))
                .thenReturn("Dr. Perera");


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            Appointment appointment =
                    appointmentDAO.getAppointmentById(10);


            assertNotNull(appointment);

            assertEquals(
                    10,
                    appointment.getAppointmentId()
            );

            assertEquals(
                    "2026-08-15",
                    appointment.getAppointmentDate()
            );

            assertEquals(
                    "10:30:00",
                    appointment.getAppointmentTime()
            );

            assertEquals(
                    "Cleaning",
                    appointment.getTreatmentType()
            );

            assertEquals(
                    1,
                    appointment.getPatientId()
            );

            assertEquals(
                    "Karan Silva",
                    appointment.getPatientName()
            );

            assertEquals(
                    "Dr. Perera",
                    appointment.getDentistName()
            );


            verify(preparedStatement)
                    .setInt(1, 10);
        }
    }

    // GET APPOINTMENT BY ID - NOT FOUND
    @Test
    void testGetAppointmentByIdNotFound()
            throws Exception {

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


            Appointment appointment =
                    appointmentDAO.getAppointmentById(999);


            assertNull(appointment);


            verify(preparedStatement)
                    .setInt(1, 999);
        }
    }

    // GET APPOINTMENT BY ID - DATABASE ERROR
    @Test
    void testGetAppointmentByIdDatabaseError()
            throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenThrow(
                        new SQLException("Database error")
                );


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            assertThrows(
                    SQLException.class,
                    () -> appointmentDAO
                            .getAppointmentById(10)
            );
        }
    }

    // GET APPOINTMENTS BY PATIENT - SUCCESS
    @Test
    void testGetAppointmentsByPatientIdSuccess()
            throws Exception {

        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery())
                .thenReturn(resultSet);


        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);


        when(resultSet.getInt("appointmentId"))
                .thenReturn(1)
                .thenReturn(2);

        when(resultSet.getDate("appointmentDate"))
                .thenReturn(
                        Date.valueOf("2026-08-15"),
                        Date.valueOf("2026-08-16")
                );

        when(resultSet.getTime("appointmentTime"))
                .thenReturn(
                        Time.valueOf("10:00:00"),
                        Time.valueOf("11:00:00")
                );

        when(resultSet.getString("treatmentType"))
                .thenReturn(
                        "Cleaning",
                        "Filling"
                );

        when(resultSet.getInt("patientId"))
                .thenReturn(5)
                .thenReturn(5);

        when(resultSet.getString("patientName"))
                .thenReturn(
                        "Karan Silva",
                        "Karan Silva"
                );

        when(resultSet.getString("dentistName"))
                .thenReturn(
                        "Dr. Perera",
                        "Dr. Perera"
                );


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            List<Appointment> appointments =
                    appointmentDAO
                            .getAppointmentsByPatientId(5);


            assertNotNull(appointments);

            assertEquals(
                    2,
                    appointments.size()
            );

            assertEquals(
                    "Cleaning",
                    appointments.get(0)
                            .getTreatmentType()
            );

            assertEquals(
                    "Filling",
                    appointments.get(1)
                            .getTreatmentType()
            );


            verify(preparedStatement)
                    .setInt(1, 5);
        }
    }

    // GET APPOINTMENTS BY PATIENT - NO RESULTS
    @Test
    void testGetAppointmentsByPatientIdEmpty()
            throws Exception {

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


            List<Appointment> appointments =
                    appointmentDAO
                            .getAppointmentsByPatientId(99);


            assertNotNull(appointments);

            assertTrue(
                    appointments.isEmpty()
            );


            verify(preparedStatement)
                    .setInt(1, 99);
        }
    }


    // GET ALL APPOINTMENTS - SUCCESS
    @Test
    void testGetAllAppointmentsSuccess()
            throws Exception {

        when(connection.createStatement())
                .thenReturn(statement);

        when(statement.executeQuery(anyString()))
                .thenReturn(resultSet);


        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);


        when(resultSet.getInt("appointmentId"))
                .thenReturn(1);

        when(resultSet.getDate("appointmentDate"))
                .thenReturn(
                        Date.valueOf("2026-08-15")
                );

        when(resultSet.getTime("appointmentTime"))
                .thenReturn(
                        Time.valueOf("10:00:00")
                );

        when(resultSet.getString("treatmentType"))
                .thenReturn("Cleaning");

        when(resultSet.getInt("patientId"))
                .thenReturn(1);

        when(resultSet.getString("patientName"))
                .thenReturn("Karan Silva");

        when(resultSet.getString("dentistName"))
                .thenReturn("Dr. Perera");


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            List<Appointment> appointments =
                    appointmentDAO
                            .getAllAppointments();


            assertNotNull(appointments);

            assertEquals(
                    1,
                    appointments.size()
            );

            assertEquals(
                    1,
                    appointments.get(0)
                            .getAppointmentId()
            );

            assertEquals(
                    "John Silva",
                    appointments.get(0)
                            .getPatientName()
            );

            assertEquals(
                    "Cleaning",
                    appointments.get(0)
                            .getTreatmentType()
            );


            verify(connection)
                    .createStatement();
        }
    }


    // GET ALL APPOINTMENTS - EMPTY
    @Test
    void testGetAllAppointmentsEmpty()
            throws Exception {

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


            List<Appointment> appointments =
                    appointmentDAO
                            .getAllAppointments();


            assertNotNull(appointments);

            assertTrue(
                    appointments.isEmpty()
            );
        }
    }


    // GET ALL APPOINTMENTS - DATABASE ERROR
    @Test
    void testGetAllAppointmentsDatabaseError()
            throws Exception {

        when(connection.createStatement())
                .thenThrow(
                        new SQLException("Database error")
                );


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            assertThrows(
                    SQLException.class,
                    () -> appointmentDAO
                            .getAllAppointments()
            );
        }
    }


    // CREATE APPOINTMENT - INVALID DATE
    @Test
    void testCreateAppointmentInvalidDate()
            throws Exception {

        Appointment appointment =
                new Appointment();

        appointment.setPatientId(1);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");

        appointment.setAppointmentDate(
                "invalid-date"
        );

        appointment.setAppointmentTime(
                "10:30"
        );


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            assertThrows(
                    IllegalArgumentException.class,
                    () -> appointmentDAO
                            .createAppointment(appointment)
            );
        }
    }


    // CREATE APPOINTMENT - INVALID TIME
    @Test
    void testCreateAppointmentInvalidTime()
            throws Exception {

        Appointment appointment =
                new Appointment();

        appointment.setPatientId(1);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");

        appointment.setAppointmentDate(
                "2026-08-15"
        );

        appointment.setAppointmentTime(
                "invalid-time"
        );


        try (MockedStatic<DatabaseConnection> mocked =
                     Mockito.mockStatic(DatabaseConnection.class)) {

            mocked.when(DatabaseConnection::getConnection)
                  .thenReturn(connection);


            assertThrows(
                    IllegalArgumentException.class,
                    () -> appointmentDAO
                            .createAppointment(appointment)
            );
        }
    }
}
