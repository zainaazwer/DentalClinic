package com.dentalclinic.webservice;

import com.dentalclinic.model.Appointment; 
import com.dentalclinic.service.AppointmentService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/appointments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AppointmentWS {

    private AppointmentService appointmentService;

    public AppointmentWS() {
        appointmentService = new AppointmentService();
    }


    // Get all appointments
    @GET
    public Response getAllAppointments() {

        try {
            List<Appointment> appointments =
                    appointmentService.getAllAppointments();

            return Response.ok(appointments).build();

        } catch (SQLException e) {

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Database error")
                    .build();
        }
    }


    // Get appointments by patient ID
    @GET
    @Path("/{patientId}")
    public Response getAppointmentsByPatient(
            @PathParam("patientId") int patientId) {

        try {

            List<Appointment> appointments =
                    appointmentService.getAppointmentsByPatientId(patientId);

            return Response.ok(appointments).build();

        } catch (SQLException e) {

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Database error")
                    .build();
        }
    }


    // Add appointment
    @POST
    public Response addAppointment(Appointment appointment) {

        try {

            boolean result =
                    appointmentService.registerAppointment(appointment);

            if (result) {

                return Response
                        .status(Response.Status.CREATED)
                        .entity("Appointment created successfully")
                        .build();
            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Invalid appointment data")
                    .build();


        } catch (SQLException e) {

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Database error")
                    .build();
        }
    }
}