package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.AddUserController;
import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;



// TESTABLE CONTROLLER
class TestableAddUserController extends AddUserController {

    public void callDoGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        super.doGet(request, response);
    }

    public void callDoPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        super.doPost(request, response);
    }
}



// TEST CLASS
public class addUserControllerTest {

    private TestableAddUserController controller;

    private UserDAO userDAO;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;


    // SETUP
    @BeforeEach
    void setUp() throws Exception {

        controller = new TestableAddUserController();

        userDAO = mock(UserDAO.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);


        Field userDAOField =
                AddUserController.class
                        .getDeclaredField("userDAO");

        userDAOField.setAccessible(true);

        userDAOField.set(
                controller,
                userDAO
        );


        // Logged-in Admin
        User admin = new User();

        admin.setUserId(1);
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFullName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setRole("Admin");

        when(request.getSession(false))
                .thenReturn(session);

        when(request.getSession())
                .thenReturn(session);


        when(session.getAttribute("user"))
                .thenReturn(admin);


        when(request.getRequestDispatcher(anyString()))
                .thenReturn(dispatcher);
    }


    // GET - ADMIN ACCESS
    @Test
    void testDoGetAdminAccess()
            throws Exception {

        controller.callDoGet(
                request,
                response
        );


        verify(request)
                .getRequestDispatcher(
                        "AddUser.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(response, never())
                .sendRedirect("Login.jsp");


        verify(response, never())
                .sendRedirect("Dashboard");
    }


    // GET - NOT LOGGED IN
    @Test
    void testDoGetNotLoggedIn()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(null);


        controller.callDoGet(
                request,
                response
        );


        verify(response)
                .sendRedirect("Login.jsp");


        verify(request, never())
                .getRequestDispatcher(anyString());
    }


    // GET - NON ADMIN
    @Test
    void testDoGetNonAdmin()
            throws Exception {

        User receptionist = new User();

        receptionist.setUserId(2);
        receptionist.setUsername("reception");
        receptionist.setRole("Receptionist");


        when(session.getAttribute("user"))
                .thenReturn(receptionist);


        controller.callDoGet(
                request,
                response
        );


        verify(response)
                .sendRedirect("Dashboard");


        verify(request, never())
                .getRequestDispatcher("AddUser.jsp");
    }


    // POST - SUCCESS
    @Test
    void testDoPostSuccess()
            throws Exception {

        when(request.getParameter("fullName"))
                .thenReturn("Staff");

        when(request.getParameter("username"))
                .thenReturn("staff");

        when(request.getParameter("email"))
                .thenReturn("staff@gmail.com");

        when(request.getParameter("password"))
                .thenReturn("staff");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");


        when(userDAO.userExists("staff"))
                .thenReturn(false);

        when(userDAO.addUser(any(User.class)))
                .thenReturn(true);


        controller.callDoPost(
                request,
                response
        );


        // Username checked
        verify(userDAO)
                .userExists("staff");


        // User added with correct information
        verify(userDAO)
                .addUser(
                        argThat(user ->
                                "Staff".equals(
                                        user.getFullName()
                                )
                                &&
                                "staff".equals(
                                        user.getUsername()
                                )
                                &&
                                "staff@gmail.com".equals(
                                        user.getEmail()
                                )
                                &&
                                "staff".equals(
                                        user.getPassword()
                                )
                                &&
                                "Receptionist".equals(
                                        user.getRole()
                                )
                        )
                );


        // Success message stored in session
        verify(session)
                .setAttribute(
                        "success",
                        "User added successfully!"
                );


        // Redirect after successful registration
        verify(response)
                .sendRedirect("Dashboard");


        // No error page
        verify(request, never())
                .setAttribute(
                        eq("error"),
                        anyString()
                );


        verify(dispatcher, never())
                .forward(
                        request,
                        response
                );
    }


    // POST - EMPTY REQUIRED FIELD
    @Test
    void testDoPostEmptyRequiredField()
            throws Exception {

        when(request.getParameter("fullName"))
                .thenReturn("");

        when(request.getParameter("username"))
                .thenReturn("staff");

        when(request.getParameter("email"))
                .thenReturn("staff@gmail.com");

        when(request.getParameter("password"))
                .thenReturn("staff");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "All required fields must be filled."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(userDAO, never())
                .userExists(anyString());


        verify(userDAO, never())
                .addUser(any(User.class));


        verify(response, never())
                .sendRedirect(anyString());
    }


    // POST - INVALID USERNAME
    @Test
    void testDoPostInvalidUsername()
            throws Exception {

        when(request.getParameter("fullName"))
                .thenReturn("Staff");

        when(request.getParameter("username"))
                .thenReturn("jo");

        when(request.getParameter("email"))
                .thenReturn("staff@gmail.com");

        when(request.getParameter("password"))
                .thenReturn("staff");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        eq("error"),
                        contains(
                                "Username must be 3-20 characters"
                        )
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(userDAO, never())
                .userExists(anyString());


        verify(userDAO, never())
                .addUser(any(User.class));


        verify(response, never())
                .sendRedirect(anyString());
    }


