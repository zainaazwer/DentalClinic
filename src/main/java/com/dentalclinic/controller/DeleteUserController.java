package com.dentalclinic.controller;

import com.dentalclinic.dao.UserDAO;

import com.dentalclinic.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/deleteUser")
public class DeleteUserController extends HttpServlet {

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
        if(session == null || session.getAttribute("user") == null){

            response.sendRedirect("login.jsp");
            return;

        }



        User loggedUser =
                (User) session.getAttribute("user");



        // Admin only
        if(!"Administrator".equals(loggedUser.getRole())){

            response.sendRedirect("dashboard.jsp");
            return;

        }



        try {


            int userId = Integer.parseInt(
                    request.getParameter("userId")
            );



            boolean deleted =
                    userDAO.deleteUser(userId);



            if(deleted){

                request.getSession().setAttribute(
                        "success",
                        "User deleted successfully."
                );


            } else {


                request.getSession().setAttribute(
                        "error",
                        "Unable to delete user."
                );

            }



            response.sendRedirect("manageUsers");



        } catch(Exception e) {


            e.printStackTrace();


            request.getSession().setAttribute(
                    "error",
                    "Error deleting user."
            );


            response.sendRedirect("manageUsers");

        }


    }

}