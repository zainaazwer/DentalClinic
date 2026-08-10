package com.dentalclinic.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.dao.PatientDAO;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

public class patientServiceTest {

private PatientService patientService;
private PatientDAO patientDAO;
private AppointmentDAO appointmentDAO;

@BeforeEach
void setUp() throws Exception {

    patientService = PatientService.getInstance();

    patientDAO = mock(PatientDAO.class);
    appointmentDAO = mock(AppointmentDAO.class);

    Field patientDAOField =
            PatientService.class.getDeclaredField("patientDAO");

    patientDAOField.setAccessible(true);
    patientDAOField.set(patientService, patientDAO);

    Field appointmentDAOField =
            PatientService.class.getDeclaredField("appointmentDAO");

    appointmentDAOField.setAccessible(true);
    appointmentDAOField.set(patientService, appointmentDAO);
}

// VALIDATION TESTS
@Test
void testValidPatient() {

    Patient patient = new Patient();

    patient.setFullName("John Weera");
    patient.setPhoneNumber("0771234567");

    assertTrue(patientService.validatePatient(patient));
}


@Test
void testNullPatient() {

    assertFalse(patientService.validatePatient(null));
}


@Test
void testPatientWithoutName() {

    Patient patient = new Patient();

    patient.setFullName("");
    patient.setPhoneNumber("0771234567");

    assertFalse(patientService.validatePatient(patient));
}


@Test
void testPatientWithNullName() {

    Patient patient = new Patient();

    patient.setFullName(null);
    patient.setPhoneNumber("0771234567");

    assertFalse(patientService.validatePatient(patient));
}


@Test
void testPatientWithoutPhoneNumber() {

    Patient patient = new Patient();

    patient.setFullName("John Weera");
    patient.setPhoneNumber("");

    assertFalse(patientService.validatePatient(patient));
}


@Test
void testPatientWithNullPhoneNumber() {

    Patient patient = new Patient();

    patient.setFullName("John Weera");
    patient.setPhoneNumber(null);

    assertFalse(patientService.validatePatient(patient));
}

// GET PATIENT BY ID

@Test
void testGetPatientById() throws SQLException {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientDAO.getPatientById(1))
            .thenReturn(patient);

    Patient result =
            patientService.getPatientById(1);

    assertNotNull(result);
    assertEquals(1, result.getPatientId());
    assertEquals("John Weera", result.getFullName());

    verify(patientDAO).getPatientById(1);
}


@Test
void testGetPatientByInvalidId() throws SQLException {

    Patient result =
            patientService.getPatientById(0);

    assertNull(result);

    verify(patientDAO, never())
            .getPatientById(anyInt());
}

// GET ALL PATIENTS

@Test
void testGetAllPatients() throws SQLException {

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

    when(patientDAO.getAllPatients())
            .thenReturn(patients);

    List<Patient> result =
            patientService.getAllPatients();

    assertEquals(2, result.size());
    assertEquals("John Silva",
            result.get(0).getFullName());
    assertEquals("Jane Perera",
            result.get(1).getFullName());

    verify(patientDAO).getAllPatients();
}

// SEARCH BY NAME

@Test
void testSearchPatientsByName() throws SQLException {

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

    when(patientDAO.getAllPatients())
            .thenReturn(Arrays.asList(patient1, patient2));

    List<Patient> result =
            patientService.searchPatientsByName("john");

    assertEquals(1, result.size());
    assertEquals("John Silva",
            result.get(0).getFullName());
}


@Test
void testSearchPatientsWithEmptyName()
        throws SQLException {

    List<Patient> result =
            patientService.searchPatientsByName("");

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(patientDAO, never())
            .getAllPatients();
}


@Test
void testSearchPatientsWithNullName()
        throws SQLException {

    List<Patient> result =
            patientService.searchPatientsByName(null);

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(patientDAO, never())
            .getAllPatients();
}

