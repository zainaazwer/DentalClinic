package com.dentalclinic.controller;

import com.dentalclinic.model.User;
import com.dentalclinic.service.AuthenticationService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AuthenticationService authService;

    @Override
    public void init() {
        authService = new AuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("logout".equals(action)) {

            HttpSession session = request.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            Cookie cookie = new Cookie("username", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            response.sendRedirect("Login.jsp");
            return;
        }

        request.getRequestDispatcher("Login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute("error",
                    "Username and password are required.");

            request.getRequestDispatcher("Login.jsp")
                   .forward(request, response);

            return;
        }

        try {

            User user = authService.login(username, password);
            
            System.out.println("Username: " + username);
            System.out.println("User object: " + user);

            if (user != null) {

                HttpSession session = request.getSession();

                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("fullName", user.getFullName());
                session.setAttribute("role", user.getRole());
                
                session.setAttribute("success",
                        "Login successful!");

                session.setMaxInactiveInterval(30 * 60);

                Cookie cookie = new Cookie("username",
                        user.getUsername());

                cookie.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie);

                response.sendRedirect("Dashboard");

            } else {

                request.setAttribute("error",
                        "Invalid username or password.");

                request.getRequestDispatcher("Login.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {

            throw new ServletException(e);

        }
    }
}