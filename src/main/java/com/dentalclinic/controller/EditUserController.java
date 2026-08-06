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


        // Check login
        if (session == null || session.getAttribute("user") == null) {

            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }


        User loggedUser = (User) session.getAttribute("user");


        // Administrator only
        if (!"Admin".equals(loggedUser.getRole())) {

            response.sendRedirect(request.getContextPath() + "/Dashboard.jsp");
            return;
        }


        try {

            String userIdParam = request.getParameter("userId");


            if (userIdParam == null || userIdParam.isEmpty()) {

                response.sendRedirect(
                        request.getContextPath() + "/ManageUsers"
                );

                return;
            }


            int userId = Integer.parseInt(userIdParam);


            User user = userDAO.getUserById(userId);


            if (user == null) {

                session.setAttribute(
                        "error",
                        "User not found."
                );

                response.sendRedirect(
                        request.getContextPath() + "/ManageUsers"
                );

                return;
            }


            request.setAttribute("user", user);


            request.getRequestDispatcher("EditUser.jsp")
                   .forward(request, response);



        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath() + "/ManageUsers"
            );
        }

    }



    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session = request.getSession(false);



        if (session == null || session.getAttribute("user") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/Login.jsp"
            );

            return;
        }



        User loggedUser = (User) session.getAttribute("user");



        if (!"Admin".equals(loggedUser.getRole())) {

            response.sendRedirect(
                    request.getContextPath() + "/Dashboard.jsp"
            );

            return;
        }



        try {


            int userId = Integer.parseInt(
                    request.getParameter("userId")
            );



            User existingUser = userDAO.getUserById(userId);



            if(existingUser == null){

                session.setAttribute(
                        "error",
                        "User not found."
                );

                response.sendRedirect(
                        request.getContextPath() + "/ManageUsers"
                );

                return;
            }




            User user = new User();


            user.setUserId(userId);



            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String role = request.getParameter("role");
            String password = request.getParameter("password");



            user.setUsername(
                    username != null ? username.trim() : ""
            );


            user.setFullName(
                    fullName != null ? fullName.trim() : ""
            );


            user.setEmail(
                    email != null ? email.trim() : ""
            );


            user.setRole(
                    role != null ? role.trim() : ""
            );



            // Keep old password if no new password entered
            if(password == null || password.trim().isEmpty()){

                user.setPassword(
                        existingUser.getPassword()
                );

            } else {

                user.setPassword(
                        password.trim()
                );
            }



            System.out.println("Updating User");
            System.out.println("ID: " + user.getUserId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Password: " + user.getPassword());



            boolean updated = userDAO.updateUser(user);



            if(updated){

                session.setAttribute(
                        "success",
                        "User updated successfully."
                );

            } else {

                session.setAttribute(
                        "error",
                        "Failed to update user."
                );
            }



            response.sendRedirect(
                    request.getContextPath() + "/ManageUsers"
            );



        } catch(Exception e){


            e.printStackTrace();


            session.setAttribute(
                    "error",
                    "Error updating user: " + e.getMessage()
            );


            response.sendRedirect(
                    request.getContextPath() + "/ManageUsers"
            );
        }

    }

}