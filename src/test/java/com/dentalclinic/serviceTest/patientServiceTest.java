package com.dentalclinic.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
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


// VALIDATE PATIENT
@Test
void testValidPatient() {

    Patient patient = new Patient();

    patient.setFullName("John Weera");
    patient.setPhoneNumber("0771234567");

    assertTrue(
            patientService.validatePatient(patient)
    );
}


@Test
void testNullPatient() {

    assertFalse(
            patientService.validatePatient(null)
    );
}


@Test
void testPatientWithoutName() {

    Patient patient = new Patient();

    patient.setFullName("");
    patient.setPhoneNumber("0771234567");

    assertFalse(
            patientService.validatePatient(patient)
    );
}


@Test
void testPatientWithNullName() {

    Patient patient = new Patient();

    patient.setFullName(null);
    patient.setPhoneNumber("0771234567");

    assertFalse(
            patientService.validatePatient(patient)
    );
}


@Test
void testPatientWithoutPhoneNumber() {

    Patient patient = new Patient();

    patient.setFullName("John Weera");
    patient.setPhoneNumber("");

    assertFalse(
            patientService.validatePatient(patient)
    );
}


@Test
void testPatientWithNullPhoneNumber() {

    Patient patient = new Patient();

    patient.setFullName("John Weera");
    patient.setPhoneNumber(null);

    assertFalse(
            patientService.validatePatient(patient)
    );
}


// REGISTER PATIENT
@Test
void testRegisterPatientSuccess() throws SQLException {

    Patient patient = new Patient();

    patient.setFullName("John Weera");
    patient.setAddress("Colombo");
    patient.setPhoneNumber("0771234567");

    when(patientDAO.createPatient(patient))
            .thenReturn(true);

    boolean result =
            patientService.registerPatient(patient);

    assertTrue(result);

    verify(patientDAO)
            .createPatient(patient);
}


@Test
void testRegisterPatientInvalid() throws SQLException {

    Patient patient = new Patient();

    patient.setFullName("");
    patient.setPhoneNumber("0771234567");

    boolean result =
            patientService.registerPatient(patient);

    assertFalse(result);

    verify(
            patientDAO,
            never()
    ).createPatient(any(Patient.class));
}


@Test
void testRegisterPatientNull() throws SQLException {

    boolean result =
            patientService.registerPatient(null);

    assertFalse(result);

    verify(
            patientDAO,
            never()
    ).createPatient(any(Patient.class));
}


@Test
void testRegisterPatientDAOFailure() throws SQLException {

    Patient patient = new Patient();

    patient.setFullName("John Weera");
    patient.setPhoneNumber("0771234567");

    when(patientDAO.createPatient(patient))
            .thenReturn(false);

    boolean result =
            patientService.registerPatient(patient);

    assertFalse(result);

    verify(patientDAO)
            .createPatient(patient);
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

    assertEquals(
            1,
            result.getPatientId()
    );

    assertEquals(
            "John Weera",
            result.getFullName()
    );

    assertEquals(
            "Colombo",
            result.getAddress()
    );

    assertEquals(
            "0771234567",
            result.getPhoneNumber()
    );

    verify(patientDAO)
            .getPatientById(1);
}


@Test
void testGetPatientByInvalidId() throws SQLException {

    Patient result =
            patientService.getPatientById(0);

    assertNull(result);

    verify(
            patientDAO,
            never()
    ).getPatientById(anyInt());
}


@Test
void testGetPatientByIdNotFound() throws SQLException {

    when(patientDAO.getPatientById(99))
            .thenReturn(null);

    Patient result =
            patientService.getPatientById(99);

    assertNull(result);

    verify(patientDAO)
            .getPatientById(99);
}


