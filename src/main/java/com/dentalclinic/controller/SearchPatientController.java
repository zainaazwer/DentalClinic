package com.dentalclinic.controller;

import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/searchPatient")
public class SearchPatientController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PatientService patientService;

    @Override
    public void init() {
        patientService = PatientService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int patientId = Integer.parseInt(
                    request.getParameter("patientId"));

            Patient patient =
                    patientService.getPatientById(patientId);

            if (patient != null) {

                request.setAttribute("patient", patient);

            } else {

                request.setAttribute("error",
                        "Patient not found.");
            }

            request.getRequestDispatcher("searchPatient.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            request.setAttribute("error",
                    "Invalid Patient ID.");

            request.getRequestDispatcher("searchPatient.jsp")
                   .forward(request, response);
        }
    }
} 