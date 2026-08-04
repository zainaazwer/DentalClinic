package com.dentalclinic.controller;

import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/addUser")
public class AddUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;


    @Override
    public void init() {

        userDAO = new UserDAO();

    }



    @Override
    protected void doPost(HttpServletRequest request,
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


            String fullName =
                    request.getParameter("fullName");


            String username =
                    request.getParameter("username");


            String password =
                    request.getParameter("password");


            String role =
                    request.getParameter("role");



            User user = new User();


            user.setFullName(fullName);

            user.setUsername(username);

            user.setPassword(password);

            user.setRole(role);



            boolean result =
                    userDAO.addUser(user);



            if(result){


                request.setAttribute("success",
                        "User added successfully.");


            }else{


                request.setAttribute("error",
                        "Unable to add user.");


            }



            request.getRequestDispatcher("addUser.jsp")
                   .forward(request, response);



        } catch(Exception e){


            e.printStackTrace();


            request.setAttribute("error",
                    "An error occurred while adding user.");


            request.getRequestDispatcher("addUser.jsp")
                   .forward(request, response);

        }


    }

}