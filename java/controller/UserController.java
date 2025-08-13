package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import dao.UserDAO;
import model.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

@WebServlet("/user/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
                 maxFileSize = 1024 * 1024 * 5, 
                 maxRequestSize = 1024 * 1024 * 5 * 5)
public class UserController extends HttpServlet {
    private UserDAO userDao;
    private String uploadPath;

    @Override
    public void init() throws ServletException {
        userDao = new UserDAO();
        uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String action = request.getPathInfo();
        if (action == null || action.equals("/profile")) {
            showProfile(request, response);
        }
        //
//        else if (action.equals("/changePassword")) {
//            request.getRequestDispatcher("/user/changePassword.jsp").forward(request, response);
//        }
        //
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String action = request.getPathInfo();
        if (action != null && action.equals("/update")) {
            updateProfile(request, response);
        }
        
        
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("user/profile.jsp").forward(request, response);
    }

    
    private void updateProfile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            User currentUser = (User) request.getSession().getAttribute("user");
            User updatedUser = new User();

            updatedUser.setUserId(currentUser.getUserId());
            updatedUser.setName(request.getParameter("name"));
            updatedUser.setEmail(currentUser.getEmail());
            updatedUser.setPassword(currentUser.getPassword());           
            updatedUser.setPhoneNumber(request.getParameter("phoneNumber"));
            updatedUser.setGender(request.getParameter("gender"));
            updatedUser.setAddress(request.getParameter("address"));
            updatedUser.setLicenseNumber(request.getParameter("licenseNumber"));

            // Handle file upload
            Part filePart = request.getPart("profileImage");
            if (filePart != null && filePart.getSize() > 0) {
                // Delete old file if exists
                if (currentUser.getProfileImage() != null && !currentUser.getProfileImage().isEmpty()) {
                    String oldFilePath = uploadPath + File.separator +
                            currentUser.getProfileImage().substring(currentUser.getProfileImage().lastIndexOf("/") + 1);
                    new File(oldFilePath).delete();
                }

                String fileName = "user_" + currentUser.getUserId() + "_" + System.currentTimeMillis() +
                        filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf("."));
                String filePath = uploadPath + File.separator + fileName;

                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                updatedUser.setProfileImage(request.getContextPath() + "/uploads/" + fileName);
            } else {
                updatedUser.setProfileImage(currentUser.getProfileImage());
            }

         //   
            
            String dobParam = request.getParameter("DOB");
            if (dobParam != null && !dobParam.isEmpty()) {
                java.util.Date dob = java.sql.Date.valueOf(dobParam); // input must be in "yyyy-MM-dd" format
                updatedUser.setDob(dob);
            } else {
                updatedUser.setDob(currentUser.getDob()); // fallback if user didn't update DOB
            }
            
        //    
            
            
            if (userDao.update(updatedUser)) {
                request.getSession().setAttribute("user", updatedUser);
                response.sendRedirect(request.getContextPath() + "/index.jsp");  // ✅ REDIRECT to home
            } else {
                request.setAttribute("error", "Failed to update profile");
                showProfile(request, response); // Optional: still show profile on error
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}