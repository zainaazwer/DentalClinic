package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.ViewAppointmentController;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

public class viewAppointmentControllerTest {

    private TestableViewAppointmentController controller;

    private AppointmentService appointmentService;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;


    // Test subclass to access protected doGet() and doPost()
    private static class TestableViewAppointmentController
            extends ViewAppointmentController {

        public void callDoGet(HttpServletRequest request,
                              HttpServletResponse response)
                throws ServletException, IOException {

            super.doGet(request, response);
        }

        public void callDoPost(HttpServletRequest request,
                               HttpServletResponse response)
                throws ServletException, IOException {

            super.doPost(request, response);
        }
    }


    @BeforeEach
    void setUp() throws Exception {

        controller = new TestableViewAppointmentController();

        appointmentService = mock(AppointmentService.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);


        Field serviceField =
                ViewAppointmentController.class
                        .getDeclaredField("appointmentService");

        serviceField.setAccessible(true);

        serviceField.set(
                controller,
                appointmentService
        );


        // Mock JSP dispatcher
        when(request.getRequestDispatcher(
                "/ViewAppointment.jsp"))
                .thenReturn(dispatcher);
    }


    // GET - APPOINTMENTS FOUND
    @Test
    void testDoGetAppointmentsFound()
            throws Exception {

        Appointment appointment1 = new Appointment();

        appointment1.setAppointmentId(1);
        appointment1.setPatientId(10);
        appointment1.setPatientName("Karan");
        appointment1.setDentistName("Dr. Smith");
        appointment1.setTreatmentType("Cleaning");
        appointment1.setAppointmentDate("2026-08-10");
        appointment1.setAppointmentTime("10:00:00");


        Appointment appointment2 = new Appointment();

        appointment2.setAppointmentId(2);
        appointment2.setPatientId(11);
        appointment2.setPatientName("Karan");
        appointment2.setDentistName("Dr. Smith");
        appointment2.setTreatmentType("Filling");
        appointment2.setAppointmentDate("2026-08-11");
        appointment2.setAppointmentTime("11:00:00");


        List<Appointment> appointments =
                Arrays.asList(
                        appointment1,
                        appointment2
                );


        when(appointmentService.getAllAppointments())
                .thenReturn(appointments);


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService)
                .getAllAppointments();


        verify(request)
                .setAttribute(
                        "appointments",
                        appointments
                );


        verify(request)
                .getRequestDispatcher(
                        "/ViewAppointment.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - NO APPOINTMENTS
    @Test
    void testDoGetNoAppointments()
            throws Exception {

        List<Appointment> appointments =
                Arrays.asList();


        when(appointmentService.getAllAppointments())
                .thenReturn(appointments);


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService)
                .getAllAppointments();


        verify(request)
                .setAttribute(
                        "appointments",
                        appointments
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - DATABASE ERROR
    @Test
    void testDoGetDatabaseError()
            throws Exception {

        when(appointmentService.getAllAppointments())
                .thenThrow(
                        new SQLException(
                                "Database error"
                        )
                );


        controller.callDoGet(
                request,
                response
        );


        verify(appointmentService)
                .getAllAppointments();


        verify(request)
                .setAttribute(
                        "error",
                        "Unable to retrieve appointments."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - FORWARD TO CORRECT JSP
    @Test
    void testDoGetForwardsToViewAppointmentJsp()
            throws Exception {

        when(appointmentService.getAllAppointments())
                .thenReturn(
                        Arrays.asList()
                );


        controller.callDoGet(
                request,
                response
        );


        verify(request)
                .getRequestDispatcher(
                        "/ViewAppointment.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // POST - SHOULD CALL doGet()
    @Test
    void testDoPost()
            throws Exception {

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatientId(10);
        appointment.setPatientName("Karan");
        appointment.setDentistName("Dr. Smith");
        appointment.setTreatmentType("Cleaning");
        appointment.setAppointmentDate("2026-08-10");
        appointment.setAppointmentTime("10:00:00");


        List<Appointment> appointments =
                Arrays.asList(appointment);


        when(appointmentService.getAllAppointments())
                .thenReturn(appointments);


        controller.callDoPost(
                request,
                response
        );


        verify(appointmentService)
                .getAllAppointments();


        verify(request)
                .setAttribute(
                        "appointments",
                        appointments
                );


        verify(request)
                .getRequestDispatcher(
                        "/ViewAppointment.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - SERVICE CALLED ONLY ONCE
    @Test
    void testDoGetServiceCalledOnce()
            throws Exception {

        when(appointmentService.getAllAppointments())
                .thenReturn(
                        Arrays.asList()
                );


        controller.callDoGet(
                request,
                response
        );


        verify(
                appointmentService,
                times(1)
        ).getAllAppointments();
    }


    // GET - APPOINTMENT DATA IS CORRECT
    @Test
    void testAppointmentData()
            throws Exception {

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentId(5);
        appointment.setPatientId(20);
        appointment.setPatientName("Michael");
        appointment.setDentistName("Dr. Pink");
        appointment.setTreatmentType("Root Canal");
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("14:30:00");


        List<Appointment> appointments =
                Arrays.asList(appointment);


        when(appointmentService.getAllAppointments())
                .thenReturn(appointments);


        controller.callDoGet(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        eq("appointments"),
                        argThat(value -> {

                            List<?> list =
                                    (List<?>) value;

                            if (list.size() != 1) {
                                return false;
                            }

                            Appointment result =
                                    (Appointment) list.get(0);

                            return result.getAppointmentId() == 5
                                    && result.getPatientId() == 20
                                    && result.getPatientName()
                                            .equals("Michael")
                                    && result.getDentistName()
                                            .equals("Dr. Pink")
                                    && result.getTreatmentType()
                                            .equals("Root Canal")
                                    && result.getAppointmentDate()
                                            .equals("2026-08-15")
                                    && result.getAppointmentTime()
                                            .equals("14:30:00");
                        })
                );
    }


    // GET - REQUEST DISPATCHER IS USED
    @Test
    void testDispatcherForwarded()
            throws Exception {

        when(appointmentService.getAllAppointments())
                .thenReturn(
                        Arrays.asList()
                );


        controller.callDoGet(
                request,
                response
        );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verifyNoMoreInteractions(dispatcher);
    }
}
