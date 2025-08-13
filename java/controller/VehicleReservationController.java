package controller;

import dao.VehicleDAO;
import dao.VehicleReservationDAO;
import model.Vehicle;
import model.VehicleReservation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/VehicleReservationController")
public class VehicleReservationController extends HttpServlet {

    private VehicleReservationDAO reservationDAO = new VehicleReservationDAO();
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Vehicle> vehicles = vehicleDAO.getAll();
            List<VehicleReservation> reservations = reservationDAO.getAll();

            request.setAttribute("vehicles", vehicles);
            request.setAttribute("reservations", reservations);

            String vehicleId = request.getParameter("vehicleId");
            if (vehicleId != null) {
                request.setAttribute("selectedVehicleId", vehicleId);
            }

            request.getRequestDispatcher("/vehicleReservation.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            Date pickupDate = sdf.parse(request.getParameter("pickupDate"));
            Date dropDate = sdf.parse(request.getParameter("dropDate"));

            if (dropDate.before(pickupDate)) {
                request.setAttribute("error", "Drop date must be after pickup date.");
                doGet(request, response);
                return;
            }

            Vehicle vehicle = vehicleDAO.getById(vehicleId);
            if (vehicle == null) {
                request.setAttribute("error", "Selected vehicle not found.");
                doGet(request, response);
                return;
            }

            long diffMillis = dropDate.getTime() - pickupDate.getTime();
            int durationDays = (int) (diffMillis / (1000 * 60 * 60 * 24));
            if (durationDays < 1) durationDays = 1;

            double totalRent = durationDays * vehicle.getRentPerDay();

            VehicleReservation vr = new VehicleReservation();
            vr.setVehicleId(vehicleId);
            vr.setUserId(userId);
            vr.setPickupDate(pickupDate);
            vr.setDropDate(dropDate);
            vr.setTotalRent(totalRent);

            boolean success = reservationDAO.insert(vr);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/VehicleReservationController");
            } else {
                request.setAttribute("error", "Failed to save reservation.");
                doGet(request, response);
            }

        } catch (NumberFormatException | ParseException | SQLException e) {
            throw new ServletException("Error processing reservation", e);
        }
    }
}
