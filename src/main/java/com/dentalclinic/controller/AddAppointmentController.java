package com.dentalclinic.controller;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addAppointment")
public class AddAppointmentController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AppointmentService appointmentService;

    @Override
    public void init() {
        appointmentService = new AppointmentService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("addAppointment.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Appointment appointment = new Appointment();

            appointment.setPatientId(
                    Integer.parseInt(request.getParameter("patientId")));

            appointment.setAppointmentDate(
                    request.getParameter("appointmentDate"));

            appointment.setAppointmentTime(
                    request.getParameter("appointmentTime"));

            appointment.setTreatmentType(
                    request.getParameter("treatmentType"));

            // Optional
            appointment.setPatientName(
                    request.getParameter("patientName"));

            appointment.setDentistName(
                    request.getParameter("dentistName"));

            boolean success =
                    appointmentService.registerAppointment(appointment);

            if (success) {

                response.sendRedirect(
                        "appointmentList.jsp?success=true");

            } else {

                request.setAttribute("error",
                        "Unable to register appointment.");

                request.getRequestDispatcher("addAppointment.jsp")
                       .forward(request, response);
            }

        } catch (Exception e) {

            request.setAttribute("error",
                    "Error: " + e.getMessage());

            request.getRequestDispatcher("addAppointment.jsp")
                   .forward(request, response);
        }
    }
}