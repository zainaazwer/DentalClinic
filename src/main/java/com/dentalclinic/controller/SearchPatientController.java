package com.dentalclinic.controller;

import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/searchPatient")
public class SearchPatientController extends HttpServlet {

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

        String patientIdStr = request.getParameter("patientId");

        try {

            if (patientIdStr != null && !patientIdStr.trim().isEmpty()) {

                int patientId = Integer.parseInt(patientIdStr);

                Patient patient = patientService.getPatientById(patientId);

                request.setAttribute("patient", patient);

                if (patient == null) {
                    request.setAttribute("error",
                            "Patient not found.");
                }

            }

        } catch (NumberFormatException e) {

            request.setAttribute("error",
                    "Invalid Patient ID.");

        } catch (SQLException e) {

            request.setAttribute("error",
                    "Unable to search patient.");

            e.printStackTrace();
        }

        request.getRequestDispatcher("/searchPatient.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}