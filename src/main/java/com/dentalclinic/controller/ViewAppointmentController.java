package com.dentalclinic.controller;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewAppointment")
public class ViewAppointmentController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AppointmentService appointmentService;


    @Override
    public void init() throws ServletException {

        appointmentService = new AppointmentService();

    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        try {

            // Get all appointments from database
            List<Appointment> appointments =
                    appointmentService.getAllAppointments();


            // Send appointment list to JSP
            request.setAttribute("appointments", appointments);


        } catch (SQLException e) {


            request.setAttribute("error",
                    "Unable to retrieve appointments.");


            e.printStackTrace();

        }


        request.getRequestDispatcher("/ViewAppointment.jsp")
               .forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

}