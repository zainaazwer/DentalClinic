package com.dentalclinic.controller;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/viewAppointments")
public class ViewAppointmentController extends HttpServlet {

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

        try {

            List<Appointment> appointments =
                    appointmentService.getAllAppointments();

            request.setAttribute("appointments", appointments);

            request.getRequestDispatcher("viewAppointments.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            throw new ServletException(e);

        }
    }
}