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


        request.setAttribute("systemName",
                "Sunrise Dental Clinic Management System");


        request.setAttribute("instructions",
                "Step 1: Login using your staff username and password.\n\n" +
                "Step 2: Register patient details through Patient Management.\n\n" +
                "Step 3: Create appointments by entering patient and appointment information.\n\n" +
                "Step 4: View existing appointments to check schedules.\n\n" +
                "Step 5: Search appointments using appointment details.\n\n" +
                "Step 6: Calculate the bill after completing treatment.\n\n" +
                "Step 7: Print the generated bill for the patient.");


        request.getRequestDispatcher("help.jsp")
               .forward(request, response);
    }
}