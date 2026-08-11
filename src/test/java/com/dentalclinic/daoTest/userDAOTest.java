package com.dentalclinic.daoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dentalclinic.dao.DatabaseConnection;
import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;

public class userDAOTest {

// AUTHENTICATE USER - SUCCESS
@Test
void testAuthenticateUserSuccess() throws Exception {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    UserDAO userDAO = new UserDAO();

    when(connection.prepareStatement(anyString()))
            .thenReturn(statement);

    when(statement.executeQuery())
            .thenReturn(resultSet);

    when(resultSet.next())
            .thenReturn(true);

    when(resultSet.getInt("userId"))
            .thenReturn(1);

    when(resultSet.getString("username"))
            .thenReturn("staff");

    when(resultSet.getString("password"))
            .thenReturn("staff");

    when(resultSet.getString("fullName"))
            .thenReturn("Staff");

    when(resultSet.getString("email"))
            .thenReturn("staff@gmail.com");

    when(resultSet.getString("role"))
            .thenReturn("Receptionist");

    try (MockedStatic<DatabaseConnection> mocked =
                 Mockito.mockStatic(DatabaseConnection.class)) {

        mocked.when(DatabaseConnection::getConnection)
                .thenReturn(connection);

        User user = userDAO.authenticateUser(
                "staff",
                "staff"
        );

        assertNotNull(user);

        assertEquals(1, user.getUserId());
        assertEquals("staff", user.getUsername());
        assertEquals("Staff", user.getFullName());
        assertEquals("staff@gmail.com", user.getEmail());
        assertEquals("Receptionist", user.getRole());
    }
}


// AUTHENTICATE USER - INVALID
@Test
void testAuthenticateUserNotFound() throws Exception {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    UserDAO userDAO = new UserDAO();

    when(connection.prepareStatement(anyString()))
            .thenReturn(statement);

    when(statement.executeQuery())
            .thenReturn(resultSet);

    when(resultSet.next())
            .thenReturn(false);

    try (MockedStatic<DatabaseConnection> mocked =
                 Mockito.mockStatic(DatabaseConnection.class)) {

        mocked.when(DatabaseConnection::getConnection)
                .thenReturn(connection);

        User user = userDAO.authenticateUser(
                "wrong",
                "wrong"
        );

        assertNull(user);
    }
}


// ADD USER - SUCCESS
@Test
void testAddUserSuccess() throws Exception {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);

    UserDAO userDAO = new UserDAO();

    User user = new User(
            1,
            "staff",
            "staff",
            "Staff",
            "staff@gmail.com",
            "Receptionist"
    );

    when(connection.prepareStatement(anyString()))
            .thenReturn(statement);

    when(statement.executeUpdate())
            .thenReturn(1);

    try (MockedStatic<DatabaseConnection> mocked =
                 Mockito.mockStatic(DatabaseConnection.class)) {

        mocked.when(DatabaseConnection::getConnection)
                .thenReturn(connection);

        boolean result = userDAO.addUser(user);

        assertTrue(result);

        verify(statement).setString(1, "staff");
        verify(statement).setString(2, "staff");
        verify(statement).setString(3, "Staff");
        verify(statement).setString(4, "staff@gmail.com");
        verify(statement).setString(5, "Receptionist");

        verify(statement).executeUpdate();
    }
}


// ADD USER - FAILURE
@Test
void testAddUserFailure() throws Exception {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);

    UserDAO userDAO = new UserDAO();

    User user = new User(
            1,
            "staff",
            "staff",
            "Staff",
            "staff@gmail.com",
            "Receptionist"
    );

    when(connection.prepareStatement(anyString()))
            .thenReturn(statement);

    when(statement.executeUpdate())
            .thenReturn(0);

    try (MockedStatic<DatabaseConnection> mocked =
                 Mockito.mockStatic(DatabaseConnection.class)) {

        mocked.when(DatabaseConnection::getConnection)
                .thenReturn(connection);

        boolean result = userDAO.addUser(user);

        assertFalse(result);
    }
}


