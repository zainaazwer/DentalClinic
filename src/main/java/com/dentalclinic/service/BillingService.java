package com.dentalclinic.service;

import com.dentalclinic.dao.BillDAO;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Bill;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Treatment;

import java.sql.SQLException;
import java.time.LocalDate;

public class BillingService {

    private BillDAO billDAO;
    private AppointmentService appointmentService;

    private static final double CONSULTATION_FEE = 50.00;

    public BillingService() {
        billDAO = new BillDAO();
        appointmentService = new AppointmentService();
    }

    // Calculate bill
    public Bill calculateBill(Appointment appointment, Treatment treatment, Patient patient) {

        if (appointment == null || treatment == null || patient == null) {
            return null;
        }

        double treatmentCost = treatment.getTreatmentCost();

        double consultationFee = CONSULTATION_FEE;

        double totalAmount = treatmentCost + consultationFee;


        Bill bill = new Bill();

        bill.setBillDate(LocalDate.now().toString());

        bill.setAppointmentId(appointment.getAppointmentId());

        bill.setPatientId(patient.getPatientId());
        bill.setPatientName(patient.getFullName());
        bill.setPatientContact(patient.getPhoneNumber());


        bill.setTreatmentType(appointment.getTreatmentType());
        bill.setTreatmentDescription(treatment.getDescription());

        bill.setTreatmentCost(treatmentCost);
        bill.setConsultationFee(consultationFee);

        bill.setTotalAmount(totalAmount);

        bill.setAmountPaid(0.00);
        bill.setPaymentMethod("Pending");
        bill.setPaymentDate(LocalDate.now().toString());
        
        return bill;
    }

    // Save bill
    public Bill saveBill(Bill bill) throws SQLException {

        if (bill == null) {
            return null;
        }

        boolean success = billDAO.createBill(bill);

        return success ? bill : null;
    }

    // Get bill by ID
    public Bill getBillById(int billId) throws SQLException {

        if (billId <= 0) {
            return null;
        }

        return billDAO.getBillById(billId);
    }

    // Get all bills
    public java.util.List<Bill> getAllBills() throws SQLException {

        return billDAO.getAllBills();
    }

    // Update payment
    public boolean updatePayment(int billId, double amountPaid, String paymentMethod) 
            throws SQLException {

        if (billId <= 0) {
            return false;
        }

        return billDAO.updatePayment(billId, amountPaid, paymentMethod);
    }
}