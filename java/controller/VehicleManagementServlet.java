package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.*;
import dao.VehicleDAO;
import model.Vehicle;

@WebServlet("/vehicles")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 1024 * 1024 * 5,       // 5MB
    maxRequestSize = 1024 * 1024 * 5 * 5  // 25MB
)
public class VehicleManagementServlet extends HttpServlet {
    private VehicleDAO vehicleDAO;

    @Override
    public void init() {
        vehicleDAO = new VehicleDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                listVehicles(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "search":
                        searchVehicle(request, response);
                        break;
                    default:
                        listVehicles(request, response);
                }
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                listVehicles(request, response);
            } else {
                switch (action) {
                    case "insert":
                        insertVehicle(request, response);
                        break;
                    case "update":
                        updateVehicle(request, response);
                        break;
                    case "delete":
                        deleteVehicle(request, response);
                        break;
                    default:
                        listVehicles(request, response);
                }
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String message) 
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    private void listVehicles(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        List<Vehicle> vehicles = vehicleDAO.getAll();
        request.setAttribute("vehicles", vehicles);
        request.getRequestDispatcher("/vehicleManagement.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/vehicleForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        
        try {
            // Validate and parse ID
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new ServletException("Vehicle ID is required");
            }
            
            int id = parseVehicleId(idParam);
            Vehicle vehicle = getVehicleById(id);
            
            if (vehicle == null) {
                throw new ServletException("Vehicle not found with ID: " + id);
            }
            
            request.setAttribute("vehicle", vehicle);
            request.getRequestDispatcher("/vehicleForm.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            handleError(request, response, "Invalid Vehicle ID format. Must be a number.");
        } catch (ServletException e) {
            handleError(request, response, e.getMessage());
        }
    }

    private void insertVehicle(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        Vehicle vehicle = extractVehicleFromRequest(request);
        boolean success = vehicleDAO.insert(vehicle);
        
        if (!success) {
            throw new ServletException("Failed to insert vehicle");
        }
        
        response.sendRedirect("vehicles");
    }

    private void updateVehicle(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        String idParam = request.getParameter("id");
        
        try {
            // Validate and parse ID
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new ServletException("Vehicle ID is required for update");
            }
            
            int id = parseVehicleId(idParam);
            Vehicle vehicle = extractVehicleFromRequest(request);
            vehicle.setVehicleId(id);
            
            boolean success = vehicleDAO.update(vehicle);
            if (!success) {
                throw new ServletException("Failed to update vehicle");
            }
            
            response.sendRedirect("vehicles");
            
        } catch (NumberFormatException e) {
            handleError(request, response, "Invalid Vehicle ID format. Must be a number.");
        } catch (ServletException e) {
            handleError(request, response, e.getMessage());
        }
    }

    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        String idParam = request.getParameter("id");
        
        try {
            // Validate and parse ID
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new ServletException("Vehicle ID is required for deletion");
            }
            
            int id = parseVehicleId(idParam);
            boolean success = vehicleDAO.delete(id);
            
            if (!success) {
                throw new ServletException("Failed to delete vehicle");
            }
            
            response.sendRedirect("vehicles");
            
        } catch (NumberFormatException e) {
            handleError(request, response, "Invalid Vehicle ID format. Must be a number.");
        } catch (ServletException e) {
            handleError(request, response, e.getMessage());
        }
    }

    private void searchVehicle(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        String searchId = request.getParameter("searchId");
        
        try {
            if (searchId == null || searchId.trim().isEmpty()) {
                listVehicles(request, response);
                return;
            }
            
            int id = parseVehicleId(searchId);
            Vehicle vehicle = getVehicleById(id);
            List<Vehicle> vehicles = new ArrayList<>();
            
            if (vehicle != null) {
                vehicles.add(vehicle);
                request.setAttribute("vehicles", vehicles);
            } else {
                request.setAttribute("message", "No vehicle found with ID: " + id);
            }
            
            request.getRequestDispatcher("/vehicleManagement.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid search ID format");
            listVehicles(request, response);
        }
    }

    // Helper method to safely parse vehicle ID
    private int parseVehicleId(String idParam) throws NumberFormatException {
        try {
            return Integer.parseInt(idParam.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid Vehicle ID format: " + idParam);
        }
    }

    // Helper method to get vehicle by ID with null check
    private Vehicle getVehicleById(int id) throws ServletException {
        try {
            Vehicle vehicle = vehicleDAO.getById(id);
            if (vehicle == null) {
                throw new ServletException("Vehicle not found with ID: " + id);
            }
            return vehicle;
        } catch (SQLException e) {
            throw new ServletException("Database error while fetching vehicle", e);
        }
    }

    private Vehicle extractVehicleFromRequest(HttpServletRequest request) 
            throws IOException, ServletException {
        Vehicle vehicle = new Vehicle();
        
        // Validate and set vehicle name
        String vehicleName = request.getParameter("vehicleName");
        if (vehicleName == null || vehicleName.trim().isEmpty()) {
            throw new ServletException("Vehicle name is required");
        }
        vehicle.setVehicleName(vehicleName);

        // Validate and set numeric fields
        try {
            vehicle.setManufactureYear(Integer.parseInt(request.getParameter("manufactureYear")));
            vehicle.setPersonCapacity(Integer.parseInt(request.getParameter("personCapacity")));
            vehicle.setLiterPerKm(Float.parseFloat(request.getParameter("literPerKm")));
            vehicle.setRentPerDay(Double.parseDouble(request.getParameter("rentPerDay")));
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format in form data", e);
        }

        // Set other fields
        String fuelType = request.getParameter("fuelType");
        if (fuelType == null || (!fuelType.equals("Gasoline") && !fuelType.equals("Hybrid"))) {
            throw new ServletException("Invalid fuel type");
        }
        vehicle.setFuelType(fuelType);

        String transmissionType = request.getParameter("transmissionType");
        if (transmissionType == null || (!transmissionType.equals("Auto") && !transmissionType.equals("Manual"))) {
            throw new ServletException("Invalid transmission type");
        }
        vehicle.setTransmissionType(transmissionType);

        // Handle file upload
        Part filePart = request.getPart("vehicleImage");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            
            String filePath = uploadPath + File.separator + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            }
            
            vehicle.setVehicleImage("uploads/" + fileName);
        } else if (request.getParameter("existingImage") != null) {
            vehicle.setVehicleImage(request.getParameter("existingImage"));
        }
        
        return vehicle;
    }
}