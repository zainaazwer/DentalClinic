package com.dentalclinic.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.controller.PrintBillController;
import com.dentalclinic.model.Bill;

public class printBillControllerTest {

    private TestablePrintBillController controller;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {

        controller = new TestablePrintBillController();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(anyString()))
                .thenReturn(dispatcher);
    }

    // GET - BILL AVAILABLE
    @Test
    void testDoGetBillAvailable() throws Exception {

        Bill bill = new Bill();

        bill.setBillId(1);
        bill.setAppointmentId(10);
        bill.setPatientId(5);
        bill.setPatientName("Jenny Perera");
        bill.setPatientContact("0771234567");
        bill.setTreatmentType("Cleaning");
        bill.setTreatmentCost(100.00);
        bill.setConsultationFee(50.00);
        bill.setTotalAmount(150.00);
        bill.setAmountPaid(150.00);
        bill.setPaymentMethod("CASH");


        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("bill"))
                .thenReturn(bill);


        controller.callDoGet(request, response);


        verify(session)
                .getAttribute("bill");


        verify(request)
                .setAttribute(
                        "bill",
                        bill
                );


        verify(request)
                .getRequestDispatcher(
                        "/PrintBill.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // GET - NO SESSION
    @Test
    void testDoGetNoSession() throws Exception {

        when(request.getSession(false))
                .thenReturn(null);


        controller.callDoGet(request, response);


        verify(request)
                .setAttribute(
                        "error",
                        "No bill available."
                );


        verify(request)
                .getRequestDispatcher(
                        "/CalculateBill.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - SESSION EXISTS BUT NO BILL
    @Test
    void testDoGetNoBillInSession() throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("bill"))
                .thenReturn(null);


        controller.callDoGet(request, response);


        verify(session)
                .getAttribute("bill");


        verify(request)
                .setAttribute(
                        "error",
                        "No bill available."
                );


        verify(request)
                .getRequestDispatcher(
                        "/PrintBill.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // GET - BILL DETAILS
    @Test
    void testDoGetBillDetails() throws Exception {

        Bill bill = new Bill();

        bill.setBillId(25);
        bill.setPatientName("Jenny Perera");
        bill.setTreatmentType("Filling");
        bill.setTreatmentCost(150.00);
        bill.setConsultationFee(50.00);

        bill.calculateTotal();


        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("bill"))
                .thenReturn(bill);


        controller.callDoGet(request, response);


        verify(request)
                .setAttribute(
                        eq("bill"),
                        same(bill)
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );


        assertEquals(
                200.00,
                bill.getTotalAmount(),
                0.01
        );
    }


    // GET - CASH PAYMENT
    @Test
    void testDoGetCashPayment() throws Exception {

        Bill bill = new Bill();

        bill.setBillId(2);
        bill.setPatientName("Jenny Perera");
        bill.setTreatmentType("Cleaning");
        bill.setTreatmentCost(100.00);
        bill.setConsultationFee(50.00);
        bill.setAmountPaid(150.00);
        bill.setPaymentMethod(Bill.PAYMENT_CASH);

        bill.calculateTotal();


        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("bill"))
                .thenReturn(bill);


        controller.callDoGet(request, response);


        assertEquals(
                "Cash",
                bill.getPaymentMethodDisplay()
        );


        verify(request)
                .setAttribute(
                        "bill",
                        bill
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // GET - CARD PAYMENT
    @Test
    void testDoGetCardPayment() throws Exception {

        Bill bill = new Bill();

        bill.setBillId(3);
        bill.setPatientName("Jenny Perera");
        bill.setTreatmentType("Braces");
        bill.setTreatmentCost(500.00);
        bill.setConsultationFee(50.00);
        bill.setAmountPaid(550.00);
        bill.setPaymentMethod(Bill.PAYMENT_CARD);

        bill.calculateTotal();


        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("bill"))
                .thenReturn(bill);


        controller.callDoGet(request, response);


        assertEquals(
                "Card",
                bill.getPaymentMethodDisplay()
        );


        verify(request)
                .setAttribute(
                        "bill",
                        bill
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // POST - BILL AVAILABLE
    @Test
    void testDoPostBillAvailable() throws Exception {

        Bill bill = new Bill();

        bill.setBillId(10);
        bill.setPatientName("Jenny Perera");
        bill.setTreatmentType("Extraction");
        bill.setTreatmentCost(200.00);
        bill.setConsultationFee(50.00);
        bill.calculateTotal();


        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("bill"))
                .thenReturn(bill);


        controller.callDoPost(request, response);


        verify(session)
                .getAttribute("bill");


        verify(request)
                .setAttribute(
                        "bill",
                        bill
                );


        verify(request)
                .getRequestDispatcher(
                        "/PrintBill.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // POST - NO SESSION
    @Test
    void testDoPostNoSession() throws Exception {

        when(request.getSession(false))
                .thenReturn(null);


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        "error",
                        "No bill available."
                );


        verify(request)
                .getRequestDispatcher(
                        "/CalculateBill.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }


    // POST - NO BILL
    @Test
    void testDoPostNoBill() throws Exception {

        when(request.getSession(false))
                .thenReturn(session);

        when(session.getAttribute("bill"))
                .thenReturn(null);


        controller.callDoPost(request, response);


        verify(request)
                .setAttribute(
                        "error",
                        "No bill available."
                );


        verify(request)
                .getRequestDispatcher(
                        "/PrintBill.jsp"
                );


        verify(dispatcher)
                .forward(
                        request,
                        response
                );
    }

    // TESTABLE SUBCLASS
    private static class TestablePrintBillController
            extends PrintBillController {

        public void callDoGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            super.doGet(request, response);
        }


        public void callDoPost(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            super.doPost(request, response);
        }
    }
}
