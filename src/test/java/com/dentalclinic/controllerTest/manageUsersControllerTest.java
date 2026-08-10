package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.ManageUsersController;
import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;

public class manageUsersControllerTest {

    private ManageUsersController controller;

    private UserDAO userDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {

        controller = new ManageUsersController();

        userDAO = mock(UserDAO.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        Field userDAOField =
                ManageUsersController.class.getDeclaredField("userDAO");

        userDAOField.setAccessible(true);
        userDAOField.set(controller, userDAO);

        // Mock session
        when(request.getSession(false))
                .thenReturn(session);

        // Mock context path
        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");

        // Mock dispatcher
        when(request.getRequestDispatcher(anyString()))
                .thenReturn(dispatcher);

        // Create Admin user
        User admin = new User();

        admin.setUserId(1);
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFullName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setRole("Admin");

        when(session.getAttribute("user"))
                .thenReturn(admin);
    }

    // Helper method to call protected doGet()
    private void callDoGet() throws Exception {

        Method doGetMethod =
                ManageUsersController.class.getDeclaredMethod(
                        "doGet",
                        HttpServletRequest.class,
                        HttpServletResponse.class
                );

        doGetMethod.setAccessible(true);

        doGetMethod.invoke(
                controller,
                request,
                response
        );
    }

    // ADMIN CAN VIEW USERS
    @Test
    void testAdminCanViewUsers() throws Exception {

        User user1 = new User();

        user1.setUserId(1);
        user1.setUsername("Jake");
        user1.setFullName("Jake Drake");
        user1.setEmail("jake@gmail.com");
        user1.setRole("Receptionist");


        User user2 = new User();

        user2.setUserId(2);
        user2.setUsername("harry");
        user2.setFullName("Harry Pura");
        user2.setEmail("Harry@gmail.com");
        user2.setRole("Receptionist");


        List<User> users =
                Arrays.asList(user1, user2);


        when(userDAO.getAllUsers())
                .thenReturn(users);


        callDoGet();


        // DAO should be called
        verify(userDAO)
                .getAllUsers();


        // Users should be placed in request
        verify(request)
                .setAttribute(
                        "users",
                        users
                );


        // Correct JSP should be opened
        verify(request)
                .getRequestDispatcher(
                        "ManageUsers.jsp"
                );


        // JSP should receive request and response
        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        // No redirect should happen
        verify(response, never())
                .sendRedirect(anyString());
    }

    // ADMIN WITH NO USERS
    @Test
    void testAdminWithNoUsers() throws Exception {

        when(userDAO.getAllUsers())
                .thenReturn(Collections.emptyList());


        callDoGet();


        verify(userDAO)
                .getAllUsers();


        verify(request)
                .setAttribute(
                        "users",
                        Collections.emptyList()
                );


        verify(request)
                .getRequestDispatcher(
                        "ManageUsers.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // USER NOT LOGGED IN
    @Test
    void testNotLoggedIn() throws Exception {

        when(request.getSession(false))
                .thenReturn(null);


        callDoGet();


        verify(response)
                .sendRedirect("Login.jsp");


        verify(userDAO, never())
                .getAllUsers();


        verify(request, never())
                .getRequestDispatcher("ManageUsers.jsp");
    }


    // SESSION EXISTS BUT NO USER
    @Test
    void testNoUserInSession() throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(null);


        callDoGet();


        verify(response)
                .sendRedirect("Login.jsp");


        verify(userDAO, never())
                .getAllUsers();
    }

    //  RECEPTIONIST CANNOT ACCESS
    @Test
    void testReceptionistCannotAccess() throws Exception {

        User receptionist = new User();

        receptionist.setUserId(2);
        receptionist.setUsername("reception");
        receptionist.setFullName("Reception");
        receptionist.setRole("Receptionist");


        when(session.getAttribute("user"))
                .thenReturn(receptionist);


        callDoGet();


        verify(response)
                .sendRedirect("Dashboard.jsp");


        verify(userDAO, never())
                .getAllUsers();


        verify(request, never())
                .getRequestDispatcher("ManageUsers.jsp");
    }

    //  ADMINISTRATOR ROLE CAN ACCESS
    @Test
    void testAdministratorRoleCanAccess() throws Exception {

        User administrator = new User();

        administrator.setUserId(1);
        administrator.setUsername("ava");
        administrator.setFullName("Ava Drake");
        administrator.setRole("Admin");


        when(session.getAttribute("user"))
                .thenReturn(administrator);


        List<User> users =
                Collections.singletonList(administrator);


        when(userDAO.getAllUsers())
                .thenReturn(users);


        callDoGet();


        verify(userDAO)
                .getAllUsers();


        verify(request)
                .setAttribute(
                        "users",
                        users
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    //  DATABASE ERROR
    @Test
    void testDatabaseError() throws Exception {

        when(userDAO.getAllUsers())
                .thenThrow(
                        new java.sql.SQLException(
                                "Database connection failed"
                        )
                );


        callDoGet();


        verify(userDAO)
                .getAllUsers();


        verify(request)
                .setAttribute(
                        eq("error"),
                        eq("Database connection failed")
                );


        verify(request)
                .getRequestDispatcher(
                        "ManageUsers.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // ADMIN ROLE CASE INSENSITIVE
    @Test
    void testAdminRoleCaseInsensitive() throws Exception {

        User admin = new User();

        admin.setUserId(1);
        admin.setUsername("admin");
        admin.setFullName("Admin");
        admin.setRole("admin");


        when(session.getAttribute("user"))
                .thenReturn(admin);


        when(userDAO.getAllUsers())
                .thenReturn(Collections.emptyList());


        callDoGet();


        verify(userDAO)
                .getAllUsers();


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }
}
