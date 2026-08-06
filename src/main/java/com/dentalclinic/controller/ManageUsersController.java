package com.dentalclinic.controller;

import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ManageUsers")
public class ManageUsersController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("user");

        // Administrator only
        if (!"Administrator".equals(loggedUser.getRole())
                && !"Admin".equals(loggedUser.getRole())) {

            response.sendRedirect("Dashboard.jsp");
            return;
        }

        try {

            List<User> users = userDAO.getAllUsers();

            // Debug
            System.out.println("Users loaded = " + users.size());

            request.setAttribute("users", users);

            request.getRequestDispatcher("ManageUsers.jsp")
                   .forward(request, response);

        } catch (SQLException e) {

            e.printStackTrace();

            request.setAttribute("error", e.getMessage());

            request.getRequestDispatcher("ManageUsers.jsp")
                   .forward(request, response);

        }

    }
}