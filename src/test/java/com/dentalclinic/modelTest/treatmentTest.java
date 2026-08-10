package com.dentalclinic.modelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dentalclinic.model.Treatment;

public class treatmentTest {

    // Test default constructor
    @Test
    void testDefaultConstructor() {

        Treatment treatment = new Treatment();

        assertNotNull(treatment);
    }


    // Test parameterized constructor
    @Test
    void testParameterizedConstructor() {

        Treatment treatment = new Treatment(
                1,
                "Dental Cleaning",
                "Routine dental cleaning",
                5000.00
        );

        assertEquals(
                1,
                treatment.getTreatmentId()
        );

        assertEquals(
                "Dental Cleaning",
                treatment.getTreatmentName()
        );

        assertEquals(
                "Routine dental cleaning",
                treatment.getDescription()
        );

        assertEquals(
                5000.00,
                treatment.getTreatmentCost()
        );
    }


    // Test treatment ID getter and setter
    @Test
    void testTreatmentId() {

        Treatment treatment = new Treatment();

        treatment.setTreatmentId(10);

        assertEquals(
                10,
                treatment.getTreatmentId()
        );
    }


    // Test treatment name getter and setter
    @Test
    void testTreatmentName() {

        Treatment treatment = new Treatment();

        treatment.setTreatmentName(
                "Root Canal"
        );

        assertEquals(
                "Root Canal",
                treatment.getTreatmentName()
        );
    }


    // Test description getter and setter
    @Test
    void testDescription() {

        Treatment treatment = new Treatment();

        treatment.setDescription(
                "Root canal treatment"
        );

        assertEquals(
                "Root canal treatment",
                treatment.getDescription()
        );
    }


    // Test treatment cost getter and setter
    @Test
    void testTreatmentCost() {

        Treatment treatment = new Treatment();

        treatment.setTreatmentCost(
                15000.00
        );

        assertEquals(
                15000.00,
                treatment.getTreatmentCost()
        );
    }


    // Test zero treatment cost
    @Test
    void testZeroTreatmentCost() {

        Treatment treatment = new Treatment();

        treatment.setTreatmentCost(0.0);

        assertEquals(
                0.0,
                treatment.getTreatmentCost()
        );
    }


    // Test complete object
    @Test
    void testCompleteTreatmentObject() {

        Treatment treatment = new Treatment();

        treatment.setTreatmentId(5);
        treatment.setTreatmentName("Tooth Extraction");
        treatment.setDescription("Simple tooth extraction");
        treatment.setTreatmentCost(7500.00);

        assertEquals(
                5,
                treatment.getTreatmentId()
        );

        assertEquals(
                "Tooth Extraction",
                treatment.getTreatmentName()
        );

        assertEquals(
                "Simple tooth extraction",
                treatment.getDescription()
        );

        assertEquals(
                7500.00,
                treatment.getTreatmentCost()
        );
    }


    // Test toString method
    @Test
    void testToString() {

        Treatment treatment = new Treatment(
                1,
                "Dental Cleaning",
                "Routine dental cleaning",
                5000.00
        );

        String result = treatment.toString();

        assertNotNull(result);

        assertTrue(
                result.contains("Dental Cleaning")
        );

        assertTrue(
                result.contains("Routine dental cleaning")
        );

        assertTrue(
                result.contains("5000.0")
        );
    }
}
