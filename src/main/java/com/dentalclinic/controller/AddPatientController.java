package com.dentalclinic.controller;

import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addPatient")
public class AddPatientController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PatientService patientService;

    @Override
    public void init() {
        patientService = PatientService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("addPatient.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Patient patient = new Patient();

            patient.setFullName(request.getParameter("fullName"));
            patient.setAddress(request.getParameter("address"));
            patient.setPhoneNumber(request.getParameter("phoneNumber"));

            boolean success = patientService.registerPatient(patient);

            if (success) {

                response.sendRedirect("viewPatients.jsp?success=true");

            } else {

                request.setAttribute("error",
                        "Unable to add patient.");

                request.getRequestDispatcher("addPatient.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {

            request.setAttribute("error", e.getMessage());

            request.getRequestDispatcher("addPatient.jsp")
                   .forward(request, response);
        }
    }
}