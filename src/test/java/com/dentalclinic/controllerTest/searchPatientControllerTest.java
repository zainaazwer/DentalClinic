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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.SearchPatientController;
import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

public class searchPatientControllerTest {

private TestableSearchPatientController controller;
private PatientService patientService;

private HttpServletRequest request;
private HttpServletResponse response;
private RequestDispatcher dispatcher;


private static class TestableSearchPatientController
        extends SearchPatientController {

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

    controller = new TestableSearchPatientController();

    patientService = mock(PatientService.class);
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    dispatcher = mock(RequestDispatcher.class);

    
    Field serviceField =
            SearchPatientController.class
                    .getDeclaredField("patientService");

    serviceField.setAccessible(true);
    serviceField.set(controller, patientService);


    // Mock RequestDispatcher
    when(request.getRequestDispatcher(anyString()))
            .thenReturn(dispatcher);
}

// VALID PATIENT ID
@Test
void testSearchPatientFound()
        throws Exception {

    when(request.getParameter("patientId"))
            .thenReturn("1");


    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );


    when(patientService.getPatientById(1))
            .thenReturn(patient);


    controller.callDoGet(request, response);


    // Check service was called
    verify(patientService)
            .getPatientById(1);


    // Check patient was stored in request
    verify(request)
            .setAttribute("patient", patient);


    // Check SearchPatient.jsp was opened
    verify(request)
            .getRequestDispatcher("/SearchPatient.jsp");


    // Check forwarding
    verify(dispatcher)
            .forward(request, response);


    // Error should not be set
    verify(request, never())
            .setAttribute(
                    eq("error"),
                    anyString()
            );
}

// PATIENT NOT FOUND
@Test
void testSearchPatientNotFound()
        throws Exception {

    when(request.getParameter("patientId"))
            .thenReturn("99");


    when(patientService.getPatientById(99))
            .thenReturn(null);


    controller.callDoGet(request, response);


    // Check service was called
    verify(patientService)
            .getPatientById(99);


    // Patient should be stored as null
    verify(request)
            .setAttribute(
                    "patient",
                    null
            );


    // Error message should be set
    verify(request)
            .setAttribute(
                    "error",
                    "Patient not found."
            );


    // Check page forwarding
    verify(request)
            .getRequestDispatcher(
                    "/SearchPatient.jsp"
            );

    verify(dispatcher)
            .forward(request, response);
}

// INVALID PATIENT ID
@Test
void testInvalidPatientId()
        throws Exception {

    when(request.getParameter("patientId"))
            .thenReturn("abc");


    controller.callDoGet(request, response);


    // Service should NOT be called
    verify(patientService, never())
            .getPatientById(anyInt());


    // Error should be displayed
    verify(request)
            .setAttribute(
                    "error",
                    "Invalid Patient ID."
            );


    // Page should still be displayed
    verify(request)
            .getRequestDispatcher(
                    "/SearchPatient.jsp"
            );

    verify(dispatcher)
            .forward(request, response);
}

// EMPTY PATIENT ID
@Test
void testEmptyPatientId()
        throws Exception {

    when(request.getParameter("patientId"))
            .thenReturn("");


    controller.callDoGet(request, response);


    // Service should not be called
    verify(patientService, never())
            .getPatientById(anyInt());


    // Search page should still be displayed
    verify(request)
            .getRequestDispatcher(
                    "/SearchPatient.jsp"
            );

    verify(dispatcher)
            .forward(request, response);
}

// NULL PATIENT ID
@Test
void testNullPatientId()
        throws Exception {

    when(request.getParameter("patientId"))
            .thenReturn(null);


    controller.callDoGet(request, response);


    // Service should not be called
    verify(patientService, never())
            .getPatientById(anyInt());


    // Search page should still be displayed
    verify(request)
            .getRequestDispatcher(
                    "/SearchPatient.jsp"
            );

    verify(dispatcher)
            .forward(request, response);
}

// DATABASE ERROR
@Test
void testDatabaseError()
        throws Exception {

    when(request.getParameter("patientId"))
            .thenReturn("1");


    when(patientService.getPatientById(1))
            .thenThrow(
                    new SQLException(
                            "Database connection error"
                    )
            );


    controller.callDoGet(request, response);


    // Check database error message
    verify(request)
            .setAttribute(
                    "error",
                    "Unable to search patient."
            );


    // Search page should still be displayed
    verify(request)
            .getRequestDispatcher(
                    "/SearchPatient.jsp"
            );

    verify(dispatcher)
            .forward(request, response);
}

// TEST POST REQUEST
@Test
void testDoPostCallsSearch()
        throws Exception {

    when(request.getParameter("patientId"))
            .thenReturn("1");


    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );


    when(patientService.getPatientById(1))
            .thenReturn(patient);


    controller.callDoPost(request, response);


    // doPost() calls doGet(), so the service should be called
    verify(patientService)
            .getPatientById(1);


    // Patient should be placed in request
    verify(request)
            .setAttribute(
                    "patient",
                    patient
            );


    // Page should be forwarded
    verify(dispatcher)
            .forward(request, response);
}

}