    // POST - DUPLICATE USERNAME
    @Test
    void testDoPostDuplicateUsername()
            throws Exception {

        when(request.getParameter("fullName"))
                .thenReturn("Staff");

        when(request.getParameter("username"))
                .thenReturn("staff");

        when(request.getParameter("email"))
                .thenReturn("staff@gmail.com");

        when(request.getParameter("password"))
                .thenReturn("staff");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");


        when(userDAO.userExists("staff"))
                .thenReturn(true);


        controller.callDoPost(
                request,
                response
        );


        verify(userDAO)
                .userExists("staff");


        verify(request)
                .setAttribute(
                        "error",
                        "Username already exists."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(userDAO, never())
                .addUser(any(User.class));


        verify(response, never())
                .sendRedirect(anyString());
    }


    // POST - ADD USER FAILURE
    @Test
    void testDoPostAddUserFailure()
            throws Exception {

        when(request.getParameter("fullName"))
                .thenReturn("Staff");

        when(request.getParameter("username"))
                .thenReturn("staff");

        when(request.getParameter("email"))
                .thenReturn("staff@gmail.com");

        when(request.getParameter("password"))
                .thenReturn("staff");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");


        when(userDAO.userExists("staff"))
                .thenReturn(false);

        when(userDAO.addUser(any(User.class)))
                .thenReturn(false);


        controller.callDoPost(
                request,
                response
        );


        verify(userDAO)
                .userExists("staff");


        verify(userDAO)
                .addUser(any(User.class));


        verify(request)
                .setAttribute(
                        "error",
                        "Failed to add user."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(response, never())
                .sendRedirect(anyString());
    }


    // POST - NULL VALUES
    @Test
    void testDoPostNullValues()
            throws Exception {

        when(request.getParameter("fullName"))
                .thenReturn(null);

        when(request.getParameter("username"))
                .thenReturn(null);

        when(request.getParameter("email"))
                .thenReturn(null);

        when(request.getParameter("password"))
                .thenReturn(null);

        when(request.getParameter("role"))
                .thenReturn(null);


        controller.callDoPost(
                request,
                response
        );


        verify(request)
                .setAttribute(
                        "error",
                        "All required fields must be filled."
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        verify(userDAO, never())
                .userExists(anyString());


        verify(userDAO, never())
                .addUser(any(User.class));


        verify(response, never())
                .sendRedirect(anyString());
    }


    // POST - EMPTY EMAIL
    @Test
    void testDoPostEmptyEmail()
            throws Exception {

        when(request.getParameter("fullName"))
                .thenReturn("Staff");

        when(request.getParameter("username"))
                .thenReturn("staff");

        when(request.getParameter("email"))
                .thenReturn("");

        when(request.getParameter("password"))
                .thenReturn("staff");

        when(request.getParameter("role"))
                .thenReturn("Receptionist");


        when(userDAO.userExists("staff"))
                .thenReturn(false);

        when(userDAO.addUser(any(User.class)))
                .thenReturn(true);


        controller.callDoPost(
                request,
                response
        );

        verify(userDAO)
                .userExists("staff");


        verify(userDAO)
                .addUser(
                        argThat(user ->
                                "staff@gmail.com".equals(
                                        user.getEmail()
                                )
                        )
                );


        verify(session)
                .setAttribute(
                        "success",
                        "User added successfully!"
                );


        verify(response)
                .sendRedirect("Dashboard");


        verify(request, never())
                .setAttribute(
                        eq("error"),
                        anyString()
                );
    }


    // POST - NON ADMIN
    @Test
    void testDoPostNonAdmin()
            throws Exception {

        User receptionist = new User();

        receptionist.setUserId(2);
        receptionist.setUsername("reception");
        receptionist.setRole("Receptionist");


        when(session.getAttribute("user"))
                .thenReturn(receptionist);


        controller.callDoPost(
                request,
                response
        );


        verify(response)
                .sendRedirect("Dashboard");


        verify(userDAO, never())
                .userExists(anyString());


        verify(userDAO, never())
                .addUser(any(User.class));
    }


    // POST - NOT LOGGED IN
    @Test
    void testDoPostNotLoggedIn()
            throws Exception {

        when(request.getSession(false))
                .thenReturn(null);


        controller.callDoPost(
                request,
                response
        );


        verify(response)
                .sendRedirect("Login.jsp");


        verify(userDAO, never())
                .userExists(anyString());


        verify(userDAO, never())
                .addUser(any(User.class));
    }
}