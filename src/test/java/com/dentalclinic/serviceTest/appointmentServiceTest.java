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
import com.dentalclinic.dao.TreatmentDAO;
import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Treatment;
import com.dentalclinic.service.AppointmentService;

public class appointmentServiceTest {

    private AppointmentService appointmentService;

    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private TreatmentDAO treatmentDAO;


    @BeforeEach
    void setUp() throws Exception {

        appointmentService = new AppointmentService();

        appointmentDAO = mock(AppointmentDAO.class);
        patientDAO = mock(PatientDAO.class);
        treatmentDAO = mock(TreatmentDAO.class);

        Field appointmentDAOField =
                AppointmentService.class
                        .getDeclaredField("appointmentDAO");

        appointmentDAOField.setAccessible(true);

        appointmentDAOField.set(
                appointmentService,
                appointmentDAO
        );

        Field patientDAOField =
                AppointmentService.class
                        .getDeclaredField("patientDAO");

        patientDAOField.setAccessible(true);

        patientDAOField.set(
                appointmentService,
                patientDAO
        );

        Field treatmentDAOField =
                AppointmentService.class
                        .getDeclaredField("treatmentDAO");

        treatmentDAOField.setAccessible(true);

        treatmentDAOField.set(
                appointmentService,
                treatmentDAO
        );
    }

    // REGISTER APPOINTMENT - SUCCESS
    @Test
    void testRegisterAppointmentSuccess() throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(1);
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");
        appointment.setTreatmentType("Cleaning");


        when(appointmentDAO.createAppointment(appointment))
                .thenReturn(true);


        boolean result =
                appointmentService.registerAppointment(
                        appointment
                );


        assertTrue(result);

