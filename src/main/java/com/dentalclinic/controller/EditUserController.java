package com.dentalclinic.controller;

import com.dentalclinic.dao.UserDAO; 
import com.dentalclinic.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EditUser")
public class EditUserController extends HttpServlet {

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


        if(session == null || session.getAttribute("user") == null){

            response.sendRedirect("login.jsp");
            return;

        }


        User loggedUser = 
                (User) session.getAttribute("user");

        // Admin access only
        if(!"Administrator".equals(loggedUser.getRole())){

            response.sendRedirect("dDshboard.jsp");
            return;

        }

        try {

            int userId = Integer.parseInt(
                    request.getParameter("userId")
            );

            User user = userDAO.getUserById(userId);

            request.setAttribute("user", user);

            request.getRequestDispatcher("EditUser.jsp")
                   .forward(request, response);

        } catch(Exception e) {

            e.printStackTrace();

            response.sendRedirect("manageUsers");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("user") == null){

            response.sendRedirect("Login.jsp");
            return;

        }

        User loggedUser =
                (User) session.getAttribute("user");

        // Admin access only
        if(!"Administrator".equals(loggedUser.getRole())){

            response.sendRedirect("Dashboard.jsp");
            return;

        }

        try {

            User user = new User();

            user.setUserId(
                    Integer.parseInt(request.getParameter("userId"))
            );

            user.setUsername(
                    request.getParameter("username")
            );

            user.setPassword(
                    request.getParameter("password")
            );

            user.setFullName(
                    request.getParameter("fullName")
            );

            user.setEmail(
                    request.getParameter("email")
            );

            user.setRole(
                    request.getParameter("role")
            );

            boolean updated =
                    userDAO.updateUser(user);


            if(updated){

                request.setAttribute("success",
                        "User updated successfully.");

            } else {

                request.setAttribute("error",
                        "Failed to update user.");

            }

            request.getRequestDispatcher("ManageUsers.jsp")
                   .forward(request, response);

        } catch(Exception e) {

            e.printStackTrace();

            request.setAttribute("error",
                    "Error updating user.");

            request.getRequestDispatcher("ManageUsers.jsp")
                   .forward(request, response);

        }

    }

}