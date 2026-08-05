package com.dentalclinic.controller;

import com.dentalclinic.model.User; 

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Dashboard")
public class DashboardController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Get existing session
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Get logged-in user
        User user = (User) session.getAttribute("user");

        // Send user details to dashboard page
        request.setAttribute("fullName", user.getFullName());
        request.setAttribute("role", user.getRole());

        // Forward to dashboard JSP
        request.getRequestDispatcher("/Dashboard.jsp")
               .forward(request, response);
    }
}