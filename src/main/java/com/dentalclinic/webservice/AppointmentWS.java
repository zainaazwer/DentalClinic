package com.dentalclinic.webservice;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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