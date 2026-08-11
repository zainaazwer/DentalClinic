package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.HelpController;

public class helpControllerTest {

    private TestableHelpController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;


    // Testable subclass
    private static class TestableHelpController
            extends HelpController {

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
    public void setUp() {

        controller = new TestableHelpController();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher("/help.jsp"))
                .thenReturn(dispatcher);
    }


    // doGet forwards to help.jsp
    @Test
    public void testDoGetForwardsToHelpPage()
            throws ServletException, IOException {

        controller.callDoGet(request, response);

        verify(request)
                .getRequestDispatcher("/help.jsp");

        verify(dispatcher)
                .forward(request, response);
    }


    // doGet sets instructions
    @Test
    public void testDoGetSetsInstructions()
            throws ServletException, IOException {

        controller.callDoGet(request, response);

        verify(request).setAttribute(
                eq("instructions"),
                any(String[].class)
        );
    }


    // Instructions contain 8 steps
    @Test
    public void testInstructionsCount()
            throws ServletException, IOException {

        controller.callDoGet(request, response);

        verify(request).setAttribute(
                eq("instructions"),
                argThat(value ->
                        value instanceof String[]
                        && ((String[]) value).length == 8
                )
        );
    }


    // First instruction is login
    @Test
    public void testFirstInstructionIsLogin()
            throws ServletException, IOException {

        controller.callDoGet(request, response);

        verify(request).setAttribute(
                eq("instructions"),
                argThat(value -> {

                    if (!(value instanceof String[])) {
                        return false;
                    }

                    String[] instructions =
                            (String[]) value;

                    return instructions[0]
                            .toLowerCase()
                            .contains("login");
                })
        );
    }


    // Instructions contain appointment registration
    @Test
    public void testAppointmentInstruction()
            throws ServletException, IOException {

        controller.callDoGet(request, response);

        verify(request).setAttribute(
                eq("instructions"),
                argThat(value -> {

                    if (!(value instanceof String[])) {
                        return false;
                    }

                    String[] instructions =
                            (String[]) value;

                    return instructions[2]
                            .toLowerCase()
                            .contains("appointment");
                })
        );
    }


    // Instructions contain search appointment
    @Test
    public void testSearchAppointmentInstruction()
            throws ServletException, IOException {

        controller.callDoGet(request, response);

        verify(request).setAttribute(
                eq("instructions"),
                argThat(value -> {

                    if (!(value instanceof String[])) {
                        return false;
                    }

                    String[] instructions =
                            (String[]) value;

                    return instructions[3]
                            .toLowerCase()
                            .contains("search");
                })
        );
    }


    // Instructions contain billing
    @Test
    public void testBillingInstruction()
            throws ServletException, IOException {

        controller.callDoGet(request, response);

        verify(request).setAttribute(
                eq("instructions"),
                argThat(value -> {

                    if (!(value instanceof String[])) {
                        return false;
                    }

                    String[] instructions =
                            (String[]) value;

                    return instructions[5]
                            .toLowerCase()
                            .contains("bill");
                })
        );
    }


    // Instructions contain logout
    @Test
    public void testLogoutInstruction()
            throws ServletException, IOException {

        controller.callDoGet(request, response);

        verify(request).setAttribute(
                eq("instructions"),
                argThat(value -> {

                    if (!(value instanceof String[])) {
                        return false;
                    }

                    String[] instructions =
                            (String[]) value;

                    return instructions[7]
                            .toLowerCase()
                            .contains("logout");
                })
        );
    }


    //  doPost forwards to help.jsp
    @Test
    public void testDoPostForwardsToHelpPage()
            throws ServletException, IOException {

        controller.callDoPost(request, response);

        verify(request)
                .getRequestDispatcher("/help.jsp");

        verify(dispatcher)
                .forward(request, response);
    }


    // doPost sets instructions
    @Test
    public void testDoPostSetsInstructions()
            throws ServletException, IOException {

        controller.callDoPost(request, response);

        verify(request).setAttribute(
                eq("instructions"),
                any(String[].class)
        );
    }
}