// GET USER BY ID - SUCCESS
@Test
void testGetUserById() throws Exception {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    UserDAO userDAO = new UserDAO();

    when(connection.prepareStatement(anyString()))
            .thenReturn(statement);

    when(statement.executeQuery())
            .thenReturn(resultSet);

    when(resultSet.next())
            .thenReturn(true);

    when(resultSet.getInt("userId"))
            .thenReturn(5);

    when(resultSet.getString("username"))
            .thenReturn("admin");

    when(resultSet.getString("password"))
            .thenReturn("admin");

    when(resultSet.getString("fullName"))
            .thenReturn("Admin");

    when(resultSet.getString("email"))
            .thenReturn("admin@gmail.com");

    when(resultSet.getString("role"))
            .thenReturn("Admin");

    try (MockedStatic<DatabaseConnection> mocked =
                 Mockito.mockStatic(DatabaseConnection.class)) {

        mocked.when(DatabaseConnection::getConnection)
                .thenReturn(connection);

        User user = userDAO.getUserById(5);

        assertNotNull(user);

        assertEquals(5, user.getUserId());
        assertEquals("admin", user.getUsername());
        assertEquals("Admin", user.getFullName());
        assertEquals("admin@gmail.com", user.getEmail());
        assertEquals("Admin", user.getRole());
    }
}


// GET USER BY ID - NOT FOUND
@Test
void testGetUserByIdNotFound() throws Exception {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    UserDAO userDAO = new UserDAO();

    when(connection.prepareStatement(anyString()))
            .thenReturn(statement);

    when(statement.executeQuery())
            .thenReturn(resultSet);

    when(resultSet.next())
            .thenReturn(false);

    try (MockedStatic<DatabaseConnection> mocked =
                 Mockito.mockStatic(DatabaseConnection.class)) {

        mocked.when(DatabaseConnection::getConnection)
                .thenReturn(connection);

        User user = userDAO.getUserById(999);

        assertNull(user);
    }
}


// USER EXISTS
@Test
void testUserExists() throws Exception {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    UserDAO userDAO = new UserDAO();

    when(connection.prepareStatement(anyString()))
            .thenReturn(statement);

    when(statement.executeQuery())
            .thenReturn(resultSet);

    when(resultSet.next())
            .thenReturn(true);

    when(resultSet.getInt(1))
            .thenReturn(1);

    try (MockedStatic<DatabaseConnection> mocked =
                 Mockito.mockStatic(DatabaseConnection.class)) {

        mocked.when(DatabaseConnection::getConnection)
                .thenReturn(connection);

        boolean exists = userDAO.userExists("john123");

        assertTrue(exists);
    }
}


// USER DOES NOT EXIST
@Test
void testUserDoesNotExist() throws Exception {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    UserDAO userDAO = new UserDAO();

    when(connection.prepareStatement(anyString()))
            .thenReturn(statement);

    when(statement.executeQuery())
            .thenReturn(resultSet);

    when(resultSet.next())
            .thenReturn(true);

    when(resultSet.getInt(1))
            .thenReturn(0);

    try (MockedStatic<DatabaseConnection> mocked =
                 Mockito.mockStatic(DatabaseConnection.class)) {

        mocked.when(DatabaseConnection::getConnection)
                .thenReturn(connection);

        boolean exists = userDAO.userExists("unknown");

        assertFalse(exists);
    }
}


// VALIDATE USER - VALID
@Test
void testValidUser() {

    UserDAO userDAO = new UserDAO();

    User user = new User(
            1,
            "staff",
            "staff",
            "Staff",
            "staff@gmail.com",
            "Receptionist"
    );

    assertTrue(
            userDAO.isValidUser(user)
    );
}


// VALIDATE USER - INVALID
@Test
void testInvalidUser() {

    UserDAO userDAO = new UserDAO();

    User user = new User();

    assertFalse(
            userDAO.isValidUser(user)
    );
}


// VALIDATE EMAIL - VALID
@Test
void testValidEmail() {

    UserDAO userDAO = new UserDAO();

    assertTrue(
            userDAO.isValidEmail(
                    "staff@gmail.com"
            )
    );
}


// VALIDATE EMAIL - INVALID
@Test
void testInvalidEmail() {

    UserDAO userDAO = new UserDAO();

    assertFalse(
            userDAO.isValidEmail(
                    "staff"
            )
    );
}


// VALIDATE USERNAME - VALID
@Test
void testValidUsername() {

    UserDAO userDAO = new UserDAO();

    assertTrue(
            userDAO.isValidUsername(
                    "staff"
            )
    );
}


// VALIDATE USERNAME - INVALID
@Test
void testInvalidUsername() {

    UserDAO userDAO = new UserDAO();

    assertFalse(
            userDAO.isValidUsername(
                    "ab"
            )
    );
}

}
