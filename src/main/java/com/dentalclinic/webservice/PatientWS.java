package com.dentalclinic.webservice;

import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;


@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PatientWS {

    private PatientService patientService;


    public PatientWS() {
        patientService = PatientService.getInstance();
    }


    // Get all patients
    @GET
    public Response getAllPatients() {

        try {

            List<Patient> patients =
                    patientService.getAllPatients();

            return Response.ok(patients).build();

        } catch(SQLException e) {

            return Response
                    .status(500)
                    .entity("Unable to retrieve patients")
                    .build();
        }
    }



    // Search patient by ID
    @GET
    @Path("/{id}")
    public Response getPatientById(
            @PathParam("id") int id) {

        try {

            Patient patient =
                    patientService.getPatientById(id);


            if(patient != null) {
                return Response.ok(patient).build();
            }


            return Response
                    .status(404)
                    .entity("Patient not found")
                    .build();


        } catch(SQLException e) {

            return Response.status(500).build();
        }
    }



    // Register patient
    @POST
    public Response addPatient(Patient patient) {

        try {

            boolean result =
                    patientService.registerPatient(patient);


            if(result) {

                return Response
                        .status(201)
                        .entity("Patient registered successfully")
                        .build();
            }


            return Response
                    .status(400)
                    .entity("Invalid patient details")
                    .build();


        } catch(SQLException e) {

            return Response.status(500).build();
        }
    }
}