package com.dentalclinic.controller;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddAppointment")
public class AddAppointmentController extends HttpServlet {

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

        request.getRequestDispatcher("/AddAppointment.jsp")
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

            appointment.setPatientName(
                    request.getParameter("patientName"));

            appointment.setDentistName(
                    request.getParameter("dentistName"));

            appointment.setTreatmentType(
                    request.getParameter("treatmentType"));

            appointment.setAppointmentDate(
                    request.getParameter("appointmentDate"));

            appointment.setAppointmentTime(
                    request.getParameter("appointmentTime"));

            boolean success =
                    appointmentService.registerAppointment(appointment);
            
            System.out.println("Appointment Time = " + appointment.getAppointmentTime());
            
            if (success) {

                request.getSession().setAttribute(
                        "success",
                        "Appointment registered successfully!"
                );

                response.sendRedirect("Dashboard");
                return;

            } else {

                request.setAttribute(
                        "error",
                        "Unable to register appointment.");

                request.getRequestDispatcher("/AddAppointment.jsp")
                       .forward(request, response);

                return;
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Patient ID must be a valid number.");

        } catch (SQLException e) {

            request.setAttribute(
                    "error",
                    "Database error while registering appointment.");

            e.printStackTrace();
        }

        request.getRequestDispatcher("/AddAppointment.jsp")
               .forward(request, response);
    }
}