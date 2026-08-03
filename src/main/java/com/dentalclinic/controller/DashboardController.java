package com.dentalclinic.controller;

import com.dentalclinic.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/dashboard")
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

            response.sendRedirect("login.jsp");
            return;
        }


        // Get logged-in user
        User user = (User) session.getAttribute("user");


        // Send user information to dashboard JSP
        request.setAttribute("fullName",
                user.getFullName());

        request.setAttribute("role",
                user.getRole());


        request.getRequestDispatcher("dashboard.jsp")
               .forward(request, response);
    }
}