package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.AddPatientController;
import com.dentalclinic.service.PatientService;

public class addPatientControllerTest {

private TestableAddPatientController controller;
private PatientService patientService;

private HttpServletRequest request;
private HttpServletResponse response;
private HttpSession session;
private RequestDispatcher dispatcher;

private static class TestableAddPatientController
        extends AddPatientController {

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


@BeforeEach
void setUp() throws Exception {

    controller = new TestableAddPatientController();

    patientService = mock(PatientService.class);
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    session = mock(HttpSession.class);
    dispatcher = mock(RequestDispatcher.class);

    Field serviceField =
            AddPatientController.class
                    .getDeclaredField("patientService");

    serviceField.setAccessible(true);
    serviceField.set(controller, patientService);


    // Mock session
    when(request.getSession())
            .thenReturn(session);


    // Mock RequestDispatcher
    when(request.getRequestDispatcher(anyString()))
            .thenReturn(dispatcher);
}

// TEST doGet()
@Test
void testDoGetDisplaysAddPatientPage()
        throws Exception {

    controller.callDoGet(request, response);

    verify(request)
            .getRequestDispatcher("AddPatient.jsp");

    verify(dispatcher)
            .forward(request, response);
}


// TEST SUCCESSFUL REGISTRATION
@Test
void testSuccessfulPatientRegistration()
        throws Exception {

    // Mock form data
    when(request.getParameter("fullName"))
            .thenReturn("John Weera");

    when(request.getParameter("address"))
            .thenReturn("Colombo");

    when(request.getParameter("phoneNumber"))
            .thenReturn("0771234567");


    // Tell Mockito registration is successful
    when(patientService.registerPatient(any()))
            .thenReturn(true);


    // Call controller
    controller.callDoPost(request, response);


    // Check PatientService was called
    verify(patientService)
            .registerPatient(any());


    // Check success message
    verify(session)
            .setAttribute(
                    "success",
                    "Patient registered successfully."
            );


    // Check Dashboard.jsp was requested
    verify(request)
            .getRequestDispatcher("Dashboard.jsp");


    // Check forwarding
    verify(dispatcher)
            .forward(request, response);
}

// TEST FAILED REGISTRATION
@Test
void testFailedPatientRegistration()
        throws Exception {

    // Mock form data
    when(request.getParameter("fullName"))
            .thenReturn("John Weera");

    when(request.getParameter("address"))
            .thenReturn("Colombo");

    when(request.getParameter("phoneNumber"))
            .thenReturn("0771234567");


    // Tell Mockito registration failed
    when(patientService.registerPatient(any()))
            .thenReturn(false);


    // Call controller
    controller.callDoPost(request, response);


    // Check service was called
    verify(patientService)
            .registerPatient(any());


    // Check error message
    verify(request)
            .setAttribute(
                    "error",
                    "Failed to register patient."
            );


    // Check AddPatient.jsp was requested
    verify(request)
            .getRequestDispatcher("AddPatient.jsp");


    // Check forwarding
    verify(dispatcher)
            .forward(request, response);
}

// TEST DATABASE ERROR
@Test
void testDatabaseError()
        throws Exception {

    // Mock form data
    when(request.getParameter("fullName"))
            .thenReturn("John Weera");

    when(request.getParameter("address"))
            .thenReturn("Colombo");

    when(request.getParameter("phoneNumber"))
            .thenReturn("0771234567");


    // Simulate SQLException
    when(patientService.registerPatient(any()))
            .thenThrow(
                    new SQLException(
                            "Database connection error"
                    )
            );


    // Call controller
    controller.callDoPost(request, response);


    // Check database error message
    verify(request)
            .setAttribute(
                    "error",
                    "Database error: Database connection error"
            );


    // Check AddPatient.jsp was requested
    verify(request)
            .getRequestDispatcher("AddPatient.jsp");


    // Check forwarding
    verify(dispatcher)
            .forward(request, response);
}

// TEST UNEXPECTED ERROR
@Test
void testUnexpectedError()
        throws Exception {

    // Mock form data
    when(request.getParameter("fullName"))
            .thenReturn("John Weera");

    when(request.getParameter("address"))
            .thenReturn("Colombo");

    when(request.getParameter("phoneNumber"))
            .thenReturn("0771234567");


    // Simulate unexpected exception
    when(patientService.registerPatient(any()))
            .thenThrow(
                    new RuntimeException(
                            "Unexpected error"
                    )
            );


    // Call controller
    controller.callDoPost(request, response);


    // Check unexpected error message
    verify(request)
            .setAttribute(
                    "error",
                    "An unexpected error occurred."
            );


    // Check AddPatient.jsp was requested
    verify(request)
            .getRequestDispatcher("AddPatient.jsp");


    // Check forwarding
    verify(dispatcher)
            .forward(request, response);
}
}
