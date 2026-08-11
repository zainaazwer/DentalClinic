package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.LoginController;
import com.dentalclinic.model.User;
import com.dentalclinic.service.AuthenticationService;

public class loginControllerTest {

    private TestableLoginController controller;

    private AuthenticationService authService;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {

        controller = new TestableLoginController();

        authService = mock(AuthenticationService.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(anyString()))
                .thenReturn(dispatcher);


        Field authServiceField =
                LoginController.class
                        .getDeclaredField("authService");

        authServiceField.setAccessible(true);

        authServiceField.set(
                controller,
                authService
        );
    }

    // GET - DISPLAY LOGIN PAGE
    @Test
    void testDoGetDisplayLoginPage() throws Exception {

        when(request.getParameter("action"))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(request)
                .getRequestDispatcher("Login.jsp");

        verify(dispatcher)
                .forward(request, response);

        verify(response, never())
                .sendRedirect(anyString());
    }

    // GET - LOGOUT WITH SESSION
    @Test
    void testDoGetLogoutWithSession() throws Exception {

        when(request.getParameter("action"))
                .thenReturn("logout");

        when(request.getSession(false))
                .thenReturn(session);

        controller.callDoGet(request, response);

        verify(session)
                .invalidate();

        verify(response)
                .addCookie(any(Cookie.class));

        verify(response)
                .sendRedirect("Login.jsp");

        verify(request, never())
                .getRequestDispatcher("Login.jsp");
    }

    // GET - LOGOUT WITHOUT SESSION
    @Test
    void testDoGetLogoutWithoutSession() throws Exception {

        when(request.getParameter("action"))
                .thenReturn("logout");

        when(request.getSession(false))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(response)
                .addCookie(any(Cookie.class));

        verify(response)
                .sendRedirect("Login.jsp");

        verify(request, never())
                .getRequestDispatcher("Login.jsp");
    }

    // GET - LOGOUT COOKIE CHECK
    @Test
    void testDoGetLogoutDeletesCookie() throws Exception {

        when(request.getParameter("action"))
                .thenReturn("logout");

        when(request.getSession(false))
                .thenReturn(null);

        controller.callDoGet(request, response);

        verify(response)
                .addCookie(argThat(cookie ->
                        "username".equals(cookie.getName())
                        && cookie.getMaxAge() == 0
                ));

        verify(response)
                .sendRedirect("Login.jsp");
    }

    // POST - SUCCESSFUL LOGIN
    @Test
    void testDoPostSuccessfulLogin() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("admin");

        when(request.getParameter("password"))
                .thenReturn("admin");

        User user = new User();

        user.setUserId(1);
        user.setUsername("admin");
        user.setPassword("admin");
        user.setFullName("Admin");
        user.setEmail("admin@gmail.com");
        user.setRole("Admin");

        when(authService.login(
                "admin",
                "admin123"))
                .thenReturn(user);

        when(request.getSession())
                .thenReturn(session);

        controller.callDoPost(request, response);

        verify(authService)
                .login(
                        "admin",
                        "admin"
                );

        verify(session)
                .setAttribute(
                        "user",
                        user
                );

        verify(session)
                .setAttribute(
                        "username",
                        "admin"
                );

        verify(session)
                .setAttribute(
                        "fullName",
                        "Admin"
                );

        verify(session)
                .setAttribute(
                        "role",
                        "Admin"
                );

        verify(session)
                .setAttribute(
                        "success",
                        "Login successful!"
                );

        verify(session)
                .setMaxInactiveInterval(
                        30 * 60
                );

        verify(response)
                .addCookie(any(Cookie.class));

