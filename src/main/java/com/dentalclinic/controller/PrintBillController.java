package com.dentalclinic.controller;

import com.dentalclinic.model.Bill;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/printBill")
public class PrintBillController extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session = request.getSession(false);


        if (session == null ||
            session.getAttribute("bill") == null) {

            request.setAttribute("error",
                    "No bill available to print.");

            request.getRequestDispatcher("/calculateBill.jsp")
                   .forward(request, response);

            return;
        }


        Bill bill =
                (Bill) session.getAttribute("bill");


        request.setAttribute("bill", bill);


        request.getRequestDispatcher("/printBill.jsp")
               .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

}