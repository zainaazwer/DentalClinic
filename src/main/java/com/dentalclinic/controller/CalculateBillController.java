package com.dentalclinic.controller;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Bill;
import com.dentalclinic.service.AppointmentService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/calculateBill")
public class CalculateBillController extends HttpServlet {

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

        request.getRequestDispatcher("/calculateBill.jsp")
               .forward(request, response);
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


            if (appointment == null) {

                request.setAttribute("error",
                        "Appointment not found.");

                request.getRequestDispatcher("/calculateBill.jsp")
                       .forward(request, response);

                return;
            }


            Bill bill = new Bill();


            // Appointment details
            bill.setAppointmentId(
                    appointment.getAppointmentId());


            bill.setPatientId(
                    appointment.getPatientId());


            bill.setPatientName(
                    appointment.getPatientName());


            bill.setTreatmentType(
                    appointment.getTreatmentType());

            
            double treatmentCost = 0.00;


            if ("Cleaning".equalsIgnoreCase(
                    appointment.getTreatmentType())) {

                treatmentCost = 100.00;

            } else if ("Filling".equalsIgnoreCase(
                    appointment.getTreatmentType())) {

                treatmentCost = 150.00;

            } else if ("Extraction".equalsIgnoreCase(
                    appointment.getTreatmentType())) {

                treatmentCost = 200.00;

            }


            bill.setTreatmentCost(treatmentCost);


            // Consultation fee
            bill.setConsultationFee(
                    Bill.DEFAULT_CONSULTATION_FEE);


            // Calculate total
            bill.calculateTotal();


            request.setAttribute("bill", bill);
            
            HttpSession session = request.getSession();
            session.setAttribute("bill", bill);


        } catch (NumberFormatException e) {

            request.setAttribute("error",
                    "Invalid Appointment ID.");


        } catch (SQLException e) {

            request.setAttribute("error",
                    "Database error while calculating bill.");

            e.printStackTrace();
        }


        request.getRequestDispatcher("/calculateBill.jsp")
               .forward(request, response);
    }
}