        verify(response)
                .sendRedirect("Dashboard");
    }

    // POST - SUCCESSFUL RECEPTIONIST LOGIN
    @Test
    void testDoPostReceptionistLogin() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("staff");

        when(request.getParameter("password"))
                .thenReturn("staff");

        User user = new User();

        user.setUserId(2);
        user.setUsername("staff");
        user.setPassword("staff");
        user.setFullName("Staff");
        user.setEmail("staff@gmail.com");
        user.setRole("Receptionist");

        when(authService.login(
                "staff",
                "staff"))
                .thenReturn(user);

        when(request.getSession())
                .thenReturn(session);

        controller.callDoPost(request, response);

        verify(authService)
                .login(
                        "staff",
                        "staff"
                );

        verify(session)
                .setAttribute(
                        "user",
                        user
                );

        verify(session)
                .setAttribute(
                        "role",
                        "Receptionist"
                );

        verify(session)
                .setAttribute(
                        "success",
                        "Login successful!"
                );

        verify(response)
                .sendRedirect("Dashboard");
    }

    // POST - EMPTY USERNAME
    @Test
    void testDoPostEmptyUsername() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("");

        when(request.getParameter("password"))
                .thenReturn("admin");

        controller.callDoPost(request, response);

        verify(request)
                .setAttribute(
                        "error",
                        "Username and password are required."
                );

        verify(request)
                .getRequestDispatcher("Login.jsp");

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verify(authService, never())
                .login(
                        anyString(),
                        anyString()
                );
    }

    // POST - NULL USERNAME
    @Test
    void testDoPostNullUsername() throws Exception {

        when(request.getParameter("username"))
                .thenReturn(null);

        when(request.getParameter("password"))
                .thenReturn("admin");

        controller.callDoPost(request, response);

        verify(request)
                .setAttribute(
                        "error",
                        "Username and password are required."
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verify(authService, never())
                .login(
                        anyString(),
                        anyString()
                );
    }

    // POST - EMPTY PASSWORD
    @Test
    void testDoPostEmptyPassword() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("admin");

        when(request.getParameter("password"))
                .thenReturn("");

        controller.callDoPost(request, response);

        verify(request)
                .setAttribute(
                        "error",
                        "Username and password are required."
                );

        verify(request)
                .getRequestDispatcher("Login.jsp");

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verify(authService, never())
                .login(
                        anyString(),
                        anyString()
                );
    }


    // POST - NULL PASSWORD
    @Test
    void testDoPostNullPassword() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("admin");

        when(request.getParameter("password"))
                .thenReturn(null);

        controller.callDoPost(request, response);

        verify(request)
                .setAttribute(
                        "error",
                        "Username and password are required."
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verify(authService, never())
                .login(
                        anyString(),
                        anyString()
                );
    }


    // POST - BOTH NULL
    @Test
    void testDoPostBothValuesNull() throws Exception {

        when(request.getParameter("username"))
                .thenReturn(null);

        when(request.getParameter("password"))
                .thenReturn(null);

        controller.callDoPost(request, response);

        verify(request)
                .setAttribute(
                        "error",
                        "Username and password are required."
                );

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verify(authService, never())
                .login(
                        anyString(),
                        anyString()
                );
    }


    // POST - INVALID LOGIN
    @Test
    void testDoPostInvalidLogin() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("wronguser");

        when(request.getParameter("password"))
                .thenReturn("wrongpassword");

        when(authService.login(
                "wronguser",
                "wrongpassword"))
                .thenReturn(null);

        controller.callDoPost(request, response);

        verify(authService)
                .login(
                        "wronguser",
                        "wrongpassword"
                );

        verify(request)
                .setAttribute(
                        "error",
                        "Invalid username or password."
                );

        verify(request)
                .getRequestDispatcher("Login.jsp");

        verify(dispatcher)
                .forward(
                        request,
                        response
                );

        verify(response, never())
                .sendRedirect("Dashboard");
    }


    // POST - USERNAME WITH SPACES
    @Test
    void testDoPostUsernameWithSpaces() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("   ");

        when(request.getParameter("password"))
                .thenReturn("admin");

        controller.callDoPost(request, response);

        verify(request)
                .setAttribute(
                        "error",
                        "Username and password are required."
                );

        verify(authService, never())
                .login(
                        anyString(),
                        anyString()
                );
    }

    // POST - PASSWORD WITH SPACES
    @Test
    void testDoPostPasswordWithSpaces() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("admin");

        when(request.getParameter("password"))
                .thenReturn("   ");

        controller.callDoPost(request, response);

        verify(request)
                .setAttribute(
                        "error",
                        "Username and password are required."
                );

        verify(authService, never())
                .login(
                        anyString(),
                        anyString()
                );
    }

    // POST - AUTHENTICATION SERVICE ERROR
    @Test
    void testDoPostAuthenticationException() throws Exception {

        when(request.getParameter("username"))
                .thenReturn("admin");

        when(request.getParameter("password"))
                .thenReturn("admin");

        when(authService.login(
                "admin",
                "admin"))
                .thenThrow(
                        new RuntimeException(
                                "Database error"
                        )
                );

        assertThrows(
                ServletException.class,
                () -> controller.callDoPost(
                        request,
                        response
                )
        );

        verify(authService)
                .login(
                        "admin",
                        "admin"
                );
    }

    // TESTABLE SUBCLASS
    private static class TestableLoginController
            extends LoginController {

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
