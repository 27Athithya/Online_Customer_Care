package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/user/image")
public class UserImageController extends HttpServlet {

    private UserDAO userDAO = new UserDAO(); // Make sure DAO is accessible

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        try {
            User user = userDAO.getById(userId);  // Fetch the user from the DB
            if (user != null && user.getProfileImage() != null) {
                String profileImagePath = user.getProfileImage();
                
                // Serve image from the file system
                String filePath = getServletContext().getRealPath("/") + File.separator + profileImagePath;
                File file = new File(filePath);
                if (file.exists()) {
                    response.setContentType("image/jpeg"); // Assuming JPEG images
                    response.setContentLength((int) file.length());
                    Files.copy(file.toPath(), response.getOutputStream());
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User or image not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
