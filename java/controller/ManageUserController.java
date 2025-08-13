//package controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import dao.UserDAO;
//import model.User;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//@WebServlet("/admin/manage-users")
//public class ManageUserController extends HttpServlet {
//    private UserDAO userDao;
//
//    @Override
//    public void init() {
//        userDao = new UserDAO();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        try {
//            List<User> users = userDao.getAll();
//            request.setAttribute("users", users);
//            request.getRequestDispatcher("/manageUser.jsp").forward(request, response);
//        } catch (SQLException e) {
//            throw new ServletException("Cannot retrieve users", e);
//        }
//    }
//}


package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import dao.UserDAO;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/manage-users")
public class ManageUserController extends HttpServlet {
    private UserDAO userDao;

    @Override
    public void init() {
        userDao = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<User> users = userDao.getAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/manageUser.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot retrieve users", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            try {
                int userId = Integer.parseInt(request.getParameter("id"));
                userDao.delete(userId);
            } catch (SQLException e) {
                throw new ServletException("Error deleting user", e);
            }
        }

        // After deletion or other actions, reload and display user list
        doGet(request, response);
    }
}
