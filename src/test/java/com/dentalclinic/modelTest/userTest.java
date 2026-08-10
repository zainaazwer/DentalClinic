package com.dentalclinic.modelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dentalclinic.model.User;

public class userTest {

// TEST DEFAULT CONSTRUCTOR
@Test
void testDefaultConstructor() {

    User user = new User();

    assertNotNull(user);
}

// TEST PARAMETERIZED CONSTRUCTOR
@Test
void testParameterizedConstructor() {

    User user = new User(
            1,
            "staff",
            "staff",
            "Staff",
            "staff@gmail.com",
            "Receptionist"
    );

    assertEquals(1, user.getUserId());
    assertEquals("staff", user.getUsername());
    assertEquals("staff", user.getPassword());
    assertEquals("Staff", user.getFullName());
    assertEquals("staff@gmail.com", user.getEmail());
    assertEquals("Receptionist", user.getRole());
}

// TEST USER ID
@Test
void testUserId() {

    User user = new User();

    user.setUserId(10);

    assertEquals(10, user.getUserId());
}

// TEST USERNAME
@Test
void testUsername() {

    User user = new User();

    user.setUsername("staff");

    assertEquals(
            "staff",
            user.getUsername()
    );
}

// TEST PASSWORD
@Test
void testPassword() {

    User user = new User();

    user.setPassword("staff");

    assertEquals(
            "staff",
            user.getPassword()
    );
}

// TEST FULL NAME
@Test
void testFullName() {

    User user = new User();

    user.setFullName("Staff");

    assertEquals(
            "Staff",
            user.getFullName()
    );
}

// TEST EMAIL
@Test
void testEmail() {

    User user = new User();

    user.setEmail("staff@gmail.com");

    assertEquals(
            "staff@gmail.com",
            user.getEmail()
    );
}

// TEST ROLE
@Test
void testRole() {

    User user = new User();

    user.setRole("Receptionist");

    assertEquals(
            "Receptionist",
            user.getRole()
    );
}

// TEST ALL GETTERS AND SETTERS
@Test
void testAllGettersAndSetters() {

    User user = new User();

    user.setUserId(5);
    user.setUsername("admin");
    user.setPassword("admin");
    user.setFullName("Admin");
    user.setEmail("admin@gmail.com");
    user.setRole("Admin");

    assertEquals(5, user.getUserId());
    assertEquals("admin", user.getUsername());
    assertEquals("admin", user.getPassword());
    assertEquals("Admin", user.getFullName());
    assertEquals("admin@gmail.com", user.getEmail());
    assertEquals("Admin", user.getRole());
}

}
