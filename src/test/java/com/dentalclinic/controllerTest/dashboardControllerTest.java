package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.DashboardController;
import com.dentalclinic.model.User;

public class dashboardControllerTest {

    private TestableDashboardController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;


    // Testable subclass to access protected doGet()
    private static class TestableDashboardController
            extends DashboardController {

        public void callDoGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            super.doGet(request, response);
        }
    }


    @BeforeEach
    public void setUp() {

        controller = new TestableDashboardController();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher("/Dashboard.jsp"))
                .thenReturn(dispatcher);
    }


    // No session redirects to Login.jsp
    @Test
    public void testNoSessionRedirectsToLogin()
            throws ServletException, IOException {

        when(request.getSession(false))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(response)
                .sendRedirect("Login.jsp");
    }


    // Session exists but user is not logged in
    @Test
    public void testNoUserInSessionRedirectsToLogin()
            throws ServletException, IOException {

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("user"))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(response)
                .sendRedirect("Login.jsp");
    }


    // Logged-in user is retrieved from session
    @Test
    public void testLoggedInUserIsRetrieved()
            throws ServletException, IOException {

        User user = new User();

        user.setFullName("Admin");
        user.setRole("Admin");

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("user"))
                .thenReturn(user);

        controller.callDoGet(request, response);

        verify(session)
                .getAttribute("user");
    }


    // Full name is added to request
    @Test
    public void testFullNameIsSet()
            throws ServletException, IOException {

        User user = new User();

        user.setFullName("Admin");
        user.setRole("Admin");

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("user"))
                .thenReturn(user);

        controller.callDoGet(request, response);

        verify(request)
                .setAttribute("fullName", "Admin");
    }


    // Role is added to request
    @Test
    public void testRoleIsSet()
            throws ServletException, IOException {

        User user = new User();

        user.setFullName("Admin");
        user.setRole("Admin");

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("user"))
                .thenReturn(user);

        controller.callDoGet(request, response);

        verify(request)
                .setAttribute("role", "Admin");
    }


    // Dashboard JSP is requested
    @Test
    public void testDashboardPageIsRequested()
            throws ServletException, IOException {

        User user = new User();

        user.setFullName("Admin");
        user.setRole("Admin");

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("user"))
                .thenReturn(user);

        controller.callDoGet(request, response);

        verify(request)
                .getRequestDispatcher("/Dashboard.jsp");
    }


    // Logged-in user is forwarded to Dashboard.jsp
    @Test
    public void testDashboardPageIsForwarded()
            throws ServletException, IOException {

        User user = new User();

        user.setFullName("Admin");
        user.setRole("Admin");

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("user"))
                .thenReturn(user);

        controller.callDoGet(request, response);

        verify(dispatcher)
                .forward(request, response);
    }


    // Admin user can access dashboard
    @Test
    public void testAdminCanAccessDashboard()
            throws ServletException, IOException {

        User user = new User();

        user.setFullName("Admin");
        user.setRole("Admin");

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("user"))
                .thenReturn(user);

        controller.callDoGet(request, response);

        verify(response, never())
                .sendRedirect("Login.jsp");

        verify(dispatcher)
                .forward(request, response);
    }
}
