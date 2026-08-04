package com.dentalclinic.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/help")
public class HelpController extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        // Step-by-step instructions for staff

        String[] instructions = {

            "1. Login to the system using your username and password.",

            "2. Register a new patient by entering patient details.",

            "3. Add an appointment by entering patient ID, dentist name, treatment type, date and time.",

            "4. Search appointments using the appointment ID.",

            "5. View all registered appointments from the appointment list.",

            "6. Calculate the patient bill by entering the appointment ID.",

            "7. Print the generated bill for the patient.",

            "8. Logout from the system after completing tasks."
        };


        request.setAttribute(
                "instructions",
                instructions
        );


        request.getRequestDispatcher("/help.jsp")
               .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

}