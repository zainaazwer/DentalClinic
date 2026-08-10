package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.EditUserController;
import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;

public class editUserControllerTest {

    private TestableEditUserController controller;

    private UserDAO userDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {

        controller = new TestableEditUserController();

        userDAO = mock(UserDAO.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        Field userDAOField =
                EditUserController.class.getDeclaredField("userDAO");

        userDAOField.setAccessible(true);
        userDAOField.set(controller, userDAO);

        // Mock session
        when(request.getSession(false))
                .thenReturn(session);

        // Mock context path
        when(request.getContextPath())
                .thenReturn("/SunriseDentalClinic");

        // Mock RequestDispatcher
        when(request.getRequestDispatcher(anyString()))
                .thenReturn(dispatcher);

        // Logged-in Admin
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

    private static class TestableEditUserController
            extends EditUserController {

        public void callDoGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, java.io.IOException {

            super.doGet(request, response);
        }

        public void callDoPost(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, java.io.IOException {

            super.doPost(request, response);
        }
    }

    // GET TESTS
    @Test
    void testDoGetAdminSuccess() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        User user = new User();

        user.setUserId(5);
        user.setUsername("harry");
        user.setPassword("harry");
        user.setFullName("Harry Pura");
        user.setEmail("harry@gmail.com");
        user.setRole("Receptionist");

        when(userDAO.getUserById(5))
                .thenReturn(user);

        controller.callDoGet(
                request,
                response
        );

        verify(userDAO)
                .getUserById(5);

        verify(request)
                .setAttribute(
                        "user",
                        user
                );

        verify(request)
                .getRequestDispatcher(
                        "EditUser.jsp"
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    @Test
    void testDoGetNotLoggedIn() throws Exception {

        when(request.getSession(false))
                .thenReturn(null);

        controller.callDoGet(
                request,
                response
        );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());
    }


    @Test
    void testDoGetNoUserInSession() throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(null);

        controller.callDoGet(
                request,
                response
        );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());
    }


    @Test
    void testDoGetNonAdmin() throws Exception {

        User receptionist = new User();

        receptionist.setUserId(2);
        receptionist.setUsername("harry");
        receptionist.setFullName("Harry Pura");
        receptionist.setRole("Receptionist");

        when(session.getAttribute("user"))
                .thenReturn(receptionist);

        controller.callDoGet(
                request,
                response
        );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Dashboard.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());
    }


    @Test
    void testDoGetMissingUserId() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn(null);

