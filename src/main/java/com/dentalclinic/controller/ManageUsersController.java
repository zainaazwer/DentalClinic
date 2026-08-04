package com.dentalclinic.controller;

import com.dentalclinic.dao.UserDAO; 
import com.dentalclinic.model.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ManageUsers")
public class ManageUsersController extends HttpServlet {

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



        User user = (User) session.getAttribute("user");



        // Admin access only
        if(!"Administrator".equals(user.getRole())){

            response.sendRedirect("dashboard.jsp");
            return;

        }



        try {


            List<User> users = userDAO.getAllUsers();


            request.setAttribute("users", users);


            request.getRequestDispatcher("manageUsers.jsp")
                   .forward(request, response);



        } catch(Exception e){


            e.printStackTrace();


            request.setAttribute("error",
                    "Unable to load users.");


            request.getRequestDispatcher("manageUsers.jsp")
                   .forward(request, response);

        }

    }

}