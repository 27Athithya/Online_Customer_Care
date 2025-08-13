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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/auth/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
                 maxFileSize = 1024 * 1024 * 5, 
                 maxRequestSize = 1024 * 1024 * 5 * 5)
public class AuthController extends HttpServlet {
    private UserDAO userDao;
    private String uploadPath;
    private SimpleDateFormat dateFormatter;

    @Override
    public void init() throws ServletException {
        userDao = new UserDAO();
        uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        switch (action) {
            case "/login":
                showLogin(request, response);
                break;
            case "/register":
                showRegister(request, response);
                break;
            case "/logout":
                handleLogout(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        switch (action) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/register":
                handleRegister(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    
	private void showLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	request.getRequestDispatcher("/login.jsp").forward(request, response);

    }

    private void showRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Admin login (hardcoded)
            if ("admin@gmail.com".equals(email) && "admin123".equals(password)) {
                HttpSession session = request.getSession();

                // Create dummy admin user
                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setName("Admin");
                session.setAttribute("user", admin);

                response.sendRedirect(request.getContextPath() + "/adminDashboard.jsp");
                return;
            }

            // Regular user login
            User user = userDao.getByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                request.setAttribute("error", "Invalid email or password");
                showLogin(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            User user = new User();
            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setPhoneNumber(request.getParameter("phoneNumber"));
            user.setGender(request.getParameter("gender"));
            user.setAddress(request.getParameter("address"));
            user.setLicenseNumber(request.getParameter("licenseNumber")); // Add this line

            // Handle date of birth
            String dobString = request.getParameter("dob");
            if (dobString != null && !dobString.isEmpty()) {
                try {
                    Date dob = dateFormatter.parse(dobString);
                    user.setDob(dob);
                } catch (ParseException e) {
                    request.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
                    showRegister(request, response);
                    return;
                }
            } else {
                request.setAttribute("error", "Date of birth is required.");
                showRegister(request, response);
                return;
            }
            
            // Handle file upload
            Part filePart = request.getPart("profileImage");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = "user_" + System.currentTimeMillis() + 
                                 filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf("."));
                String filePath = uploadPath + File.separator + fileName;
                
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                
                user.setProfileImage(request.getContextPath() + "/uploads/" + fileName);
            }
            
            if (userDao.insert(user)) {
                request.setAttribute("success", "Registration successful. Please login.");
                response.sendRedirect(request.getContextPath() + "/auth/login");
    
                //           showLogin(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Email may already exist.");
                showRegister(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}