package com.dentalclinic.service;

import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;
import java.sql.SQLException;

public class AuthenticationService {

    private UserDAO userDAO;

    public AuthenticationService() {
        this.userDAO = new UserDAO();
    }


    public User login(String username, String password) throws SQLException {

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            return null;
        }

        return userDAO.authenticateUser(
                username.trim(),
                password.trim()
        );
    }


    public boolean isAuthorized(User user, String requiredRole) {

        if (user == null || user.getRole() == null) {
            return false;
        }

        return user.getRole().equalsIgnoreCase(requiredRole);
    }
}