package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/changePassword")
public class ChangePasswordController extends HttpServlet {
    private UserDAO userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        User currentUser = (User) session.getAttribute("user");

        if (currentUser.getPassword().equals(oldPassword)) {
            currentUser.setPassword(newPassword);
            try {
				if (userDao.update(currentUser)) {
				    session.setAttribute("user", currentUser); // update session
				    session.setAttribute("success", "Password changed successfully.");
				    response.sendRedirect(request.getContextPath() + "/profile.jsp");
				} else {
				    request.setAttribute("error", "Failed to update password.");
				    request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
				}
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            request.setAttribute("error", "Old password is incorrect.");
            request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
    }

}
