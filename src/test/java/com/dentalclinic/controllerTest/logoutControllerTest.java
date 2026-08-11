package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.dentalclinic.controller.LogoutController;

public class logoutControllerTest {

    private TestableLogoutController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;

    private HttpSession session;
    private HttpSession newSession;


    @BeforeEach
    void setUp() {

        controller = new TestableLogoutController();

        request = org.mockito.Mockito.mock(
                HttpServletRequest.class);

        response = org.mockito.Mockito.mock(
                HttpServletResponse.class);

        session = org.mockito.Mockito.mock(
                HttpSession.class);

        newSession = org.mockito.Mockito.mock(
                HttpSession.class);
    }


    // ==========================================
    // SUCCESSFUL LOGOUT
    // ==========================================

    @Test
    void testLogoutSuccess() throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(request.getSession())
                .thenReturn(newSession);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");


        controller.callDoGet(request, response);


        // Existing session is invalidated
        verify(session)
                .invalidate();


        // New session is created
        verify(request)
                .getSession();


        // Success message is stored
        verify(newSession)
                .setAttribute(
                        "success",
                        "Logged out successfully."
                );


        // Cookie is added
        verify(response)
                .addCookie(any(Cookie.class));


        // Redirect to Login.jsp
        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );
    }


    // ==========================================
    // NO EXISTING SESSION
    // ==========================================

    @Test
    void testLogoutWithoutExistingSession()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(null);

        when(request.getSession())
                .thenReturn(newSession);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");


        controller.callDoGet(request, response);


        // No old session exists
        verifyNoInteractions(session);


        // New session is created
        verify(request)
                .getSession();


        // Success message is stored
        verify(newSession)
                .setAttribute(
                        "success",
                        "Logged out successfully."
                );


        // Cookie is removed
        verify(response)
                .addCookie(any(Cookie.class));


        // Redirect to Login.jsp
        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );
    }


    // ==========================================
    // SESSION INVALIDATION
    // ==========================================

    @Test
    void testSessionIsInvalidated()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(request.getSession())
                .thenReturn(newSession);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");


        controller.callDoGet(request, response);


        verify(session)
                .invalidate();
    }


    // ==========================================
    // SUCCESS MESSAGE
    // ==========================================

    @Test
    void testLogoutSuccessMessage()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(request.getSession())
                .thenReturn(newSession);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");


        controller.callDoGet(request, response);


        verify(newSession)
                .setAttribute(
                        "success",
                        "Logged out successfully."
                );
    }


    // ==========================================
    // USERNAME COOKIE REMOVAL
    // ==========================================

    @Test
    void testUsernameCookieIsRemoved()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(request.getSession())
                .thenReturn(newSession);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");


        controller.callDoGet(request, response);


        // Capture cookie
        ArgumentCaptor<Cookie> cookieCaptor =
                ArgumentCaptor.forClass(Cookie.class);


        verify(response)
                .addCookie(cookieCaptor.capture());


        Cookie cookie =
                cookieCaptor.getValue();


        // Check cookie name
        assertEquals(
                "username",
                cookie.getName()
        );


        // Check cookie value
        assertEquals(
                "",
                cookie.getValue()
        );


        // Max age 0 means cookie is deleted
        assertEquals(
                0,
                cookie.getMaxAge()
        );


        // Check cookie path
        assertEquals(
                "/SunriseDentalClinic",
                cookie.getPath()
        );
    }


    // ==========================================
    // REDIRECT
    // ==========================================

    @Test
    void testLogoutRedirect()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(request.getSession())
                .thenReturn(newSession);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");


        controller.callDoGet(request, response);


        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );
    }


    // ==========================================
    // TESTABLE CONTROLLER
    // ==========================================

    private static class TestableLogoutController
            extends LogoutController {

        public void callDoGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            super.doGet(request, response);
        }
    }
}
