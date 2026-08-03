package com.dentalclinic.controller;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/searchAppointment")
public class SearchAppointmentController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AppointmentService appointmentService;

    @Override
    public void init() {
        appointmentService = new AppointmentService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int appointmentId =
                    Integer.parseInt(request.getParameter("appointmentId"));

            Appointment appointment =
                    appointmentService.getAppointmentById(appointmentId);

            request.setAttribute("appointment", appointment);

            request.getRequestDispatcher("searchAppointment.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            request.setAttribute("error",
                    "Appointment not found.");

            request.getRequestDispatcher("searchAppointment.jsp")
                   .forward(request, response);
        }
    }
}