@Test
void testGetPatientByIdSQLException() throws SQLException {

    when(patientDAO.getPatientById(1))
            .thenThrow(
                    new SQLException("Database error")
            );

    assertThrows(
            SQLException.class,
            () -> patientService.getPatientById(1)
    );

    verify(patientDAO)
            .getPatientById(1);
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
            Arrays.asList(
                    patient1,
                    patient2
            );

    when(patientDAO.getAllPatients())
            .thenReturn(patients);

    List<Patient> result =
            patientService.getAllPatients();

    assertNotNull(result);

    assertEquals(
            2,
            result.size()
    );

    assertEquals(
            "John Weera",
            result.get(0).getFullName()
    );

    assertEquals(
            "Jenny Perera",
            result.get(1).getFullName()
    );

    verify(patientDAO)
            .getAllPatients();
}


@Test
void testGetAllPatientsEmpty() throws SQLException {

    when(patientDAO.getAllPatients())
            .thenReturn(Collections.emptyList());

    List<Patient> result =
            patientService.getAllPatients();

    assertNotNull(result);

    assertTrue(result.isEmpty());

    verify(patientDAO)
            .getAllPatients();
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
            .thenReturn(
                    Arrays.asList(
                            patient1,
                            patient2
                    )
            );

    List<Patient> result =
            patientService.searchPatientsByName("john");

    assertNotNull(result);

    assertEquals(
            1,
            result.size()
    );

    assertEquals(
            "John Weera",
            result.get(0).getFullName()
    );

    verify(patientDAO)
            .getAllPatients();
}


@Test
void testSearchPatientsByNameCaseInsensitive()
        throws SQLException {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientDAO.getAllPatients())
            .thenReturn(
                    Collections.singletonList(patient)
            );

    List<Patient> result =
            patientService.searchPatientsByName("JOHN");

    assertNotNull(result);

    assertEquals(
            1,
            result.size()
    );

    assertEquals(
            "John Weera",
            result.get(0).getFullName()
    );
}


@Test
void testSearchPatientsWithEmptyName()
        throws SQLException {

    List<Patient> result =
            patientService.searchPatientsByName("");

    assertNotNull(result);

    assertTrue(result.isEmpty());

    verify(
            patientDAO,
            never()
    ).getAllPatients();
}


@Test
void testSearchPatientsWithNullName()
        throws SQLException {

    List<Patient> result =
            patientService.searchPatientsByName(null);

    assertNotNull(result);

    assertTrue(result.isEmpty());

    verify(
            patientDAO,
            never()
    ).getAllPatients();
}


// SEARCH BY PHONE NUMBER
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
            .thenReturn(
                    Collections.singletonList(patient)
            );

    Patient result =
            patientService.getPatientByPhoneNumber(
                    "0771234567"
            );

    assertNotNull(result);

    assertEquals(
            "John Weera",
            result.getFullName()
    );

    assertEquals(
            "0771234567",
            result.getPhoneNumber()
    );

    verify(patientDAO)
            .getAllPatients();
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
            .thenReturn(
                    Collections.singletonList(patient)
            );

    Patient result =
            patientService.getPatientByPhoneNumber(
                    "0711111111"
            );

    assertNull(result);

    verify(patientDAO)
            .getAllPatients();
}


@Test
void testGetPatientByEmptyPhoneNumber()
        throws SQLException {

    Patient result =
            patientService.getPatientByPhoneNumber("");

    assertNull(result);

    verify(
            patientDAO,
            never()
    ).getAllPatients();
}


@Test
void testGetPatientByNullPhoneNumber()
        throws SQLException {

    Patient result =
            patientService.getPatientByPhoneNumber(null);

    assertNull(result);

    verify(
            patientDAO,
            never()
    ).getAllPatients();
}


// UPDATE PATIENT
@Test
void testUpdatePatientSuccess()
        throws SQLException {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    when(patientDAO.updatePatient(patient))
            .thenReturn(true);

    boolean result =
            patientService.updatePatient(patient);

    assertTrue(result);

    verify(patientDAO)
            .updatePatient(patient);
}