// SEARCH BY PHONE NUMBE
@Test
void testGetPatientByPhoneNumber()
        throws SQLException {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientDAO.getAllPatients())
            .thenReturn(Arrays.asList(patient));

    Patient result =
            patientService
                    .getPatientByPhoneNumber("0771234567");

    assertNotNull(result);
    assertEquals("John Weera",
            result.getFullName());
}


@Test
void testGetPatientByInvalidPhoneNumber()
        throws SQLException {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientDAO.getAllPatients())
            .thenReturn(Arrays.asList(patient));

    Patient result =
            patientService
                    .getPatientByPhoneNumber("0711111111");

    assertNull(result);
}


@Test
void testGetPatientByEmptyPhoneNumber()
        throws SQLException {

    Patient result =
            patientService
                    .getPatientByPhoneNumber("");

    assertNull(result);

    verify(patientDAO, never())
            .getAllPatients();
}

// PATIENT APPOINTMENTS

@Test
void testGetPatientAppointments()
        throws SQLException {

    List<Appointment> appointments =
            new ArrayList<>();

    Appointment appointment1 =
            new Appointment();

    Appointment appointment2 =
            new Appointment();

    appointments.add(appointment1);
    appointments.add(appointment2);

    when(appointmentDAO
            .getAppointmentsByPatientId(1))
            .thenReturn(appointments);

    List<Appointment> result =
            patientService
                    .getPatientAppointments(1);

    assertEquals(2, result.size());

    verify(appointmentDAO)
            .getAppointmentsByPatientId(1);
}


@Test
void testGetPatientAppointmentsInvalidId()
        throws SQLException {

    List<Appointment> result =
            patientService
                    .getPatientAppointments(0);

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(appointmentDAO, never())
            .getAppointmentsByPatientId(anyInt());
}

// APPOINTMENT COUNT

@Test
void testGetPatientAppointmentCount()
        throws SQLException {

    List<Appointment> appointments =
            Arrays.asList(
                    new Appointment(),
                    new Appointment()
            );

    when(appointmentDAO
            .getAppointmentsByPatientId(1))
            .thenReturn(appointments);

    int count =
            patientService
                    .getPatientAppointmentCount(1);

    assertEquals(2, count);
}

// PATIENT EXISTS

@Test
void testPatientExists()
        throws SQLException {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientDAO.getPatientById(1))
            .thenReturn(patient);

    assertTrue(
            patientService.patientExists(1)
    );
}


@Test
void testPatientDoesNotExist()
        throws SQLException {

    when(patientDAO.getPatientById(99))
            .thenReturn(null);

    assertFalse(
            patientService.patientExists(99)
    );
}

// TOTAL PATIENT COUNT
@Test
void testGetTotalPatientCount()
        throws SQLException {

    List<Patient> patients =
            Arrays.asList(
                    new Patient(),
                    new Patient(),
                    new Patient()
            );

    when(patientDAO.getAllPatients())
            .thenReturn(patients);

    int count =
            patientService.getTotalPatientCount();

    assertEquals(3, count);
}

// PATIENT STATISTICS
@Test
void testGetPatientStatistics()
        throws SQLException {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    List<Appointment> appointments =
            Arrays.asList(
                    new Appointment(),
                    new Appointment()
            );

    when(patientDAO.getPatientById(1))
            .thenReturn(patient);

    when(appointmentDAO
            .getAppointmentsByPatientId(1))
            .thenReturn(appointments);

    PatientService.PatientStatistics stats =
            patientService
                    .getPatientStatistics(1);

    assertNotNull(stats);
    assertEquals(patient, stats.getPatient());
    assertEquals(2,
            stats.getTotalAppointments());
}


@Test
void testGetPatientStatisticsForInvalidPatient()
        throws SQLException {

    when(patientDAO.getPatientById(99))
            .thenReturn(null);

    PatientService.PatientStatistics stats =
            patientService
                    .getPatientStatistics(99);

    assertNull(stats);
}

}
