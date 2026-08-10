package com.dentalclinic.modelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dentalclinic.model.Appointment;

public class appointmentTest {

    // DEFAULT CONSTRUCTOR
    @Test
    void testDefaultConstructor() {

        Appointment appointment = new Appointment();

        assertNotNull(appointment);

        assertEquals(0, appointment.getAppointmentId());
        assertEquals(0, appointment.getPatientId());

        assertNull(appointment.getPatientName());
        assertNull(appointment.getDentistName());
        assertNull(appointment.getTreatmentType());
        assertNull(appointment.getAppointmentDate());
        assertNull(appointment.getAppointmentTime());
    }

    // APPOINTMENT ID
    @Test
    void testAppointmentId() {

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(101);

        assertEquals(
                101,
                appointment.getAppointmentId()
        );
    }

    // PATIENT ID
    @Test
    void testPatientId() {

        Appointment appointment = new Appointment();

        appointment.setPatientId(10);

        assertEquals(
                10,
                appointment.getPatientId()
        );
    }

    // PATIENT NAME
    @Test
    void testPatientName() {

        Appointment appointment = new Appointment();

        appointment.setPatientName("Karan Silva");

        assertEquals(
                "Karan Silva",
                appointment.getPatientName()
        );
    }

    
    // DENTIST NAME
    @Test
    void testDentistName() {

        Appointment appointment = new Appointment();

        appointment.setDentistName("Dr. Perera");

        assertEquals(
                "Dr. Perera",
                appointment.getDentistName()
        );
    }

    // TREATMENT TYPE
    @Test
    void testTreatmentType() {

        Appointment appointment = new Appointment();

        appointment.setTreatmentType("Dental Cleaning");

        assertEquals(
                "Dental Cleaning",
                appointment.getTreatmentType()
        );
    }


    // APPOINTMENT DATE
    @Test
    void testAppointmentDate() {

        Appointment appointment = new Appointment();

        appointment.setAppointmentDate("2026-08-15");

        assertEquals(
                "2026-08-15",
                appointment.getAppointmentDate()
        );
    }


    // APPOINTMENT TIME
    @Test
    void testAppointmentTime() {

        Appointment appointment = new Appointment();

        appointment.setAppointmentTime("10:30");

        assertEquals(
                "10:30",
                appointment.getAppointmentTime()
        );
    }


    // ALL SETTERS AND GETTERS
    @Test
    void testAllAppointmentFields() {

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(101);
        appointment.setPatientId(10);
        appointment.setPatientName("Karan Silva");
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Dental Cleaning");
        appointment.setAppointmentDate("2026-08-15");
        appointment.setAppointmentTime("10:30");


        assertEquals(
                101,
                appointment.getAppointmentId()
        );

        assertEquals(
                10,
                appointment.getPatientId()
        );

        assertEquals(
                "Karan Silva",
                appointment.getPatientName()
        );

        assertEquals(
                "Dr. Perera",
                appointment.getDentistName()
        );

        assertEquals(
                "Dental Cleaning",
                appointment.getTreatmentType()
        );

        assertEquals(
                "2026-08-15",
                appointment.getAppointmentDate()
        );

        assertEquals(
                "10:30",
                appointment.getAppointmentTime()
        );
    }

    // NULL VALUES
    @Test
    void testNullValues() {

        Appointment appointment = new Appointment();

        appointment.setPatientName(null);
        appointment.setDentistName(null);
        appointment.setTreatmentType(null);
        appointment.setAppointmentDate(null);
        appointment.setAppointmentTime(null);


        assertNull(
                appointment.getPatientName()
        );

        assertNull(
                appointment.getDentistName()
        );

        assertNull(
                appointment.getTreatmentType()
        );

        assertNull(
                appointment.getAppointmentDate()
        );

        assertNull(
                appointment.getAppointmentTime()
        );
    }

    // EMPTY STRING VALUES
    @Test
    void testEmptyStringValues() {

        Appointment appointment = new Appointment();

        appointment.setPatientName("");
        appointment.setDentistName("");
        appointment.setTreatmentType("");
        appointment.setAppointmentDate("");
        appointment.setAppointmentTime("");


        assertEquals(
                "",
                appointment.getPatientName()
        );

        assertEquals(
                "",
                appointment.getDentistName()
        );

        assertEquals(
                "",
                appointment.getTreatmentType()
        );

        assertEquals(
                "",
                appointment.getAppointmentDate()
        );

        assertEquals(
                "",
                appointment.getAppointmentTime()
        );
    }
}