package com.dentalclinic.webserviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;
import com.dentalclinic.webservice.PatientWS;

public class patientWSTest {

private PatientWS patientWS;
private PatientService patientService;


@BeforeEach
void setUp() throws Exception {

    patientWS = new PatientWS();

    // Create Mockito mock
    patientService = mock(PatientService.class);

    Field serviceField =
            PatientWS.class
                    .getDeclaredField("patientService");

    serviceField.setAccessible(true);
    serviceField.set(patientWS, patientService);
}

// GET ALL PATIENTS - SUCCESS
@Test
void testGetAllPatientsSuccess()
        throws Exception {

    Patient patient1 = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    Patient patient2 = new Patient(
            2,
            "Jenny Perera",
            "Kandy",
            "0712345678"
    );

    List<Patient> patients =
            Arrays.asList(patient1, patient2);

    when(patientService.getAllPatients())
            .thenReturn(patients);


    Response response =
            patientWS.getAllPatients();


    assertEquals(
            200,
            response.getStatus()
    );

    assertEquals(
            patients,
            response.getEntity()
    );

    verify(patientService)
            .getAllPatients();

    response.close();
}

// GET ALL PATIENTS - DATABASE ERROR
@Test
void testGetAllPatientsDatabaseError()
        throws Exception {

    when(patientService.getAllPatients())
            .thenThrow(
                    new SQLException(
                            "Database error"
                    )
            );


    Response response =
            patientWS.getAllPatients();


    assertEquals(
            500,
            response.getStatus()
    );

    assertEquals(
            "Unable to retrieve patients",
            response.getEntity()
    );

    verify(patientService)
            .getAllPatients();

    response.close();
}

// GET PATIENT BY ID - FOUND
@Test
void testGetPatientByIdFound()
        throws Exception {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientService.getPatientById(1))
            .thenReturn(patient);


    Response response =
            patientWS.getPatientById(1);


    assertEquals(
            200,
            response.getStatus()
    );

    assertEquals(
            patient,
            response.getEntity()
    );

    verify(patientService)
            .getPatientById(1);

    response.close();
}

// GET PATIENT BY ID - NOT FOUND
@Test
void testGetPatientByIdNotFound()
        throws Exception {

    when(patientService.getPatientById(99))
            .thenReturn(null);


    Response response =
            patientWS.getPatientById(99);


    assertEquals(
            404,
            response.getStatus()
    );

    assertEquals(
            "Patient not found",
            response.getEntity()
    );

    verify(patientService)
            .getPatientById(99);

    response.close();
}

// GET PATIENT BY ID - DATABASE ERROR
@Test
void testGetPatientByIdDatabaseError()
        throws Exception {

    when(patientService.getPatientById(1))
            .thenThrow(
                    new SQLException(
                            "Database error"
                    )
            );


    Response response =
            patientWS.getPatientById(1);


    assertEquals(
            500,
            response.getStatus()
    );

    verify(patientService)
            .getPatientById(1);

    response.close();
}

// ADD PATIENT - SUCCESS
@Test
void testAddPatientSuccess()
        throws Exception {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientService.registerPatient(patient))
            .thenReturn(true);


    Response response =
            patientWS.addPatient(patient);


    assertEquals(
            201,
            response.getStatus()
    );

    assertEquals(
            "Patient registered successfully",
            response.getEntity()
    );

    verify(patientService)
            .registerPatient(patient);

    response.close();
}

// ADD PATIENT - INVALID DETAILS
@Test
void testAddPatientInvalidDetails()
        throws Exception {

    Patient patient = new Patient(
            1,
            "",
            "Colombo",
            ""
    );

    when(patientService.registerPatient(patient))
            .thenReturn(false);


    Response response =
            patientWS.addPatient(patient);


    assertEquals(
            400,
            response.getStatus()
    );

    assertEquals(
            "Invalid patient details",
            response.getEntity()
    );

    verify(patientService)
            .registerPatient(patient);

    response.close();
}

// ADD PATIENT - DATABASE ERROR
@Test
void testAddPatientDatabaseError()
        throws Exception {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientService.registerPatient(patient))
            .thenThrow(
                    new SQLException(
                            "Database error"
                    )
            );


    Response response =
            patientWS.addPatient(patient);


    assertEquals(
            500,
            response.getStatus()
    );

    verify(patientService)
            .registerPatient(patient);

    response.close();
}

}
