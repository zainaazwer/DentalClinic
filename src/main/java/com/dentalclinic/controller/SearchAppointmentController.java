package com.dentalclinic.controller;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/searchAppointment")
public class SearchAppointmentController extends HttpServlet {

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

        String appointmentIdStr = request.getParameter("appointmentId");

        try {

            if (appointmentIdStr != null && !appointmentIdStr.trim().isEmpty()) {

                int appointmentId = Integer.parseInt(appointmentIdStr);

                Appointment appointment =
                        appointmentService.getAppointmentById(appointmentId);

                if (appointment != null) {

                    request.setAttribute("appointment", appointment);

                } else {

                    request.setAttribute("error",
                            "Appointment not found.");

                }
            }

        } catch (NumberFormatException e) {

            request.setAttribute("error",
                    "Invalid Appointment ID.");

        } catch (SQLException e) {

            request.setAttribute("error",
                    "Database error while searching appointment.");

            e.printStackTrace();
        }

        request.getRequestDispatcher("/searchAppointment.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

}