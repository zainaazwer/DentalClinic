package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.AddAppointmentController;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

public class addAppointmentControllerTest {

    private TestableAddAppointmentController controller;

    private AppointmentService appointmentService;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;


    // TESTABLE CONTROLLER
    private static class TestableAddAppointmentController
            extends AddAppointmentController {

        public void callDoGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, java.io.IOException {

            super.doGet(request, response);
        }

        public void callDoPost(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, java.io.IOException {

            super.doPost(request, response);
        }
    }

    // SET UP
    @BeforeEach
    void setUp() throws Exception {

        controller = new TestableAddAppointmentController();

        appointmentService = mock(AppointmentService.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        Field serviceField =
                AddAppointmentController.class
                        .getDeclaredField("appointmentService");

        serviceField.setAccessible(true);

        serviceField.set(
                controller,
                appointmentService
        );


        // Request dispatcher
        when(request.getRequestDispatcher(anyString()))
                .thenReturn(dispatcher);


        // Session
        when(request.getSession())
                .thenReturn(session);
    }

    // GET - OPEN ADD APPOINTMENT PAGE
    @Test
    void testDoGet() throws Exception {

        controller.callDoGet(
                request,
                response
        );


        verify(request)
                .getRequestDispatcher(
                        "/AddAppointment.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // POST - SUCCESS
    @Test
    void testDoPostSuccess() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn("1");

        when(request.getParameter("patientName"))
                .thenReturn("Karan Silva");

        when(request.getParameter("dentistName"))
                .thenReturn("Dr. Perera");

        when(request.getParameter("treatmentType"))
                .thenReturn("Cleaning");

        when(request.getParameter("appointmentDate"))
                .thenReturn("2026-08-15");

        when(request.getParameter("appointmentTime"))
                .thenReturn("10:30");


        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(true);


        controller.callDoPost(
                request,
                response
        );


        verify(appointmentService)
                .registerAppointment(
                        any(Appointment.class)
                );


        verify(session)
                .setAttribute(
                        "success",
                        "Appointment registered successfully!"
                );


        verify(response)
                .sendRedirect(
                        "Dashboard"
                );


        verify(request, never())
                .getRequestDispatcher(
                        "/AddAppointment.jsp"
                );
    }

    // POST - INVALID PATIENT ID
    @Test
    void testDoPostInvalidPatientId() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn("abc");


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "Patient ID must be a valid number."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(appointmentService, never())
                .registerAppointment(
                        any(Appointment.class)
                );


        verify(response, never())
                .sendRedirect(
                        anyString()
                );
    }


    // POST - EMPTY PATIENT ID
    @Test
    void testDoPostEmptyPatientId() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn("");


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "Patient ID must be a valid number."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(appointmentService, never())
                .registerAppointment(
                        any(Appointment.class)
                );
    }

    // POST - NULL PATIENT ID
    @Test
    void testDoPostNullPatientId() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn(null);


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "Patient ID must be a valid number."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(appointmentService, never())
                .registerAppointment(
                        any(Appointment.class)
                );
    }

    // POST - SERVICE RETURNS FALSE
    @Test
    void testDoPostRegistrationFailure() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn("1");

        when(request.getParameter("patientName"))
                .thenReturn("Karan Silva");

        when(request.getParameter("dentistName"))
                .thenReturn("Dr. Perera");

        when(request.getParameter("treatmentType"))
                .thenReturn("Cleaning");

        when(request.getParameter("appointmentDate"))
                .thenReturn("2026-08-15");

        when(request.getParameter("appointmentTime"))
                .thenReturn("10:30");


        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(false);


        controller.callDoPost(
                request,
                response
        );


        verify(appointmentService)
                .registerAppointment(
                        any(Appointment.class)
                );


        verify(request)
                .setAttribute(
                        "error",
                        "Unable to register appointment."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(response, never())
                .sendRedirect(
                        anyString()
                );
    }

    // POST - SQL EXCEPTION
    @Test
    void testDoPostSQLException() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn("1");

        when(request.getParameter("patientName"))
                .thenReturn("Karan Silva");

        when(request.getParameter("dentistName"))
                .thenReturn("Dr. Perera");

        when(request.getParameter("treatmentType"))
                .thenReturn("Cleaning");

        when(request.getParameter("appointmentDate"))
                .thenReturn("2026-08-15");

        when(request.getParameter("appointmentTime"))
                .thenReturn("10:30");


        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenThrow(
                        new SQLException(
                                "Database connection error"
                        )
                );


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "Database error while registering appointment."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(response, never())
                .sendRedirect(
                        anyString()
                );
    }

    // POST - NULL PATIENT NAME
    @Test
    void testDoPostNullPatientName() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn("1");

        when(request.getParameter("patientName"))
                .thenReturn(null);

        when(request.getParameter("dentistName"))
                .thenReturn("Dr. Perera");

        when(request.getParameter("treatmentType"))
                .thenReturn("Cleaning");

        when(request.getParameter("appointmentDate"))
                .thenReturn("2026-08-15");

        when(request.getParameter("appointmentTime"))
                .thenReturn("10:30");


        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(false);


        controller.callDoPost(
                request,
                response
        );


        verify(appointmentService)
                .registerAppointment(
                        argThat(appointment ->
                                appointment.getPatientName() == null
                        )
                );


        verify(request)
                .setAttribute(
                        "error",
                        "Unable to register appointment."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // POST - EMPTY TREATMENT TYPE
    @Test
    void testDoPostEmptyTreatmentType() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn("1");

        when(request.getParameter("patientName"))
                .thenReturn("Karan Silva");

        when(request.getParameter("dentistName"))
                .thenReturn("Dr. Perera");

        when(request.getParameter("treatmentType"))
                .thenReturn("");

        when(request.getParameter("appointmentDate"))
                .thenReturn("2026-08-15");

        when(request.getParameter("appointmentTime"))
                .thenReturn("10:30");


        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(false);


        controller.callDoPost(
                request,
                response
        );


        verify(appointmentService)
                .registerAppointment(
                        argThat(appointment ->
                                "".equals(
                                        appointment.getTreatmentType()
                                )
                        )
                );


        verify(request)
                .setAttribute(
                        "error",
                        "Unable to register appointment."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // POST - VERIFY APPOINTMENT DATA
    @Test
    void testAppointmentDataIsCorrect() throws Exception {

        when(request.getParameter("patientId"))
                .thenReturn("10");

        when(request.getParameter("patientName"))
                .thenReturn("Karan Perera");

        when(request.getParameter("dentistName"))
                .thenReturn("Dr. Silva");

        when(request.getParameter("treatmentType"))
                .thenReturn("Root Canal");

        when(request.getParameter("appointmentDate"))
                .thenReturn("2026-09-01");

        when(request.getParameter("appointmentTime"))
                .thenReturn("14:00");


        when(appointmentService.registerAppointment(
                any(Appointment.class)))
                .thenReturn(true);


        controller.callDoPost(
                request,
                response
        );


        verify(appointmentService)
                .registerAppointment(
                        argThat(appointment ->

                                appointment.getPatientId() == 10

                                && "Karan Perera".equals(
                                        appointment.getPatientName()
                                )

                                && "Dr. Silva".equals(
                                        appointment.getDentistName()
                                )

                                && "Root Canal".equals(
                                        appointment.getTreatmentType()
                                )

                                && "2026-09-01".equals(
                                        appointment.getAppointmentDate()
                                )

                                && "14:00".equals(
                                        appointment.getAppointmentTime()
                                )
                        )
                );


        verify(response)
                .sendRedirect(
                        "Dashboard"
                );
    }
}
