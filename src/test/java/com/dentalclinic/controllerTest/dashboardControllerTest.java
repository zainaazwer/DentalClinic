package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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

// NO SESSION
@Test
public void testNoSessionRedirectsToLogin()
        throws ServletException, IOException {

    when(request.getSession(false))
            .thenReturn(null);

    controller.callDoGet(request, response);

    verify(response)
            .sendRedirect("Login.jsp");

    verify(request, never())
            .getRequestDispatcher("/Dashboard.jsp");
}

// SESSION EXISTS BUT NO USER
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

    verify(request, never())
            .getRequestDispatcher("/Dashboard.jsp");
}

// LOGGED-IN USER IS RETRIEVED
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

    // DashboardController calls getAttribute("user") twice
    verify(session, times(2))
            .getAttribute("user");
}

// FULL NAME IS SET
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
            .setAttribute(
                    "fullName",
                    "Admin"
            );
}

// ROLE IS SET
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
            .setAttribute(
                    "role",
                    "Admin"
            );
}

// DASHBOARD JSP IS REQUESTED
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

// DASHBOARD JSP IS FORWARDED
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
            .forward(
                    request,
                    response
            );
}

// ADMIN CAN ACCESS DASHBOARD
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
            .forward(
                    request,
                    response
            );
}

}
