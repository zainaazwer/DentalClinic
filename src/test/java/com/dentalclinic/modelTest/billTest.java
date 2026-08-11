package com.dentalclinic.modelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dentalclinic.model.Bill;

public class billTest {

    // TEST DEFAULT CONSTRUCTOR
    @Test
    void testDefaultConstructor() {

        Bill bill = new Bill();

        assertNotNull(bill);

        assertEquals(50.00, bill.getConsultationFee(), 0.001);
        assertEquals(0.00, bill.getTreatmentCost(), 0.001);
        assertEquals(0.00, bill.getTotalAmount(), 0.001);
        assertEquals(0.00, bill.getAmountPaid(), 0.001);

        assertNotNull(bill.getBillDate());
        assertFalse(bill.getBillDate().isEmpty());
    }

    // TEST PARAMETERIZED CONSTRUCTOR
    @Test
    void testParameterizedConstructor() {

        Bill bill = new Bill(10, 20);

        assertEquals(10, bill.getAppointmentId());
        assertEquals(20, bill.getBillId());

        assertEquals(
                Bill.DEFAULT_CONSULTATION_FEE,
                bill.getConsultationFee(),
                0.001
        );
    }

    // TEST BILL ID
    @Test
    void testBillId() {

        Bill bill = new Bill();

        bill.setBillId(100);

        assertEquals(100, bill.getBillId());
    }

    // TEST BILL DATE
    @Test
    void testBillDate() {

        Bill bill = new Bill();

        String date = "2026-08-11 16:00:00";

        bill.setBillDate(date);

        assertEquals(date, bill.getBillDate());
    }

    // TEST APPOINTMENT ID
    @Test
    void testAppointmentId() {

        Bill bill = new Bill();

        bill.setAppointmentId(25);

        assertEquals(25, bill.getAppointmentId());
    }

    // TEST PATIENT ID
    @Test
    void testPatientId() {

        Bill bill = new Bill();

        bill.setPatientId(15);

        assertEquals(15, bill.getPatientId());
    }

    // TEST PATIENT NAME
    @Test
    void testPatientName() {

        Bill bill = new Bill();

        bill.setPatientName("Jenny Perera");

        assertEquals(
                "Jenny Perera",
                bill.getPatientName()
        );
    }

    // TEST PATIENT CONTACT
    @Test
    void testPatientContact() {

        Bill bill = new Bill();

        bill.setPatientContact("0771234567");

        assertEquals(
                "0771234567",
                bill.getPatientContact()
        );
    }

    // TEST TREATMENT TYPE
    @Test
    void testTreatmentType() {

        Bill bill = new Bill();

        bill.setTreatmentType("Dental Cleaning");

        assertEquals(
                "Dental Cleaning",
                bill.getTreatmentType()
        );
    }

    // TEST TREATMENT COST
    @Test
    void testTreatmentCost() {

        Bill bill = new Bill();

        bill.setTreatmentCost(150.00);

        assertEquals(
                150.00,
                bill.getTreatmentCost(),
                0.001
        );
    }

    // TEST CONSULTATION FEE
    @Test
    void testConsultationFee() {

        Bill bill = new Bill();

        bill.setConsultationFee(75.00);

        assertEquals(
                75.00,
                bill.getConsultationFee(),
                0.001
        );
    }

    // TEST TOTAL AMOUNT
    @Test
    void testTotalAmount() {

        Bill bill = new Bill();

        bill.setTotalAmount(250.00);

        assertEquals(
                250.00,
                bill.getTotalAmount(),
                0.001
        );
    }

    // TEST AMOUNT PAID
    @Test
    void testAmountPaid() {

        Bill bill = new Bill();

        bill.setAmountPaid(100.00);

        assertEquals(
                100.00,
                bill.getAmountPaid(),
                0.001
        );
    }

    // TEST PAYMENT METHOD
    @Test
    void testPaymentMethod() {

        Bill bill = new Bill();

        bill.setPaymentMethod(Bill.PAYMENT_CASH);

        assertEquals(
                Bill.PAYMENT_CASH,
                bill.getPaymentMethod()
        );
    }