@Test
void testUpdatePatientInvalid()
        throws SQLException {

    Patient patient = new Patient();

    patient.setFullName("");
    patient.setPhoneNumber("0771234567");

    boolean result =
            patientService.updatePatient(patient);

    assertFalse(result);

    verify(
            patientDAO,
            never()
    ).updatePatient(any(Patient.class));
}


@Test
void testUpdatePatientNull()
        throws SQLException {

    boolean result =
            patientService.updatePatient(null);

    assertFalse(result);

    verify(
            patientDAO,
            never()
    ).updatePatient(any(Patient.class));
}


// PATIENT APPOINTMENTS
@Test
void testGetPatientAppointments()
        throws SQLException {

    Appointment appointment1 =
            new Appointment();

    Appointment appointment2 =
            new Appointment();

    List<Appointment> appointments =
            Arrays.asList(
                    appointment1,
                    appointment2
            );

    when(
            appointmentDAO
                    .getAppointmentsByPatientId(1)
    ).thenReturn(appointments);

    List<Appointment> result =
            patientService.getPatientAppointments(1);

    assertNotNull(result);

    assertEquals(
            2,
            result.size()
    );

    verify(appointmentDAO)
            .getAppointmentsByPatientId(1);
}


@Test
void testGetPatientAppointmentsInvalidId()
        throws SQLException {

    List<Appointment> result =
            patientService.getPatientAppointments(0);

    assertNotNull(result);

    assertTrue(result.isEmpty());

    verify(
            appointmentDAO,
            never()
    ).getAppointmentsByPatientId(anyInt());
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

    when(
            appointmentDAO
                    .getAppointmentsByPatientId(1)
    ).thenReturn(appointments);

    int count =
            patientService.getPatientAppointmentCount(1);

    assertEquals(
            2,
            count
    );

    verify(appointmentDAO)
            .getAppointmentsByPatientId(1);
}


@Test
void testGetPatientAppointmentCountInvalidId()
        throws SQLException {

    int count =
            patientService.getPatientAppointmentCount(0);

    assertEquals(
            0,
            count
    );

    verify(
            appointmentDAO,
            never()
    ).getAppointmentsByPatientId(anyInt());
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

    verify(patientDAO)
            .getPatientById(1);
}


@Test
void testPatientDoesNotExist()
        throws SQLException {

    when(patientDAO.getPatientById(99))
            .thenReturn(null);

    assertFalse(
            patientService.patientExists(99)
    );

    verify(patientDAO)
            .getPatientById(99);
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

    assertEquals(
            3,
            count
    );

    verify(patientDAO)
            .getAllPatients();
}


@Test
void testGetTotalPatientCountEmpty()
        throws SQLException {

    when(patientDAO.getAllPatients())
            .thenReturn(Collections.emptyList());

    int count =
            patientService.getTotalPatientCount();

    assertEquals(
            0,
            count
    );

    verify(patientDAO)
            .getAllPatients();
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

    when(
            appointmentDAO
                    .getAppointmentsByPatientId(1)
    ).thenReturn(appointments);

    PatientService.PatientStatistics stats =
            patientService.getPatientStatistics(1);

    assertNotNull(stats);

    assertEquals(
            patient,
            stats.getPatient()
    );

    assertEquals(
            2,
            stats.getTotalAppointments()
    );

    verify(patientDAO)
            .getPatientById(1);

    verify(appointmentDAO)
            .getAppointmentsByPatientId(1);
}


@Test
void testGetPatientStatisticsForInvalidPatient()
        throws SQLException {

    when(patientDAO.getPatientById(99))
            .thenReturn(null);

    PatientService.PatientStatistics stats =
            patientService.getPatientStatistics(99);

    assertNull(stats);

    verify(patientDAO)
            .getPatientById(99);

    verify(
            appointmentDAO,
            never()
    ).getAppointmentsByPatientId(anyInt());
}

}