        verify(appointmentDAO)
                .createAppointment(appointment);
    }

    // REGISTER APPOINTMENT - NULL
    @Test
    void testRegisterAppointmentNull() throws Exception {

        boolean result =
                appointmentService.registerAppointment(null);


        assertFalse(result);

        verify(
                appointmentDAO,
                never()
        ).createAppointment(any());
    }

    // REGISTER APPOINTMENT - INVALID PATIENT ID
    @Test
    void testRegisterAppointmentInvalidPatientId()
            throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(0);
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");
        appointment.setTreatmentType("Cleaning");


        boolean result =
                appointmentService.registerAppointment(
                        appointment
                );


        assertFalse(result);

        verify(
                appointmentDAO,
                never()
        ).createAppointment(any());
    }

    // REGISTER APPOINTMENT - NULL DATE
    @Test
    void testRegisterAppointmentNullDate()
            throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(1);
        appointment.setAppointmentDate(null);
        appointment.setAppointmentTime("10:30");
        appointment.setTreatmentType("Cleaning");


        boolean result =
                appointmentService.registerAppointment(
                        appointment
                );


        assertFalse(result);

        verify(
                appointmentDAO,
                never()
        ).createAppointment(any());
    }

    // REGISTER APPOINTMENT - NULL TIME
    @Test
    void testRegisterAppointmentNullTime()
            throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(1);
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime(null);
        appointment.setTreatmentType("Cleaning");


        boolean result =
                appointmentService.registerAppointment(
                        appointment
                );


        assertFalse(result);

        verify(
                appointmentDAO,
                never()
        ).createAppointment(any());
    }

    // REGISTER APPOINTMENT - EMPTY TREATMENT
    @Test
    void testRegisterAppointmentEmptyTreatment()
            throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(1);
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");
        appointment.setTreatmentType("");


        boolean result =
                appointmentService.registerAppointment(
                        appointment
                );


        assertFalse(result);

        verify(
                appointmentDAO,
                never()
        ).createAppointment(any());
    }


    // REGISTER APPOINTMENT - DAO FAILURE
    @Test
    void testRegisterAppointmentDAOFailure()
            throws Exception {

        Appointment appointment = new Appointment();

        appointment.setPatientId(1);
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");
        appointment.setTreatmentType("Cleaning");


        when(appointmentDAO.createAppointment(appointment))
                .thenReturn(false);


        boolean result =
                appointmentService.registerAppointment(
                        appointment
                );


        assertFalse(result);

        verify(appointmentDAO)
                .createAppointment(appointment);
    }


    // GET APPOINTMENT BY ID - SUCCESS
    @Test
    void testGetAppointmentByIdSuccess()
            throws Exception {

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatientId(10);
        appointment.setPatientName("Karan Silva");
        appointment.setTreatmentType("Cleaning");


        when(appointmentDAO.getAppointmentById(1))
                .thenReturn(appointment);


        Appointment result =
                appointmentService.getAppointmentById(1);


        assertNotNull(result);

        assertEquals(
                1,
                result.getAppointmentId()
        );

        assertEquals(
                "Karan Silva",
                result.getPatientName()
        );

        verify(appointmentDAO)
                .getAppointmentById(1);
    }

    // GET APPOINTMENT BY ID - INVALID
    @Test
    void testGetAppointmentByIdInvalid()
            throws Exception {

        Appointment result =
                appointmentService.getAppointmentById(0);


        assertNull(result);

        verify(
                appointmentDAO,
                never()
        ).getAppointmentById(anyInt());
    }

    // GET APPOINTMENT BY ID - NOT FOUND
    @Test
    void testGetAppointmentByIdNotFound()
            throws Exception {

        when(appointmentDAO.getAppointmentById(999))
                .thenReturn(null);


        Appointment result =
                appointmentService.getAppointmentById(999);


        assertNull(result);

        verify(appointmentDAO)
                .getAppointmentById(999);
    }


    // GET APPOINTMENTS BY PATIENT - SUCCESS
    @Test
    void testGetAppointmentsByPatientIdSuccess()
            throws Exception {

        Appointment appointment1 = new Appointment();

        appointment1.setAppointmentId(1);
        appointment1.setPatientId(10);


        Appointment appointment2 = new Appointment();

        appointment2.setAppointmentId(2);
        appointment2.setPatientId(10);


        List<Appointment> appointments =
                Arrays.asList(
                        appointment1,
                        appointment2
                );


        when(
                appointmentDAO
                        .getAppointmentsByPatientId(10)
        ).thenReturn(appointments);


        List<Appointment> result =
                appointmentService
                        .getAppointmentsByPatientId(10);


        assertNotNull(result);

        assertEquals(2, result.size());

        verify(appointmentDAO)
                .getAppointmentsByPatientId(10);
    }


    // GET APPOINTMENTS BY PATIENT - INVALID
    @Test
    void testGetAppointmentsByPatientIdInvalid()
            throws Exception {

        List<Appointment> result =
                appointmentService
                        .getAppointmentsByPatientId(0);


        assertNull(result);

        verify(
                appointmentDAO,
                never()
        ).getAppointmentsByPatientId(anyInt());
    }


    // GET ALL APPOINTMENTS
    @Test
    void testGetAllAppointments()
            throws Exception {

        List<Appointment> appointments =
                Arrays.asList(
                        new Appointment(),
                        new Appointment()
                );


        when(appointmentDAO.getAllAppointments())
                .thenReturn(appointments);


        List<Appointment> result =
                appointmentService.getAllAppointments();


        assertNotNull(result);

        assertEquals(2, result.size());

        verify(appointmentDAO)
                .getAllAppointments();
    }


    // GET ALL APPOINTMENTS - EMPTY
    @Test
    void testGetAllAppointmentsEmpty()
            throws Exception {

        when(appointmentDAO.getAllAppointments())
                .thenReturn(Collections.emptyList());


        List<Appointment> result =
                appointmentService.getAllAppointments();


        assertNotNull(result);

        assertTrue(result.isEmpty());

        verify(appointmentDAO)
                .getAllAppointments();
    }


    @Test
    void testGetPatientByIdSuccess()
            throws Exception {

        Patient patient = new Patient();

        patient.setPatientId(10);
        patient.setFullName("Karan Silva");
        patient.setAddress("Colombo");
        patient.setPhoneNumber("0771234567");


        when(patientDAO.getPatientById(10))
                .thenReturn(patient);


        Patient result =
                appointmentService.getPatientById(10);


        assertNotNull(result);

        assertEquals(
                10,
                result.getPatientId()
        );

        assertEquals(
                "John Silva",
                result.getFullName()
        );

        verify(patientDAO)
                .getPatientById(10);
    }

    // GET PATIENT BY ID - INVALID
    @Test
    void testGetPatientByIdInvalid()
            throws Exception {

        Patient result =
                appointmentService.getPatientById(0);


        assertNull(result);

        verify(
                patientDAO,
                never()
        ).getPatientById(anyInt());
    }

    // CREATE PATIENT - SUCCESS
    @Test
    void testCreatePatientSuccess()
            throws Exception {

        Patient patient = new Patient();

        patient.setFullName("Karan Silva");
        patient.setAddress("Colombo");
        patient.setPhoneNumber("0771234567");


        when(patientDAO.createPatient(patient))
                .thenReturn(true);


        boolean result =
                appointmentService.createPatient(patient);


        assertTrue(result);

        verify(patientDAO)
                .createPatient(patient);
    }

    // CREATE PATIENT - NULL
    @Test
    void testCreatePatientNull()
            throws Exception {

        boolean result =
                appointmentService.createPatient(null);


        assertFalse(result);

        verify(
                patientDAO,
                never()
        ).createPatient(any());
    }

    // CREATE PATIENT - EMPTY NAME
    @Test
    void testCreatePatientEmptyName()
            throws Exception {

        Patient patient = new Patient();

        patient.setFullName("");
        patient.setAddress("Colombo");
        patient.setPhoneNumber("0771234567");


        boolean result =
                appointmentService.createPatient(patient);


        assertFalse(result);

        verify(
                patientDAO,
                never()
        ).createPatient(any());
    }

    // GET ALL TREATMENTS
    @Test
    void testGetAllTreatments()
            throws Exception {

        Treatment treatment1 = new Treatment();

        treatment1.setTreatmentId(1);
        treatment1.setTreatmentName("Cleaning");
        treatment1.setTreatmentCost(50.00);


        Treatment treatment2 = new Treatment();

        treatment2.setTreatmentId(2);
        treatment2.setTreatmentName("Filling");
        treatment2.setTreatmentCost(100.00);


        List<Treatment> treatments =
                Arrays.asList(
                        treatment1,
                        treatment2
                );


        when(treatmentDAO.getAllTreatments())
                .thenReturn(treatments);


        List<Treatment> result =
                appointmentService.getAllTreatments();


        assertNotNull(result);

        assertEquals(2, result.size());

        assertEquals(
                "Cleaning",
                result.get(0).getTreatmentName()
        );

        verify(treatmentDAO)
                .getAllTreatments();
    }


    // GET TREATMENT BY ID - SUCCESS
    @Test
    void testGetTreatmentByIdSuccess()
            throws Exception {

        Treatment treatment = new Treatment();

        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Cleaning");
        treatment.setDescription("Dental cleaning");
        treatment.setTreatmentCost(50.00);


        when(treatmentDAO.getTreatmentById(1))
                .thenReturn(treatment);


        Treatment result =
                appointmentService.getTreatmentById(1);


        assertNotNull(result);

        assertEquals(
                1,
                result.getTreatmentId()
        );

        assertEquals(
                "Cleaning",
                result.getTreatmentName()
        );

        assertEquals(
                50.00,
                result.getTreatmentCost()
        );

        verify(treatmentDAO)
                .getTreatmentById(1);
    }


    // GET TREATMENT BY ID - INVALID
    @Test
    void testGetTreatmentByIdInvalid()
            throws Exception {

        Treatment result =
                appointmentService.getTreatmentById(0);


        assertNull(result);

        verify(
                treatmentDAO,
                never()
        ).getTreatmentById(anyInt());
    }

    // DAO EXCEPTION
    @Test
    void testGetAppointmentByIdSQLException()
            throws Exception {

        when(appointmentDAO.getAppointmentById(1))
                .thenThrow(
                        new SQLException(
                                "Database error"
                        )
                );


        assertThrows(
                SQLException.class,
                () -> appointmentService
                        .getAppointmentById(1)
        );
    }
}