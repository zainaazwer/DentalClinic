package com.dentalclinic.controller;

import com.dentalclinic.dao.BillDAO;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Bill;
import com.dentalclinic.service.AppointmentService;

import com.dentalclinic.dao.PatientDAO;
import com.dentalclinic.model.Patient;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CalculateBill")
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

        request.getRequestDispatcher("/CalculateBill.jsp")
               .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int appointmentId =
                    Integer.parseInt(
                    request.getParameter("appointmentId"));

            Appointment appointment =
                    appointmentService.getAppointmentById(
                            appointmentId);

            if(appointment == null){

                request.setAttribute(
                        "error",
                        "Appointment not found.");

                request.getRequestDispatcher(
                        "/CalculateBill.jsp")
                        .forward(request,response);

                return;

            }
            
            Bill bill = new Bill();

            // Set current date
            bill.setBillDate(
                    LocalDate.now().toString()
            );

            // Appointment details
            bill.setAppointmentId(
                    appointment.getAppointmentId()
            );

            bill.setPatientId(
                    appointment.getPatientId()
            );

            bill.setPatientName(
                    appointment.getPatientName()
            );
            
            PatientDAO patientDAO = new PatientDAO();

            Patient patient = patientDAO.getPatientById(appointment.getPatientId());

            if(patient != null){
                bill.setPatientContact(patient.getPhoneNumber());
            }

            bill.setTreatmentType(
                    appointment.getTreatmentType()
            );
            
            // Calculate treatment cost
            double treatmentCost = 0.00;

            if("Cleaning".equalsIgnoreCase(
                    appointment.getTreatmentType())){

                treatmentCost = 100.00;

            } else if("Filling".equalsIgnoreCase(
                    appointment.getTreatmentType())){

                treatmentCost = 150.00;

            } else if("Extraction".equalsIgnoreCase(
                    appointment.getTreatmentType())){

                treatmentCost = 200.00;

            } else if("Braces".equalsIgnoreCase(
                    appointment.getTreatmentType())){

                treatmentCost = 500.00;

            }

            bill.setTreatmentCost(treatmentCost);
            
            // Consultation fee
            bill.setConsultationFee(
                    Bill.DEFAULT_CONSULTATION_FEE
            );
            
            // Payment details from form
            double amountPaid =
                    Double.parseDouble(
                    request.getParameter("amountPaid")
           );                  
    
            String paymentMethod =
                    request.getParameter("paymentMethod");
            
            bill.setAmountPaid(amountPaid);
            
            bill.setPaymentMethod(paymentMethod);

            // Calculate total
            bill.calculateTotal();
            
            // Save bill
            BillDAO billDAO = new BillDAO();

            boolean saved =
                    billDAO.createBill(bill);

            if(!saved){

                request.setAttribute(
                        "error",
                        "Bill was not saved.");

            }

            request.setAttribute(
                    "bill",
                    bill);

        }
        catch(NumberFormatException e){

            request.setAttribute(
                    "error",
                    "Invalid amount entered.");

            e.printStackTrace();

        }
        catch(SQLException e){

            request.setAttribute(
                    "error",
                    "Database error while saving bill.");

            e.printStackTrace();

        }

        request.getRequestDispatcher(
                "/PrintBill.jsp")
                .forward(request,response);

    }
}
