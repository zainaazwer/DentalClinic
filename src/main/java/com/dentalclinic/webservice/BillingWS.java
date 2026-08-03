package com.dentalclinic.webservice;


import com.dentalclinic.model.Bill;
import com.dentalclinic.service.BillingService;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.sql.SQLException;



@Path("/billing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class BillingWS {


    private BillingService billingService;



    public BillingWS() {

        billingService = new BillingService();

    }



    // Create bill
    @POST
    @Path("/calculate")
    public Response calculateBill(Bill bill) {


        try {


            double total =
                    bill.getTreatmentCost()
                    + bill.getConsultationFee();



            bill.setTotalAmount(total);



            return Response
                    .ok(bill)
                    .build();



        } catch(Exception e) {


            return Response
                    .status(500)
                    .entity("Bill calculation failed")
                    .build();

        }

    }





    // Get bill details
    @GET
    @Path("/{billId}")
    public Response getBill(
            @PathParam("billId") int billId) {


        try {


            Bill bill =
                billingService.getBillById(billId);



            if(bill != null) {

                return Response.ok(bill).build();

            }



            return Response
                    .status(404)
                    .entity("Bill not found")
                    .build();



        } catch(SQLException e) {

            return Response.status(500).build();

        }

    }

}