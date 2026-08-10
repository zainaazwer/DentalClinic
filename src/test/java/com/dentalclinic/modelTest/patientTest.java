package com.dentalclinic.modelTest;

import com.dentalclinic.model.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class patientTest {

@Test
void testDefaultConstructor() {

    Patient patient = new Patient();

    assertNotNull(patient);
    assertEquals(0, patient.getPatientId());
    assertNull(patient.getFullName());
    assertNull(patient.getAddress());
    assertNull(patient.getPhoneNumber());
}

@Test
void testParameterizedConstructor() {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    assertEquals(1, patient.getPatientId());
    assertEquals("John Weera", patient.getFullName());
    assertEquals("Colombo", patient.getAddress());
    assertEquals("0771234567", patient.getPhoneNumber());
}

@Test
void testPatientIdGetterAndSetter() {

    Patient patient = new Patient();

    patient.setPatientId(10);

    assertEquals(10, patient.getPatientId());
}

@Test
void testFullNameGetterAndSetter() {

    Patient patient = new Patient();

    patient.setFullName("John Weera");

    assertEquals("John Weera", patient.getFullName());
}

@Test
void testAddressGetterAndSetter() {

    Patient patient = new Patient();

    patient.setAddress("Colombo");

    assertEquals("Colombo", patient.getAddress());
}

@Test
void testPhoneNumberGetterAndSetter() {

    Patient patient = new Patient();

    patient.setPhoneNumber("0771234567");

    assertEquals("0771234567", patient.getPhoneNumber());
}

@Test
void testToString() {

    Patient patient = new Patient(
            1,
            "John Weera",
            "Colombo",
            "0771234567"
    );

    String result = patient.toString();

    assertTrue(result.contains("1"));
    assertTrue(result.contains("John Weera"));
    assertTrue(result.contains("Colombo"));
    assertTrue(result.contains("0771234567"));
}

}