        controller.callDoGet(
                request,
                response
        );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );

        verify(userDAO, never())
                .getUserById(anyInt());
    }


    @Test
    void testDoGetEmptyUserId() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("");

        controller.callDoGet(
                request,
                response
        );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );

        verify(userDAO, never())
                .getUserById(anyInt());
    }


    @Test
    void testDoGetUserNotFound() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("99");

        when(userDAO.getUserById(99))
                .thenReturn(null);

        controller.callDoGet(
                request,
                response
        );

        verify(userDAO)
                .getUserById(99);

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


    @Test
    void testDoGetInvalidUserId() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("abc");

        controller.callDoGet(
                request,
                response
        );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );

        verify(userDAO, never())
                .getUserById(anyInt());
    }


    @Test
    void testDoGetDatabaseError() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        when(userDAO.getUserById(5))
                .thenThrow(
                        new RuntimeException(
                                "Database error"
                        )
                );

        controller.callDoGet(
                request,
                response
        );

        verify(userDAO)
                .getUserById(5);

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }

    // POST TESTS
    @Test
    void testDoPostSuccessfulUpdate() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        when(request.getParameter("username"))
                .thenReturn("jake");

        when(request.getParameter("fullName"))
                .thenReturn("Jake Drake");

        when(request.getParameter("email"))
                .thenReturn("jake@gmail.com");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");

        when(request.getParameter("password"))
                .thenReturn("newpassword");


        User existingUser = new User();

        existingUser.setUserId(5);
        existingUser.setUsername("oldjake");
        existingUser.setPassword("oldpassword");
        existingUser.setFullName("Old Jake");
        existingUser.setEmail("old@gmail.com");
        existingUser.setRole("Receptionist");


        when(userDAO.getUserById(5))
                .thenReturn(existingUser);

        when(userDAO.updateUser(any(User.class)))
                .thenReturn(true);


        controller.callDoPost(
                request,
                response
        );


        verify(userDAO)
                .getUserById(5);

        verify(userDAO)
                .updateUser(any(User.class));

        verify(session)
                .setAttribute(
                        "success",
                        "User updated successfully."
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    @Test
    void testDoPostKeepsOldPassword() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        when(request.getParameter("username"))
                .thenReturn("jake");

        when(request.getParameter("fullName"))
                .thenReturn("Jake Drake");

        when(request.getParameter("email"))
                .thenReturn("jake@gmail.com");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");

        // Empty password means keep old password
        when(request.getParameter("password"))
                .thenReturn("");


        User existingUser = new User();

        existingUser.setUserId(5);
        existingUser.setUsername("john");
        existingUser.setPassword("oldpassword");
        existingUser.setFullName("Jake Drake");
        existingUser.setEmail("jake@gmail.com");
        existingUser.setRole("Receptionist");


        when(userDAO.getUserById(5))
                .thenReturn(existingUser);

        when(userDAO.updateUser(any(User.class)))
                .thenReturn(true);


        controller.callDoPost(
                request,
                response
        );


        verify(userDAO)
                .updateUser(
                        argThat(user ->
                                user.getPassword()
                                        .equals("oldpassword")
                        )
                );

        verify(session)
                .setAttribute(
                        "success",
                        "User updated successfully."
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    @Test
    void testDoPostNewPassword() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        when(request.getParameter("username"))
                .thenReturn("staff");

        when(request.getParameter("fullName"))
                .thenReturn("Staff");

        when(request.getParameter("email"))
                .thenReturn("staff@gmail.com");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");

        when(request.getParameter("password"))
                .thenReturn("newpassword");


        User existingUser = new User();

        existingUser.setUserId(5);
        existingUser.setPassword("oldpassword");


        when(userDAO.getUserById(5))
                .thenReturn(existingUser);

        when(userDAO.updateUser(any(User.class)))
                .thenReturn(true);


        controller.callDoPost(
                request,
                response
        );


        verify(userDAO)
                .updateUser(
                        argThat(user ->
                                user.getPassword()
                                        .equals("newpassword")
                        )
                );

        verify(session)
                .setAttribute(
                        "success",
                        "User updated successfully."
                );
    }


    @Test
    void testDoPostUserNotFound() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("99");

        when(userDAO.getUserById(99))
                .thenReturn(null);


        controller.callDoPost(
                request,
                response
        );


        verify(userDAO)
                .getUserById(99);

        verify(userDAO, never())
                .updateUser(any(User.class));

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


    @Test
    void testDoPostNotLoggedIn() throws Exception {

        when(request.getSession(false))
                .thenReturn(null);


        controller.callDoPost(
                request,
                response
        );


        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .updateUser(any(User.class));
    }


    @Test
    void testDoPostNoUserInSession() throws Exception {

        when(session.getAttribute("user"))
                .thenReturn(null);


        controller.callDoPost(
                request,
                response
        );


        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Login.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .updateUser(any(User.class));
    }


    @Test
    void testDoPostNonAdmin() throws Exception {

        User receptionist = new User();

        receptionist.setUserId(2);
        receptionist.setUsername("reception");
        receptionist.setFullName("Reception");
        receptionist.setRole("Receptionist");


        when(session.getAttribute("user"))
                .thenReturn(receptionist);


        controller.callDoPost(
                request,
                response
        );


        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/Dashboard.jsp"
                );

        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .updateUser(any(User.class));
    }


    @Test
    void testDoPostUpdateFailure() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");

        when(request.getParameter("username"))
                .thenReturn("jake");

        when(request.getParameter("fullName"))
                .thenReturn("Jake Drake");

        when(request.getParameter("email"))
                .thenReturn("jake@gmail.com");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");

        when(request.getParameter("password"))
                .thenReturn("newpassword");


        User existingUser = new User();

        existingUser.setUserId(5);
        existingUser.setPassword("oldpassword");


        when(userDAO.getUserById(5))
                .thenReturn(existingUser);

        when(userDAO.updateUser(any(User.class)))
                .thenReturn(false);


        controller.callDoPost(
                request,
                response
        );


        verify(userDAO)
                .updateUser(any(User.class));

        verify(session)
                .setAttribute(
                        "error",
                        "Failed to update user."
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    @Test
    void testDoPostInvalidUserId() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("abc");


        controller.callDoPost(
                request,
                response
        );


        verify(userDAO, never())
                .getUserById(anyInt());

        verify(userDAO, never())
                .updateUser(any(User.class));

        verify(session)
                .setAttribute(
                        eq("error"),
                        contains("Error updating user")
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }


    @Test
    void testDoPostDatabaseException() throws Exception {

        when(request.getParameter("userId"))
                .thenReturn("5");


        when(userDAO.getUserById(5))
                .thenThrow(
                        new RuntimeException(
                                "Database error"
                        )
                );


        controller.callDoPost(
                request,
                response
        );


        verify(session)
                .setAttribute(
                        eq("error"),
                        contains("Error updating user")
                );

        verify(response)
                .sendRedirect(
                        "/SunriseDentalClinic/ManageUsers"
                );
    }
}
