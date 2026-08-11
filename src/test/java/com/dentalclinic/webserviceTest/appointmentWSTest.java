package com.dentalclinic.webserviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;
import com.dentalclinic.webservice.AppointmentWS;

import javax.ws.rs.core.Response;

public class appointmentWSTest {

    private AppointmentWS webService;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() throws Exception {

        // Create web service
        webService = new AppointmentWS();

        // Create mock service
        appointmentService = mock(AppointmentService.class);

        // Replace real AppointmentService with mock
        Field serviceField =
                AppointmentWS.class.getDeclaredField("appointmentService");

        serviceField.setAccessible(true);

        serviceField.set(webService, appointmentService);
    }

    // =========================================================
    // GET ALL APPOINTMENTS - SUCCESS
    // =========================================================

    @Test
    void testGetAllAppointmentsSuccess() throws Exception {

        Appointment appointment1 = new Appointment();

        appointment1.setAppointmentId(1);
        appointment1.setPatientId(1);
        appointment1.setPatientName("Karan Silva");
        appointment1.setDentistName("Dr. Perera");
        appointment1.setTreatmentType("Cleaning");
        appointment1.setAppointmentDate("2026-08-10");
        appointment1.setAppointmentTime("10:00");

        Appointment appointment2 = new Appointment();

        appointment2.setAppointmentId(2);
        appointment2.setPatientId(2);
        appointment2.setPatientName("Nimal Perera");
        appointment2.setDentistName("Dr. Silva");
        appointment2.setTreatmentType("Filling");
        appointment2.setAppointmentDate("2026-08-11");
        appointment2.setAppointmentTime("11:00");

        List<Appointment> appointments =
                Arrays.asList(appointment1, appointment2);

        when(appointmentService.getAllAppointments())
                .thenReturn(appointments);

        Response response =
                webService.getAllAppointments();

        assertEquals(
                Response.Status.OK.getStatusCode(),
                response.getStatus()
        );

        assertEquals(
                appointments,
                response.getEntity()
        );

        verify(appointmentService)
                .getAllAppointments();

        response.close();
    }

    // =========================================================
    // GET ALL APPOINTMENTS - EMPTY LIST
    // =========================================================

    @Test
    void testGetAllAppointmentsEmpty() throws Exception {

        when(appointmentService.getAllAppointments())
                .thenReturn(Collections.emptyList());

        Response response =
                webService.getAllAppointments();

        assertEquals(
                Response.Status.OK.getStatusCode(),
                response.getStatus()
        );

        assertNotNull(response.getEntity());

        assertTrue(
                ((List<?>) response.getEntity()).isEmpty()
        );

        verify(appointmentService)
                .getAllAppointments();

        response.close();
    }

    // =========================================================
    // GET ALL APPOINTMENTS - DATABASE ERROR
    // =========================================================

    @Test
    void testGetAllAppointmentsDatabaseError()
            throws Exception {

        when(appointmentService.getAllAppointments())
                .thenThrow(
                        new SQLException(
                                "Database connection error"
                        )
                );

        Response response =
                webService.getAllAppointments();

        assertEquals(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus()
        );

        assertEquals(
                "Database error",
                response.getEntity()
        );

        verify(appointmentService)
                .getAllAppointments();

        response.close();
    }

    // =========================================================
    // GET APPOINTMENTS BY PATIENT - SUCCESS
    // =========================================================

    @Test
    void testGetAppointmentsByPatientSuccess()
            throws Exception {

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatientId(5);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");
        appointment.setAppointmentDate("2026-08-10");
        appointment.setAppointmentTime("10:00");

        List<Appointment> appointments =
                Collections.singletonList(appointment);

        when(
                appointmentService.getAppointmentsByPatientId(5)
        ).thenReturn(appointments);

        Response response =
                webService.getAppointmentsByPatient(5);

        assertEquals(
                Response.Status.OK.getStatusCode(),
                response.getStatus()
        );

        assertEquals(
                appointments,
                response.getEntity()
        );

        verify(appointmentService)
                .getAppointmentsByPatientId(5);

        response.close();
    }

    // =========================================================
    // GET APPOINTMENTS BY PATIENT - EMPTY RESULT
    // =========================================================

