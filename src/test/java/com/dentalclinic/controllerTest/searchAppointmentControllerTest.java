package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.SearchAppointmentController;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

public class searchAppointmentControllerTest {

    private TestableSearchAppointmentController controller;

    private AppointmentService appointmentService;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;


    // TESTABLE CONTROLLER
    private static class TestableSearchAppointmentController
            extends SearchAppointmentController {

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

        controller = new TestableSearchAppointmentController();

        appointmentService = mock(AppointmentService.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        
        Field serviceField =
                SearchAppointmentController.class
                        .getDeclaredField("appointmentService");

        serviceField.setAccessible(true);

        serviceField.set(
                controller,
                appointmentService
        );


        // Request dispatcher
        when(request.getRequestDispatcher(anyString()))
                .thenReturn(dispatcher);
    }

    // GET - APPOINTMENT FOUND
    @Test
    void testDoGetAppointmentFound() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("1");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatientId(5);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");


        when(appointmentService.getAppointmentById(1))
                .thenReturn(appointment);


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService)
                .getAppointmentById(1);


        verify(request)
                .setAttribute(
                        "appointment",
                        appointment
                );


        verify(request, never())
                .setAttribute(
                        eq("error"),
                        anyString()
                );


        verify(request)
                .getRequestDispatcher(
                        "/SearchAppointment.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // GET - APPOINTMENT NOT FOUND
    @Test
    void testDoGetAppointmentNotFound() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("99");


        when(appointmentService.getAppointmentById(99))
                .thenReturn(null);


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService)
                .getAppointmentById(99);


        verify(request)
                .setAttribute(
                        "error",
                        "Appointment not found."
                );


        verify(request, never())
                .setAttribute(
                        eq("appointment"),
                        any(Appointment.class)
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // GET - INVALID APPOINTMENT ID
    @Test
    void testDoGetInvalidAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("abc");


        controller.callDoGet(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "Invalid Appointment ID."
                );


        verify(appointmentService, never())
                .getAppointmentById(anyInt());


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - EMPTY APPOINTMENT ID
    @Test
    void testDoGetEmptyAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("");


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService, never())
                .getAppointmentById(anyInt());


        verify(request, never())
                .setAttribute(
                        eq("appointment"),
                        any(Appointment.class)
                );


        verify(request, never())
                .setAttribute(
                        eq("error"),
                        anyString()
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - NULL APPOINTMENT ID
    @Test
    void testDoGetNullAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn(null);


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService, never())
                .getAppointmentById(anyInt());


        verify(request, never())
                .setAttribute(
                        eq("appointment"),
                        any(Appointment.class)
                );


        verify(request, never())
                .setAttribute(
                        eq("error"),
                        anyString()
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - ZERO APPOINTMENT ID
    @Test
    void testDoGetZeroAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("0");


        when(appointmentService.getAppointmentById(0))
                .thenReturn(null);


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService)
                .getAppointmentById(0);


        verify(request)
                .setAttribute(
                        "error",
                        "Appointment not found."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - NEGATIVE APPOINTMENT ID
    @Test
    void testDoGetNegativeAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("-1");


        when(appointmentService.getAppointmentById(-1))
                .thenReturn(null);


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService)
                .getAppointmentById(-1);


        verify(request)
                .setAttribute(
                        "error",
                        "Appointment not found."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - DATABASE ERROR
    @Test
    void testDoGetSQLException() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("5");


        when(appointmentService.getAppointmentById(5))
                .thenThrow(
                        new SQLException(
                                "Database connection failed"
                        )
                );


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService)
                .getAppointmentById(5);


        verify(request)
                .setAttribute(
                        "error",
                        "Database error while searching appointment."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - APPOINTMENT DATA VERIFICATION
    @Test
    void testAppointmentDataIsCorrect() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("10");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(10);
        appointment.setPatientId(25);
        appointment.setPatientName("Karan Perera");
        appointment.setDentistName("Dr. Silva");
        appointment.setTreatmentType("Root Canal");
        appointment.setAppointmentDate("2026-09-01");
        appointment.setAppointmentTime("14:00");


        when(appointmentService.getAppointmentById(10))
                .thenReturn(appointment);


        controller.callDoGet(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "appointment",
                        argThat(value -> {

                            Appointment a =
                                    (Appointment) value;

                            return a.getAppointmentId() == 10
                                    && a.getPatientId() == 25
                                    && "Karan Perera".equals(
                                            a.getPatientName()
                                    )
                                    && "Dr. Silva".equals(
                                            a.getDentistName()
                                    )
                                    && "Root Canal".equals(
                                            a.getTreatmentType()
                                    )
                                    && "2026-09-01".equals(
                                            a.getAppointmentDate()
                                    )
                                    && "14:00".equals(
                                            a.getAppointmentTime()
                                    );
                        })
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // POST - SUCCESS
    @Test
    void testDoPost() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("1");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatientId(5);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Cleaning");


        when(appointmentService.getAppointmentById(1))
                .thenReturn(appointment);


        controller.callDoPost(
                request,
                response
        );


        verify(appointmentService)
                .getAppointmentById(1);


        verify(request)
                .setAttribute(
                        "appointment",
                        appointment
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // POST - INVALID ID
    @Test
    void testDoPostInvalidAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("abc");


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "Invalid Appointment ID."
                );


        verify(appointmentService, never())
                .getAppointmentById(anyInt());


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // POST - NOT FOUND
    @Test
    void testDoPostAppointmentNotFound() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("100");


        when(appointmentService.getAppointmentById(100))
                .thenReturn(null);


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "Appointment not found."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }
}
