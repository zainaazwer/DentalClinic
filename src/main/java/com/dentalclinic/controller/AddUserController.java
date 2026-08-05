package com.dentalclinic.controller;

import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AddUser")
public class AddUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // User must be logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("user");

        // Admin only
        if (!"Admin".equalsIgnoreCase(loggedUser.getRole())) {
            response.sendRedirect("Dashboard");
            return;
        }

        request.getRequestDispatcher("AddUser.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // User must be logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("user");

        // Admin only
        if (!"Admin".equalsIgnoreCase(loggedUser.getRole())) {
            response.sendRedirect("Dashboard");
            return;
        }

        try {

            String fullName = request.getParameter("fullName");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            // Trim values
            fullName = fullName == null ? "" : fullName.trim();
            username = username == null ? "" : username.trim();
            email = email == null ? "" : email.trim();
            password = password == null ? "" : password.trim();
            role = role == null ? "" : role.trim();

            // Required fields
            if (fullName.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty() ||
                role.isEmpty()) {

                request.setAttribute("error",
                        "All required fields must be filled.");

                request.getRequestDispatcher("AddUser.jsp")
                       .forward(request, response);
                return;
            }

            // Username validation
            if (!username.matches("^[A-Za-z0-9_]{3,20}$")) {

                request.setAttribute("error",
                        "Username must be 3-20 characters and contain only letters, numbers and underscores.");

                request.getRequestDispatcher("AddUser.jsp")
                       .forward(request, response);
                return;
            }

            // Check if username already exists
            if (userDAO.userExists(username)) {

                request.setAttribute("error",
                        "Username already exists.");

                request.getRequestDispatcher("AddUser.jsp")
                       .forward(request, response);
                return;
            }

            User user = new User();

            user.setFullName(fullName);
            user.setUsername(username);

            if (email.isEmpty()) {
                user.setEmail(username + "@gmail.com");
            } else {
                user.setEmail(email);
            }

            user.setPassword(password);
            user.setRole(role);

            boolean added = userDAO.addUser(user);

            if (added) {

                request.setAttribute("success",
                        "User added successfully.");

            } else {

                request.setAttribute("error",
                        "Failed to add user.");

            }

            request.getRequestDispatcher("AddUser.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute("error",
                    "Error: " + e.getMessage());

            request.getRequestDispatcher("AddUser.jsp")
                   .forward(request, response);
        }
    }
}