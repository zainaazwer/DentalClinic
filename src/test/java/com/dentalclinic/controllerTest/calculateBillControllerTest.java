package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.CalculateBillController;
import com.dentalclinic.dao.BillDAO;
import com.dentalclinic.dao.PatientDAO;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Bill;
import com.dentalclinic.model.Patient;
import com.dentalclinic.service.AppointmentService;

public class calculateBillControllerTest {

    private TestableCalculateBillController controller;

    private AppointmentService appointmentService;
    private BillDAO billDAO;
    private PatientDAO patientDAO;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {

        controller = new TestableCalculateBillController();

        appointmentService = mock(AppointmentService.class);
        billDAO = mock(BillDAO.class);
        patientDAO = mock(PatientDAO.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(anyString()))
                .thenReturn(dispatcher);


        Field appointmentServiceField =
                CalculateBillController.class
                        .getDeclaredField("appointmentService");

        appointmentServiceField.setAccessible(true);

        appointmentServiceField.set(
                controller,
                appointmentService
        );
    }


    // GET TEST
    @Test
    void testDoGet() throws Exception {

        controller.callDoGet(request, response);

        verify(request)
                .getRequestDispatcher("/CalculateBill.jsp");

        verify(dispatcher)
                .forward(request, response);
    }


    // POST - SUCCESS
    @Test
    void testDoPostSuccess() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("1");

        when(request.getParameter("amountPaid"))
                .thenReturn("150");

        when(request.getParameter("paymentMethod"))
                .thenReturn("CASH");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatientId(10);
        appointment.setPatientName("Jenny Perera");
        appointment.setTreatmentType("Cleaning");


        when(appointmentService.getAppointmentById(1))
                .thenReturn(appointment);


        Patient patient = new Patient();

        patient.setPatientId(10);
        patient.setFullName("Jenny Perera");
        patient.setPhoneNumber("0771234567");


        controller.callDoPost(request, response);


        verify(appointmentService)
                .getAppointmentById(1);


        verify(request)
                .getRequestDispatcher("/PrintBill.jsp");

        verify(dispatcher)
                .forward(request, response);


        verify(request)
                .setAttribute(
                        eq("bill"),
                        any(Bill.class)
                );
    }

    // POST - APPOINTMENT NOT FOUND
    @Test
    void testDoPostAppointmentNotFound() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("99");


        when(appointmentService.getAppointmentById(99))
                .thenReturn(null);


        controller.callDoPost(request, response);


        verify(appointmentService)
                .getAppointmentById(99);


        verify(request)
                .setAttribute(
                        "error",
                        "Appointment not found."
                );


        verify(request)
                .getRequestDispatcher("/CalculateBill.jsp");


        verify(dispatcher)
                .forward(request, response);
    }

    // POST - INVALID APPOINTMENT ID
    @Test
    void testDoPostInvalidAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("abc");


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        "error",
                        "Invalid amount entered."
                );


        verify(request)
                .getRequestDispatcher("/PrintBill.jsp");


        verify(dispatcher)
                .forward(request, response);


        verify(appointmentService, never())
                .getAppointmentById(anyInt());
    }


    // POST - NULL APPOINTMENT ID
    @Test
    void testDoPostNullAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn(null);


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        "error",
                        "Invalid amount entered."
                );


        verify(request)
                .getRequestDispatcher("/PrintBill.jsp");


        verify(dispatcher)
                .forward(request, response);
    }


    // POST - EMPTY APPOINTMENT ID
    @Test
    void testDoPostEmptyAppointmentId() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("");


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        "error",
                        "Invalid amount entered."
                );


        verify(request)
                .getRequestDispatcher("/PrintBill.jsp");


        verify(dispatcher)
                .forward(request, response);
    }

    // POST - INVALID AMOUNT
    @Test
    void testDoPostInvalidAmount() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("1");

        when(request.getParameter("amountPaid"))
                .thenReturn("abc");

        when(request.getParameter("paymentMethod"))
                .thenReturn("CASH");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatientId(10);
        appointment.setPatientName("Jenny Perera");
        appointment.setTreatmentType("Cleaning");


        when(appointmentService.getAppointmentById(1))
                .thenReturn(appointment);


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        "error",
                        "Invalid amount entered."
                );


        verify(request)
                .getRequestDispatcher("/PrintBill.jsp");


        verify(dispatcher)
                .forward(request, response);
    }


    // POST - DIFFERENT TREATMENT: FILLING
    @Test
    void testDoPostFilling() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("2");

        when(request.getParameter("amountPaid"))
                .thenReturn("200");

        when(request.getParameter("paymentMethod"))
                .thenReturn("CARD");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(2);
        appointment.setPatientId(20);
        appointment.setPatientName("Jenny Perera");
        appointment.setTreatmentType("Filling");


        when(appointmentService.getAppointmentById(2))
                .thenReturn(appointment);


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        eq("bill"),
                        any(Bill.class)
                );


        verify(dispatcher)
                .forward(request, response);
    }

    // POST - EXTRACTION
    @Test
    void testDoPostExtraction() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("3");

        when(request.getParameter("amountPaid"))
                .thenReturn("250");

        when(request.getParameter("paymentMethod"))
                .thenReturn("CASH");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(3);
        appointment.setPatientId(30);
        appointment.setPatientName("Jenny Perera");

        appointment.setTreatmentType("Extraction");


        when(appointmentService.getAppointmentById(3))
                .thenReturn(appointment);


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        eq("bill"),
                        any(Bill.class)
                );


        verify(dispatcher)
                .forward(request, response);
    }

    // POST - BRACES
    @Test
    void testDoPostBraces() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("4");

        when(request.getParameter("amountPaid"))
                .thenReturn("500");

        when(request.getParameter("paymentMethod"))
                .thenReturn("CARD");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(4);
        appointment.setPatientId(40);
        appointment.setPatientName("Jenny Perera");

        appointment.setTreatmentType("Braces");


        when(appointmentService.getAppointmentById(4))
                .thenReturn(appointment);


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        eq("bill"),
                        any(Bill.class)
                );


        verify(dispatcher)
                .forward(request, response);
    }

    // POST - DATABASE ERROR
    @Test
    void testDoPostDatabaseError() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("5");

        when(request.getParameter("amountPaid"))
                .thenReturn("100");

        when(request.getParameter("paymentMethod"))
                .thenReturn("CASH");


        when(appointmentService.getAppointmentById(5))
                .thenThrow(
                        new SQLException("Database error")
                );


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        "error",
                        "Database error while saving bill."
                );


        verify(request)
                .getRequestDispatcher("/PrintBill.jsp");


        verify(dispatcher)
                .forward(request, response);
    }

    // POST - UNKNOWN TREATMENT
    @Test
    void testDoPostUnknownTreatment() throws Exception {

        when(request.getParameter("appointmentId"))
                .thenReturn("6");

        when(request.getParameter("amountPaid"))
                .thenReturn("50");

        when(request.getParameter("paymentMethod"))
                .thenReturn("CASH");


        Appointment appointment = new Appointment();

        appointment.setAppointmentId(6);
        appointment.setPatientId(60);
        appointment.setPatientName("Test Patient");
        appointment.setTreatmentType("Unknown");


        when(appointmentService.getAppointmentById(6))
                .thenReturn(appointment);


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        eq("bill"),
                        any(Bill.class)
                );


        verify(dispatcher)
                .forward(request, response);
    }

    // TESTABLE SUBCLASS
    private static class TestableCalculateBillController
            extends CalculateBillController {

        public void callDoGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            super.doGet(request, response);
        }


        public void callDoPost(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            super.doPost(request, response);
        }
    }
}