    // TEST CALCULATE TOTAL
    @Test
    void testCalculateTotal() {

        Bill bill = new Bill();

        bill.setTreatmentCost(200.00);
        bill.setConsultationFee(50.00);

        double total = bill.calculateTotal();

        assertEquals(
                250.00,
                total,
                0.001
        );

        assertEquals(
                250.00,
                bill.getTotalAmount(),
                0.001
        );
    }

    // TEST CALCULATE TOTAL WITH DIFFERENT FEES
    @Test
    void testCalculateTotalWithDifferentFees() {

        Bill bill = new Bill();

        bill.setTreatmentCost(350.00);
        bill.setConsultationFee(75.00);

        double total = bill.calculateTotal();

        assertEquals(
                425.00,
                total,
                0.001
        );
    }

    // TEST CALCULATE BALANCE
    @Test
    void testCalculateBalance() {

        Bill bill = new Bill();

        bill.setTreatmentCost(200.00);
        bill.setConsultationFee(50.00);

        bill.calculateTotal();

        bill.setAmountPaid(100.00);

        assertEquals(
                150.00,
                bill.calculateBalance(),
                0.001
        );
    }

    // TEST FULL PAYMENT
    @Test
    void testCalculateBalanceAfterFullPayment() {

        Bill bill = new Bill();

        bill.setTreatmentCost(200.00);
        bill.setConsultationFee(50.00);

        bill.calculateTotal();

        bill.setAmountPaid(250.00);

        assertEquals(
                0.00,
                bill.calculateBalance(),
                0.001
        );
    }

    // TEST PAYMENT METHOD DISPLAY - CASH
    @Test
    void testPaymentMethodDisplayCash() {

        Bill bill = new Bill();

        bill.setPaymentMethod(Bill.PAYMENT_CASH);

        assertEquals(
                "Cash",
                bill.getPaymentMethodDisplay()
        );
    }

    // TEST PAYMENT METHOD DISPLAY - CARD
    @Test
    void testPaymentMethodDisplayCard() {

        Bill bill = new Bill();

        bill.setPaymentMethod(Bill.PAYMENT_CARD);

        assertEquals(
                "Card",
                bill.getPaymentMethodDisplay()
        );
    }

    // TEST NULL PAYMENT METHOD
    @Test
    void testPaymentMethodDisplayNull() {

        Bill bill = new Bill();

        bill.setPaymentMethod(null);

        assertEquals(
                "",
                bill.getPaymentMethodDisplay()
        );
    }

    // TEST OTHER PAYMENT METHOD
    @Test
    void testPaymentMethodDisplayOther() {

        Bill bill = new Bill();

        bill.setPaymentMethod("ONLINE");

        assertEquals(
                "ONLINE",
                bill.getPaymentMethodDisplay()
        );
    }

    // TEST FORMATTED TOTAL
    @Test
    void testFormattedTotal() {

        Bill bill = new Bill();

        bill.setTotalAmount(250.50);

        assertEquals(
                "$250.50",
                bill.getFormattedTotal()
        );
    }

    // TEST FORMATTED BALANCE
    @Test
    void testFormattedBalance() {

        Bill bill = new Bill();

        bill.setTreatmentCost(300.00);
        bill.setConsultationFee(50.00);

        bill.calculateTotal();

        bill.setAmountPaid(100.00);

        assertEquals(
                "$250.00",
                bill.getFormattedBalance()
        );
    }

    // TEST TO STRING
    @Test
    void testToString() {

        Bill bill = new Bill();

        bill.setBillId(10);
        bill.setPatientName("Jenny Perera");
        bill.setTreatmentType("Cleaning");
        bill.setTotalAmount(250.00);

        String result = bill.toString();

        assertNotNull(result);

        assertTrue(result.contains("billId=10"));
        assertTrue(result.contains("patientName='Jenny Perera'"));
        assertTrue(result.contains("treatmentType='Cleaning'"));
        assertTrue(result.contains("totalAmount=250.0"));
    }

    // TEST DEFAULT CONSULTATION FEE CONSTANT
    @Test
    void testDefaultConsultationFeeConstant() {

        assertEquals(
                50.00,
                Bill.DEFAULT_CONSULTATION_FEE,
                0.001
        );
    }

    // TEST PAYMENT CONSTANTS
    @Test
    void testPaymentConstants() {

        assertEquals(
                "CASH",
                Bill.PAYMENT_CASH
        );

        assertEquals(
                "CARD",
                Bill.PAYMENT_CARD
        );
    }
}