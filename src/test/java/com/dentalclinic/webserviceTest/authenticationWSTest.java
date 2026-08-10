package com.dentalclinic.webserviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dentalclinic.model.User;
import com.dentalclinic.service.AuthenticationService;
import com.dentalclinic.webservice.AuthenticationWS;

public class authenticationWSTest {

private AuthenticationWS authenticationWS;
private AuthenticationService authService;


@BeforeEach
void setUp() throws Exception {

    authenticationWS =
            new AuthenticationWS();

    // Create Mockito mock
    authService =
            mock(AuthenticationService.class);

    // with the Mockito mock
    Field authServiceField =
            AuthenticationWS.class
                    .getDeclaredField("authService");

    authServiceField.setAccessible(true);

    authServiceField.set(
            authenticationWS,
            authService
    );
}

// LOGIN - SUCCESS
@Test
void testLoginSuccess()
        throws Exception {

    User loginUser = new User();

    loginUser.setUsername("staff");
    loginUser.setPassword("staff");


    User authenticatedUser =
            new User(
                    1,
                    "staff",
                    "staff",
                    "Staff",
                    "staff@gmail.com",
                    "Receptionist"
            );


    when(authService.login(
            "staff",
            "staff"
    )).thenReturn(authenticatedUser);


    Response response =
            authenticationWS.login(loginUser);


    assertEquals(
            200,
            response.getStatus()
    );

    assertEquals(
            authenticatedUser,
            response.getEntity()
    );


    verify(authService)
            .login(
                    "staff",
                    "staff"
            );


    response.close();
}

// LOGIN - INVALID CREDENTIALS
@Test
void testLoginInvalidCredentials()
        throws Exception {

    User loginUser = new User();

    loginUser.setUsername("wrong");
    loginUser.setPassword("wrong");


    when(authService.login(
            "wrong",
            "wrong"
    )).thenReturn(null);


    Response response =
            authenticationWS.login(loginUser);


    assertEquals(
            401,
            response.getStatus()
    );

    assertEquals(
            "Invalid login",
            response.getEntity()
    );


    verify(authService)
            .login(
                    "wrong",
                    "wrong"
            );


    response.close();
}

// LOGIN - DATABASE / SERVICE ERROR
@Test
void testLoginServiceError()
        throws Exception {

    User loginUser = new User();

    loginUser.setUsername("staff");
    loginUser.setPassword("staff");


    when(authService.login(
            "staff",
            "staff"
    )).thenThrow(
            new RuntimeException(
                    "Authentication service error"
            )
    );


    Response response =
            authenticationWS.login(loginUser);


    assertEquals(
            500,
            response.getStatus()
    );


    verify(authService)
            .login(
                    "staff",
                    "staff"
            );


    response.close();
}

// LOGIN - EMPTY USERNAME
@Test
void testLoginWithEmptyUsername()
        throws Exception {

    User loginUser = new User();

    loginUser.setUsername("");
    loginUser.setPassword("staff");


    when(authService.login(
            "",
            "staff"
    )).thenReturn(null);


    Response response =
            authenticationWS.login(loginUser);


    assertEquals(
            401,
            response.getStatus()
    );

    assertEquals(
            "Invalid login",
            response.getEntity()
    );


    verify(authService)
            .login(
                    "",
                    "staff"
            );


    response.close();
}

// LOGIN - EMPTY PASSWORD
@Test
void testLoginWithEmptyPassword()
        throws Exception {

    User loginUser = new User();

    loginUser.setUsername("staff");
    loginUser.setPassword("");


    when(authService.login(
            "staff",
            ""
    )).thenReturn(null);


    Response response =
            authenticationWS.login(loginUser);


    assertEquals(
            401,
            response.getStatus()
    );

    assertEquals(
            "Invalid login",
            response.getEntity()
    );


    verify(authService)
            .login(
                    "staff",
                    ""
            );


    response.close();
}

// LOGIN - NULL USERNAME
@Test
void testLoginWithNullUsername()
        throws Exception {

    User loginUser = new User();

    loginUser.setUsername(null);
    loginUser.setPassword("staff");


    when(authService.login(
            null,
            "staff"
    )).thenReturn(null);


    Response response =
            authenticationWS.login(loginUser);


    assertEquals(
            401,
            response.getStatus()
    );

    assertEquals(
            "Invalid login",
            response.getEntity()
    );


    verify(authService)
            .login(
                    null,
                    "staff"
            );


    response.close();
}

}
