package com.dentalclinic.controller;

import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/addPatient")
public class AddPatientController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PatientService patientService;


    @Override
    public void init() throws ServletException {
        patientService = PatientService.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException {

        // Display add patient page
        request.getRequestDispatcher("addPatient.jsp")
               .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        try {

            // Get form data
            String fullName = request.getParameter("fullName");
            String address = request.getParameter("address");
            String phoneNumber = request.getParameter("phoneNumber");


            // Create patient object
            Patient patient = new Patient();

            patient.setFullName(fullName);
            patient.setAddress(address);
            patient.setPhoneNumber(phoneNumber);


            // Save patient
            boolean result = patientService.registerPatient(patient);


            if(result){

                request.setAttribute("message",
                        "Patient registered successfully.");

                request.getRequestDispatcher("addPatient.jsp")
                       .forward(request, response);

            }else{

                request.setAttribute("error",
                        "Failed to register patient.");

                request.getRequestDispatcher("addPatient.jsp")
                       .forward(request, response);
            }


        } catch(SQLException e){

            request.setAttribute("error",
                    "Database error: " + e.getMessage());

            request.getRequestDispatcher("addPatient.jsp")
                   .forward(request, response);

        } catch(Exception e){

            request.setAttribute("error",
                    "An unexpected error occurred.");

            request.getRequestDispatcher("addPatient.jsp")
                   .forward(request, response);
        }
    }
}