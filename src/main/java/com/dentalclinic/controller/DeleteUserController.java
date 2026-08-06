package com.dentalclinic.controller;

import com.dentalclinic.dao.UserDAO;
import com.dentalclinic.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteUser")
public class DeleteUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        User loggedUser = (User) session.getAttribute("user");

        // Only Admin can delete users
        if (!"Admin".equals(loggedUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/Dashboard.jsp");
            return;
        }

        try {

            String id = request.getParameter("userId");

            if (id == null || id.trim().isEmpty()) {
                session.setAttribute("error", "Invalid User ID.");
                response.sendRedirect(request.getContextPath() + "/ManageUsers");
                return;
            }

            int userId = Integer.parseInt(id);

            // Check if user exists
            User user = userDAO.getUserById(userId);

            if (user == null) {
                session.setAttribute("error", "User not found.");
                response.sendRedirect(request.getContextPath() + "/ManageUsers");
                return;
            }

            boolean deleted = userDAO.deleteUser(userId);

            if (deleted) {
                session.setAttribute("success", "User deleted successfully.");
            } else {
                session.setAttribute("error", "Unable to delete user.");
            }

            response.sendRedirect(request.getContextPath() + "/ManageUsers");

        } catch (NumberFormatException e) {

            session.setAttribute("error", "Invalid User ID.");
            response.sendRedirect(request.getContextPath() + "/ManageUsers");

        } catch (Exception e) {

            e.printStackTrace();

            session.setAttribute("error", "Error deleting user.");
            response.sendRedirect(request.getContextPath() + "/ManageUsers");
        }
    }
}