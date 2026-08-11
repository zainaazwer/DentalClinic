package com.dentalclinic.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.dao.BillDAO;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Bill;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Treatment;
import com.dentalclinic.service.BillingService;

public class billingServiceTest {

    private BillingService billingService;

    private BillDAO billDAO;

    private Appointment appointment;
    private Treatment treatment;
    private Patient patient;

    @BeforeEach
    void setUp() throws Exception {

        billingService = new BillingService();

        billDAO = mock(BillDAO.class);

        Field billDAOField =
                BillingService.class.getDeclaredField("billDAO");

        billDAOField.setAccessible(true);

        billDAOField.set(
                billingService,
                billDAO
        );

        // Create Appointment
        appointment = new Appointment();

        appointment.setAppointmentId(10);
        appointment.setPatientId(5);
        appointment.setPatientName("Jenny Perera");
        appointment.setTreatmentType("Dental Cleaning");
        appointment.setAppointmentDate("2026-08-11");
        appointment.setAppointmentTime("10:00");


        // Create Treatment
        treatment = new Treatment();

        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Dental Cleaning");
        treatment.setDescription("Basic dental cleaning");
        treatment.setTreatmentCost(100.00);


        // Create Patient
        patient = new Patient();

        patient.setPatientId(5);
        patient.setFullName("Jenny Perera");
        patient.setPhoneNumber("0771234567");
    }


    // CALCULATE BILL SUCCESS
    @Test
    void testCalculateBillSuccess() {

        Bill bill =
                billingService.calculateBill(
                        appointment,
                        treatment,
                        patient
                );

        assertNotNull(bill);

        assertEquals(
                10,
                bill.getAppointmentId()
        );

        assertEquals(
                5,
                bill.getPatientId()
        );

        assertEquals(
                "Jenny Perera",
                bill.getPatientName()
        );

        assertEquals(
                "0771234567",
                bill.getPatientContact()
        );

        assertEquals(
                "Dental Cleaning",
                bill.getTreatmentType()
        );

        assertEquals(
                100.00,
                bill.getTreatmentCost(),
                0.001
        );

        assertEquals(
                50.00,
                bill.getConsultationFee(),
                0.001
        );

        assertEquals(
                150.00,
                bill.getTotalAmount(),
                0.001
        );

        assertEquals(
                0.00,
                bill.getAmountPaid(),
                0.001
        );

        assertEquals(
                "Pending",
                bill.getPaymentMethod()
        );

        assertNotNull(
                bill.getBillDate()
        );
    }


    //  CALCULATE BILL WITH NULL APPOINTMENT
    @Test
    void testCalculateBillNullAppointment() {

        Bill bill =
                billingService.calculateBill(
                        null,
                        treatment,
                        patient
                );

        assertNull(bill);
    }


    //  CALCULATE BILL WITH NULL TREATMENT
    @Test
    void testCalculateBillNullTreatment() {

        Bill bill =
                billingService.calculateBill(
                        appointment,
                        null,
                        patient
                );

        assertNull(bill);
    }


    //  CALCULATE BILL WITH NULL PATIENT
    @Test
    void testCalculateBillNullPatient() {

        Bill bill =
                billingService.calculateBill(
                        appointment,
                        treatment,
                        null
                );

        assertNull(bill);
    }


    // CALCULATE BILL WITH ALL NULL VALUES
    @Test
    void testCalculateBillAllNull() {

        Bill bill =
                billingService.calculateBill(
                        null,
                        null,
                        null
                );

        assertNull(bill);
    }

    
    // CALCULATE BILL WITH ZERO TREATMENT COST
    @Test
    void testCalculateBillZeroTreatmentCost() {

        treatment.setTreatmentCost(0.00);

        Bill bill =
                billingService.calculateBill(
                        appointment,
                        treatment,
                        patient
                );

        assertNotNull(bill);

        assertEquals(
                0.00,
                bill.getTreatmentCost(),
                0.001
        );

        assertEquals(
                50.00,
                bill.getConsultationFee(),
                0.001
        );

        assertEquals(
                50.00,
                bill.getTotalAmount(),
                0.001
        );
    }

    
    // SAVE BILL SUCCESS
    @Test
    void testSaveBillSuccess() throws Exception {

        Bill bill = new Bill();

        bill.setAppointmentId(10);
        bill.setPatientId(5);
        bill.setPatientName("Jenny Perera");
        bill.setTreatmentType("Dental Cleaning");
        bill.setTreatmentCost(100.00);
        bill.setConsultationFee(50.00);
        bill.setTotalAmount(150.00);

        when(billDAO.createBill(bill))
                .thenReturn(true);

        Bill result =
                billingService.saveBill(bill);

        assertNotNull(result);

        assertSame(
                bill,
                result
        );

        verify(billDAO)
                .createBill(bill);
    }

    
    // SAVE BILL FAILURE
    @Test
    void testSaveBillFailure() throws Exception {

        Bill bill = new Bill();

        when(billDAO.createBill(bill))
                .thenReturn(false);

        Bill result =
                billingService.saveBill(bill);

        assertNull(result);

        verify(billDAO)
                .createBill(bill);
    }


    //  SAVE NULL BILL
    @Test
    void testSaveNullBill() throws Exception {

        Bill result =
                billingService.saveBill(null);

        assertNull(result);

        verify(
                billDAO,
                never()
        ).createBill(any(Bill.class));
    }


