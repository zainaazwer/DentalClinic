package com.dentalclinic.webserviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.model.Bill;
import com.dentalclinic.service.BillingService;
import com.dentalclinic.webservice.BillingWS;

import javax.ws.rs.core.Response;

public class billingWSTest {

    private BillingWS billingWS;

    private BillingService billingService;


    @BeforeEach
    void setUp() throws Exception {

        billingWS = new BillingWS();

        billingService = mock(BillingService.class);


        java.lang.reflect.Field serviceField =
                BillingWS.class.getDeclaredField("billingService");

        serviceField.setAccessible(true);

        serviceField.set(
                billingWS,
                billingService
        );
    }

    
    // CALCULATE BILL - SUCCESS
    @Test
    void testCalculateBillSuccess() {

        Bill bill = new Bill();

        bill.setTreatmentCost(500.00);
        bill.setConsultationFee(50.00);


        Response response =
                billingWS.calculateBill(bill);


        assertEquals(
                200,
                response.getStatus()
        );


        Bill result =
                (Bill) response.getEntity();


        assertNotNull(result);


        assertEquals(
                550.00,
                result.getTotalAmount(),
                0.001
        );
    }


    // CALCULATE BILL - ZERO TREATMENT COST

    @Test
    void testCalculateBillZeroTreatmentCost() {

        Bill bill = new Bill();

        bill.setTreatmentCost(0.00);
        bill.setConsultationFee(50.00);


        Response response =
                billingWS.calculateBill(bill);


        assertEquals(
                200,
                response.getStatus()
        );


        Bill result =
                (Bill) response.getEntity();


        assertEquals(
                50.00,
                result.getTotalAmount(),
                0.001
        );
    }

    
    // CALCULATE BILL - NULL BILL
    @Test
    void testCalculateBillNullBill() {

        Response response =
                billingWS.calculateBill(null);


        assertEquals(
                500,
                response.getStatus()
        );


        assertEquals(
                "Bill calculation failed",
                response.getEntity()
        );
    }


    // CALCULATE BILL - NEGATIVE TREATMENT COST
    @Test
    void testCalculateBillNegativeTreatmentCost() {

        Bill bill = new Bill();

        bill.setTreatmentCost(-100.00);
        bill.setConsultationFee(50.00);


        Response response =
                billingWS.calculateBill(bill);


        assertEquals(
                200,
                response.getStatus()
        );


        Bill result =
                (Bill) response.getEntity();


        assertEquals(
                -50.00,
                result.getTotalAmount(),
                0.001
        );
    }


    // GET BILL - SUCCESS
    @Test
    void testGetBillSuccess()
            throws Exception {

        Bill bill = new Bill();

        bill.setBillId(1);
        bill.setPatientId(10);
        bill.setPatientName("Jenny Perera");
        bill.setTreatmentType("Cleaning");
        bill.setTreatmentCost(500.00);
        bill.setConsultationFee(50.00);
        bill.setTotalAmount(550.00);


        when(
                billingService.getBillById(1)
        ).thenReturn(bill);


        Response response =
                billingWS.getBill(1);


        assertEquals(
                200,
                response.getStatus()
        );


        Bill result =
                (Bill) response.getEntity();


        assertNotNull(result);


        assertEquals(
                1,
                result.getBillId()
        );


        assertEquals(
                "John",
                result.getPatientName()
        );


        assertEquals(
                550.00,
                result.getTotalAmount(),
                0.001
        );


        verify(
                billingService
        ).getBillById(1);
    }

    // GET BILL - NOT FOUND
    @Test
    void testGetBillNotFound()
            throws Exception {

        when(
                billingService.getBillById(99)
        ).thenReturn(null);


        Response response =
                billingWS.getBill(99);


        assertEquals(
                404,
                response.getStatus()
        );


        assertEquals(
                "Bill not found",
                response.getEntity()
        );


        verify(
                billingService
        ).getBillById(99);
    }


    // GET BILL - INVALID ID
    @Test
    void testGetBillInvalidId()
            throws Exception {

        when(
                billingService.getBillById(0)
        ).thenReturn(null);


        Response response =
                billingWS.getBill(0);


        assertEquals(
                404,
                response.getStatus()
        );


        assertEquals(
                "Bill not found",
                response.getEntity()
        );


        verify(
                billingService
        ).getBillById(0);
    }


    // GET BILL - NEGATIVE ID
    @Test
    void testGetBillNegativeId()
            throws Exception {

        when(
                billingService.getBillById(-1)
        ).thenReturn(null);


        Response response =
                billingWS.getBill(-1);


        assertEquals(
                404,
                response.getStatus()
        );


        assertEquals(
                "Bill not found",
                response.getEntity()
        );


        verify(
                billingService
        ).getBillById(-1);
    }


    // GET BILL - DATABASE ERROR
    @Test
    void testGetBillDatabaseError()
            throws Exception {

        when(
                billingService.getBillById(1)
        ).thenThrow(
                new SQLException("Database error")
        );


        Response response =
                billingWS.getBill(1);


        assertEquals(
                500,
                response.getStatus()
        );


        verify(
                billingService
        ).getBillById(1);
    }
}