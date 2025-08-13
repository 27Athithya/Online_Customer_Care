package controller;

import dao.VehicleDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Vehicle;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    private VehicleDAO vehicleDAO;
    
    @Override
    public void init() {
        vehicleDAO = new VehicleDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Vehicle> featuredVehicles = vehicleDAO.getAll();
            request.setAttribute("featuredVehicles", featuredVehicles);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving vehicles", e);
        }
    }
    
    
}
