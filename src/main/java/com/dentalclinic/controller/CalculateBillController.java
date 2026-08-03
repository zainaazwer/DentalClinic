package com.dentalclinic.controller;

import com.dentalclinic.model.Bill;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/calculateBill")
public class CalculateBillController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Bill bill = new Bill();

            // Appointment details
            bill.setAppointmentId(
                    Integer.parseInt(request.getParameter("appointmentId")));

            // Patient details
            bill.setPatientId(
                    Integer.parseInt(request.getParameter("patientId")));

            bill.setPatientName(
                    request.getParameter("patientName"));

            bill.setPatientNumber(
                    request.getParameter("patientNumber"));

            bill.setPatientContact(
                    request.getParameter("patientContact"));


            // Treatment details
            bill.setTreatmentType(
                    request.getParameter("treatmentType"));

            bill.setTreatmentDescription(
                    request.getParameter("treatmentDescription"));


            // Cost calculation
            double treatmentCost =
                    Double.parseDouble(
                    request.getParameter("treatmentCost"));

            bill.setTreatmentCost(treatmentCost);

            bill.setConsultationFee(
                    Bill.DEFAULT_CONSULTATION_FEE);

            bill.calculateTotal();


            // Store bill temporarily
            HttpSession session = request.getSession();

            session.setAttribute("bill", bill);


            response.sendRedirect("printBill");


        } catch (Exception e) {

            request.setAttribute("error",
                    "Unable to calculate bill: " + e.getMessage());

            request.getRequestDispatcher("calculateBill.jsp")
                   .forward(request, response);
        }
    }
}