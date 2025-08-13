package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.UserDAO;
import model.User;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
    private UserDAO userDao;

    @Override
    public void init() {
        userDao = new UserDAO();
    }
        
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false);
            if (!isAdminLoggedIn(session)) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }

            String action = request.getPathInfo();  // Will capture action after /admin/ in the URL
            
            try {
                if (action == null || action.equals("/dashboard")) {
                    showDashboard(request, response);
                } else if (action.equals("/view")) {
                    viewUser(request, response);  // Handles /admin/view?id=...
                } else if (action.equals("/delete")) {
                    deleteUser(request, response);  // Handles /admin/delete?id=...
                } else if (action.equals("/logout")) {
                    handleLogout(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        }
    


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    private boolean isAdminLoggedIn(HttpSession session) {
        if (session == null || session.getAttribute("user") == null) {
            return false;
        }
        User user = (User) session.getAttribute("user");
        return "admin@gmail.com".equals(user.getEmail());
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        request.setAttribute("users", userDao.getAll());
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userDao.getById(userId);
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/userDetails.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        int userId = Integer.parseInt(request.getParameter("id"));
        if (userDao.delete(userId)) {
        	response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/auth/login");
    }
}