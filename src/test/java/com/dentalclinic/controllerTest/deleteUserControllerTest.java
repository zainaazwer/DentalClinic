package com.dentalclinic.controllerTest;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.DeleteUserController;
import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;

public class deleteUserControllerTest {

    private TestableDeleteUserController controller;

    private UserDAO userDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() throws Exception {

        controller = new TestableDeleteUserController();

        userDAO = mock(UserDAO.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        // Replace the real UserDAO with Mockito mock
        Field userDAOField =
                DeleteUserController.class.getDeclaredField("userDAO");

        userDAOField.setAccessible(true);
        userDAOField.set(controller, userDAO);

        // Create logged-in Admin
        User admin = new User();

        admin.setUserId(1);
        admin.setUsername("admin");
        admin.setFullName("Admin");
        admin.setRole("Admin");

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("user"))
                .thenReturn(admin);

        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");
    }


    // SUCCESSFUL DELETE
    @Test
    void testDeleteUserSuccess() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        User user = new User();

        user.setUserId(5);
        user.setUsername("staff");
        user.setFullName("Staff");
        user.setRole("Receptionist");

        when(userDAO.getUserById(5))
                .thenReturn(user);

        when(userDAO.deleteUser(5))
                .thenReturn(true);

        controller.callDoGet(request, response);

        verify(userDAO)
                .getUserById(5);

        verify(userDAO)
                .deleteUser(5);

        verify(session)
                .setAttribute(
                        "success",
                        "User deleted successfully."
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    // USER NOT FOUND 
    @Test
    void testDeleteUserNotFound() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("99");

        when(userDAO.getUserById(99))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(userDAO)
                .getUserById(99);

        verify(userDAO, never())
                .deleteUser(anyInt());

        verify(session)
                .setAttribute(
                        "error",
                        "User not found."
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    // EMPTY USER ID
    @Test
    void testDeleteUserEmptyId() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("");

        controller.callDoGet(request, response);

        verify(session)
                .setAttribute(
                        "error",
                        "Invalid User ID."
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .deleteUser(anyInt());

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    // NULL USER ID
    @Test
    void testDeleteUserNullId() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(session)
                .setAttribute(
                        "error",
                        "Invalid User ID."
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .deleteUser(anyInt());

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    // INVALID USER ID FORMAT
    @Test
    void testDeleteUserInvalidId() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("abc");

        controller.callDoGet(request, response);

        verify(session)
                .setAttribute(
                        "error",
                        "Invalid User ID."
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .deleteUser(anyInt());

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    // DAO DELETE FAILURE
    @Test
    void testDeleteUserFailure() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        User user = new User();

        user.setUserId(5);
        user.setUsername("staff");
        user.setFullName("Staff");
        user.setRole("Receptionist");

        when(userDAO.getUserById(5))
                .thenReturn(user);

        when(userDAO.deleteUser(5))
                .thenReturn(false);

        controller.callDoGet(request, response);

        verify(userDAO)
                .getUserById(5);

        verify(userDAO)
                .deleteUser(5);

        verify(session)
                .setAttribute(
                        "error",
                        "Unable to delete user."
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    // NOT LOGGED IN
    @Test
    void testDeleteUserNotLoggedIn() throws Exception {

        when(request.getSession(false))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .deleteUser(anyInt());
    }

    // SESSION HAS NO USER
    @Test
    void testDeleteUserNoUserInSession() throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .deleteUser(anyInt());
    }


    // NON ADMIN USER
    @Test
    void testDeleteUserNonAdmin() throws Exception {

        User receptionist = new User();

        receptionist.setUserId(2);
        receptionist.setUsername("reception");
        receptionist.setRole("Receptionist");

        when(session.getAttribute("user"))
                .thenReturn(receptionist);

        controller.callDoGet(request, response);

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Dashboard.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .deleteUser(anyInt());
    }


    // DATABASE / DAO ERROR
    @Test
    void testDeleteUserException() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        when(userDAO.getUserById(5))
                .thenThrow(
                        new RuntimeException("Database error")
                );

        controller.callDoGet(request, response);

        verify(session)
                .setAttribute(
                        "error",
                        "Error deleting user."
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    // HELPER CLASS
    private static class TestableDeleteUserController
            extends DeleteUserController {

        public void callDoGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws Exception {

            super.doGet(request, response);
        }
    }
}