    @Test
    void testGetAppointmentsByPatientEmpty()
            throws Exception {

        when(
                appointmentService.getAppointmentsByPatientId(99)
        ).thenReturn(Collections.emptyList());

        Response response =
                webService.getAppointmentsByPatient(99);

        assertEquals(
                Response.Status.OK.getStatusCode(),
                response.getStatus()
        );

        assertNotNull(
                response.getEntity()
        );

        assertTrue(
                ((List<?>) response.getEntity()).isEmpty()
        );

        verify(appointmentService)
                .getAppointmentsByPatientId(99);

        response.close();
    }

    // =========================================================
    // GET APPOINTMENTS BY PATIENT - DATABASE ERROR
    // =========================================================

    @Test
    void testGetAppointmentsByPatientDatabaseError()
            throws Exception {

        when(
                appointmentService.getAppointmentsByPatientId(5)
        ).thenThrow(
                new SQLException(
                        "Database connection error"
                )
        );

        Response response =
                webService.getAppointmentsByPatient(5);

        assertEquals(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus()
        );

        assertEquals(
                "Database error",
                response.getEntity()
        );

        verify(appointmentService)
                .getAppointmentsByPatientId(5);

        response.close();
    }

    // =========================================================
    // ADD APPOINTMENT - SUCCESS
    // =========================================================

    @Test
    void testAddAppointmentSuccess()
            throws Exception {

        Appointment appointment =
                new Appointment();

        appointment.setPatientId(1);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");
        appointment.setAppointmentDate("2026-08-10");
        appointment.setAppointmentTime("10:00");

        when(
                appointmentService.registerAppointment(appointment)
        ).thenReturn(true);

        Response response =
                webService.addAppointment(appointment);

        assertEquals(
                Response.Status.CREATED.getStatusCode(),
                response.getStatus()
        );

        assertEquals(
                "Appointment created successfully",
                response.getEntity()
        );

        verify(appointmentService)
                .registerAppointment(appointment);

        response.close();
    }

    // =========================================================
    // ADD APPOINTMENT - INVALID DATA
    // =========================================================

    @Test
    void testAddAppointmentInvalidData()
            throws Exception {

        Appointment appointment =
                new Appointment();

        appointment.setPatientId(0);
        appointment.setTreatmentType("");

        when(
                appointmentService.registerAppointment(appointment)
        ).thenReturn(false);

        Response response =
                webService.addAppointment(appointment);

        assertEquals(
                Response.Status.BAD_REQUEST.getStatusCode(),
                response.getStatus()
        );

        assertEquals(
                "Invalid appointment data",
                response.getEntity()
        );

        verify(appointmentService)
                .registerAppointment(appointment);

        response.close();
    }

    // =========================================================
    // ADD APPOINTMENT - NULL OBJECT
    // =========================================================

    @Test
    void testAddAppointmentNull()
            throws Exception {

        when(
                appointmentService.registerAppointment(null)
        ).thenReturn(false);

        Response response =
                webService.addAppointment(null);

        assertEquals(
                Response.Status.BAD_REQUEST.getStatusCode(),
                response.getStatus()
        );

        assertEquals(
                "Invalid appointment data",
                response.getEntity()
        );

        verify(appointmentService)
                .registerAppointment(null);

        response.close();
    }

    // =========================================================
    // ADD APPOINTMENT - DATABASE ERROR
    // =========================================================

    @Test
    void testAddAppointmentDatabaseError()
            throws Exception {

        Appointment appointment =
                new Appointment();

        appointment.setPatientId(1);
        appointment.setPatientName("Karan Silva");
        appointment.setTreatmentType("Cleaning");
        appointment.setAppointmentDate("2026-08-10");
        appointment.setAppointmentTime("10:00");

        when(
                appointmentService.registerAppointment(appointment)
        ).thenThrow(
                new SQLException(
                        "Database connection error"
                )
        );

        Response response =
                webService.addAppointment(appointment);

        assertEquals(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus()
        );

        assertEquals(
                "Database error",
                response.getEntity()
        );

        verify(appointmentService)
                .registerAppointment(appointment);

        response.close();
    }
}