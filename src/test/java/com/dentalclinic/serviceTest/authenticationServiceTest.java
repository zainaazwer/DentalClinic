package com.dentalclinic.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;
import com.dentalclinic.service.AuthenticationService;

public class authenticationServiceTest {

private AuthenticationService authenticationService;
private UserDAO userDAO;


@BeforeEach
void setUp() throws Exception {

    authenticationService =
            new AuthenticationService();

    // Create Mockito mock
    userDAO = mock(UserDAO.class);

    Field userDAOField =
            AuthenticationService.class
                    .getDeclaredField("userDAO");

    userDAOField.setAccessible(true);
    userDAOField.set(
            authenticationService,
            userDAO
    );
}

// LOGIN - SUCCESS
@Test
void testLoginSuccess()
        throws Exception {

    User user = new User(
            1,
            "staff",
            "staff",
            "Staff",
            "staff@gmail.com",
            "Receptionist"
    );

    when(userDAO.authenticateUser(
            "staff",
            "staff"
    )).thenReturn(user);


    User result =
            authenticationService.login(
                    "staff",
                    "staff"
            );


    assertNotNull(result);

    assertEquals(
            "staff",
            result.getUsername()
    );

    assertEquals(
            "Staff",
            result.getFullName()
    );

    assertEquals(
            "Receptionist",
            result.getRole()
    );


    verify(userDAO)
            .authenticateUser(
                    "staff",
                    "staff"
            );
}

// LOGIN - INVALID USERNAME
@Test
void testLoginWithEmptyUsername()
        throws Exception {

    User result =
            authenticationService.login(
                    "",
                    "staff"
            );


    assertNull(result);


    // DAO should not be called
    verify(userDAO, never())
            .authenticateUser(
                    anyString(),
                    anyString()
            );
}

// LOGIN - NULL USERNAME
@Test
void testLoginWithNullUsername()
        throws Exception {

    User result =
            authenticationService.login(
                    null,
                    "staff"
            );


    assertNull(result);


    verify(userDAO, never())
            .authenticateUser(
                    anyString(),
                    anyString()
            );
}

// LOGIN - INVALID PASSWORD
@Test
void testLoginWithEmptyPassword()
        throws Exception {

    User result =
            authenticationService.login(
                    "staff",
                    ""
            );


    assertNull(result);


    verify(userDAO, never())
            .authenticateUser(
                    anyString(),
                    anyString()
            );
}

// LOGIN - NULL PASSWORD

@Test
void testLoginWithNullPassword()
        throws Exception {

    User result =
            authenticationService.login(
                    "staff",
                    null
            );


    assertNull(result);


    verify(userDAO, never())
            .authenticateUser(
                    anyString(),
                    anyString()
            );
}

// LOGIN - WRONG CREDENTIALS
@Test
void testLoginWithWrongCredentials()
        throws Exception {

    when(userDAO.authenticateUser(
            "wrong",
            "wrong"
    )).thenReturn(null);


    User result =
            authenticationService.login(
                    "wrong",
                    "wrong"
            );


    assertNull(result);


    verify(userDAO)
            .authenticateUser(
                    "wrong",
                    "wrong"
            );
}

// LOGIN
@Test
void testLoginTrimsUsernameAndPassword()
        throws Exception {

    User user = new User(
            1,
            "staff",
            "staff",
            "Staff",
            "staff@gmail.com",
            "Receptionist"
    );

    when(userDAO.authenticateUser(
            "staff",
            "staff"
    )).thenReturn(user);


    User result =
            authenticationService.login(
                    "  staff  ",
                    "  staff  "
            );


    assertNotNull(result);


    verify(userDAO)
            .authenticateUser(
                    "staff",
                    "staff"
            );
}

// LOGIN - DATABASE ERROR
@Test
void testLoginDatabaseError()
        throws Exception {

    when(userDAO.authenticateUser(
            "staff",
            "staff"
    )).thenThrow(
            new SQLException(
                    "Database connection error"
            )
    );


    assertThrows(
            SQLException.class,
            () -> authenticationService.login(
                    "staff",
                    "staff"
            )
    );


    verify(userDAO)
            .authenticateUser(
                    "staff",
                    "staff"
            );
}

// AUTHORIZATION - CORRECT ROLE
@Test
void testIsAuthorizedCorrectRole() {

    User user = new User();

    user.setRole("Admin");


    boolean result =
            authenticationService.isAuthorized(
                    user,
                    "Admin"
            );


    assertTrue(result);
}

// AUTHORIZATION - WRONG ROLE
@Test
void testIsAuthorizedWrongRole() {

    User user = new User();

    user.setRole("Receptionist");


    boolean result =
            authenticationService.isAuthorized(
                    user,
                    "Admin"
            );


    assertFalse(result);
}

// AUTHORIZATION - CASE INSENSITIVE

@Test
void testIsAuthorizedCaseInsensitive() {

    User user = new User();

    user.setRole("admin");


    boolean result =
            authenticationService.isAuthorized(
                    user,
                    "ADMIN"
            );


    assertTrue(result);
}

// AUTHORIZATION - NULL USER

@Test
void testIsAuthorizedNullUser() {

    boolean result =
            authenticationService.isAuthorized(
                    null,
                    "Admin"
            );


    assertFalse(result);
}

// AUTHORIZATION - NULL ROLE
@Test
void testIsAuthorizedNullRole() {

    User user = new User();

    user.setRole(null);


    boolean result =
            authenticationService.isAuthorized(
                    user,
                    "Admin"
            );


    assertFalse(result);
}

}