    // SAVE BILL DATABASE ERROR
    @Test
    void testSaveBillSQLException() throws Exception {

        Bill bill = new Bill();

        when(billDAO.createBill(bill))
                .thenThrow(
                        new SQLException(
                                "Database error"
                        )
                );

        assertThrows(
                SQLException.class,
                () -> billingService.saveBill(bill)
        );

        verify(billDAO)
                .createBill(bill);
    }


    //  GET BILL BY ID SUCCESS
    @Test
    void testGetBillByIdSuccess() throws Exception {

        Bill bill = new Bill();

        bill.setBillId(10);
        bill.setPatientName("Jenny Perera");

        when(billDAO.getBillById(10))
                .thenReturn(bill);

        Bill result =
                billingService.getBillById(10);

        assertNotNull(result);

        assertEquals(
                10,
                result.getBillId()
        );

        assertEquals(
                "Jenny Perera",
                result.getPatientName()
        );

        verify(billDAO)
                .getBillById(10);
    }


    // GET BILL BY ID NOT FOUND
    @Test
    void testGetBillByIdNotFound() throws Exception {

        when(billDAO.getBillById(99))
                .thenReturn(null);

        Bill result =
                billingService.getBillById(99);

        assertNull(result);

        verify(billDAO)
                .getBillById(99);
    }


    //  GET BILL BY INVALID ID
    @Test
    void testGetBillByInvalidId() throws Exception {

        Bill result =
                billingService.getBillById(0);

        assertNull(result);

        verify(
                billDAO,
                never()
        ).getBillById(anyInt());
    }


    // GET BILL BY NEGATIVE ID
    @Test
    void testGetBillByNegativeId() throws Exception {

        Bill result =
                billingService.getBillById(-1);

        assertNull(result);

        verify(
                billDAO,
                never()
        ).getBillById(anyInt());
    }


    // GET ALL BILLS
    @Test
    void testGetAllBills() throws Exception {

        List<Bill> bills =
                new ArrayList<>();

        Bill bill1 = new Bill();
        bill1.setBillId(1);
        bill1.setPatientName("Jenny Perera");

        Bill bill2 = new Bill();
        bill2.setBillId(2);
        bill2.setPatientName("Jenny Perera");

        bills.add(bill1);
        bills.add(bill2);

        when(billDAO.getAllBills())
                .thenReturn(bills);

        List<Bill> result =
                billingService.getAllBills();

        assertNotNull(result);

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                "Jenny Perera",
                result.get(0).getPatientName()
        );

        assertEquals(
                "Jenny Perera",
                result.get(1).getPatientName()
        );

        verify(billDAO)
                .getAllBills();
    }


    // GET ALL BILLS EMPTY
    @Test
    void testGetAllBillsEmpty() throws Exception {

        when(billDAO.getAllBills())
                .thenReturn(
                        new ArrayList<>()
                );

        List<Bill> result =
                billingService.getAllBills();

        assertNotNull(result);

        assertTrue(
                result.isEmpty()
        );

        verify(billDAO)
                .getAllBills();
    }


    //  GET ALL BILLS DATABASE ERROR
    @Test
    void testGetAllBillsSQLException() throws Exception {

        when(billDAO.getAllBills())
                .thenThrow(
                        new SQLException(
                                "Database error"
                        )
                );

        assertThrows(
                SQLException.class,
                () -> billingService.getAllBills()
        );

        verify(billDAO)
                .getAllBills();
    }


    // UPDATE PAYMENT SUCCESS
    @Test
    void testUpdatePaymentSuccess() throws Exception {

        when(
                billDAO.updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CASH
                )
        ).thenReturn(true);

        boolean result =
                billingService.updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CASH
                );

        assertTrue(result);

        verify(billDAO)
                .updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CASH
                );
    }


    // UPDATE PAYMENT FAILURE
    @Test
    void testUpdatePaymentFailure() throws Exception {

        when(
                billDAO.updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CASH
                )
        ).thenReturn(false);

        boolean result =
                billingService.updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CASH
                );

        assertFalse(result);

        verify(billDAO)
                .updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CASH
                );
    }


    //  UPDATE PAYMENT INVALID ID
    @Test
    void testUpdatePaymentInvalidId() throws Exception {

        boolean result =
                billingService.updatePayment(
                        0,
                        100.00,
                        Bill.PAYMENT_CASH
                );

        assertFalse(result);

        verify(
                billDAO,
                never()
        ).updatePayment(
                anyInt(),
                anyDouble(),
                anyString()
        );
    }


    // UPDATE PAYMENT NEGATIVE ID
    @Test
    void testUpdatePaymentNegativeId() throws Exception {

        boolean result =
                billingService.updatePayment(
                        -5,
                        100.00,
                        Bill.PAYMENT_CASH
                );

        assertFalse(result);

        verify(
                billDAO,
                never()
        ).updatePayment(
                anyInt(),
                anyDouble(),
                anyString()
        );
    }


    //  UPDATE PAYMENT DATABASE ERROR
    @Test
    void testUpdatePaymentSQLException()
            throws Exception {

        when(
                billDAO.updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CARD
                )
        ).thenThrow(
                new SQLException(
                        "Database error"
                )
        );

        assertThrows(
                SQLException.class,
                () -> billingService.updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CARD
                )
        );

        verify(billDAO)
                .updatePayment(
                        10,
                        100.00,
                        Bill.PAYMENT_CARD
                );
    }
}
