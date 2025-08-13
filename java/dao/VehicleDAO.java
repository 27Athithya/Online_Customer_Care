package dao;


import model.Vehicle;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO implements BaseDAO<Vehicle> {

    @Override
    public boolean insert(Vehicle v) throws SQLException {
        String sql = "INSERT INTO vehicles (vehicle_name, manufacture_year, person_capacity, fuel_type, liter_per_km, transmission_type, rent_per_day, vehicle_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, v.getVehicleName());
            stmt.setInt(2, v.getManufactureYear());
            stmt.setInt(3, v.getPersonCapacity());
            stmt.setString(4, v.getFuelType());
            stmt.setFloat(5, v.getLiterPerKm());
            stmt.setString(6, v.getTransmissionType());
            stmt.setDouble(7, v.getRentPerDay());
            stmt.setString(8, v.getVehicleImage());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Vehicle v) throws SQLException {
        String sql = "UPDATE vehicles SET vehicle_name=?, manufacture_year=?, person_capacity=?, fuel_type=?, liter_per_km=?, transmission_type=?, rent_per_day=?, vehicle_image=? WHERE vehicle_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, v.getVehicleName());
            stmt.setInt(2, v.getManufactureYear());
            stmt.setInt(3, v.getPersonCapacity());
            stmt.setString(4, v.getFuelType());
            stmt.setFloat(5, v.getLiterPerKm());
            stmt.setString(6, v.getTransmissionType());
            stmt.setDouble(7, v.getRentPerDay());
            stmt.setString(8, v.getVehicleImage());
            stmt.setInt(9, v.getVehicleId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM vehicles WHERE vehicle_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Vehicle getById(int id) throws SQLException {
        String sql = "SELECT * FROM vehicles WHERE vehicle_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return extractVehicle(rs);
        }
        return null;
    }

    @Override
    public List<Vehicle> getAll() throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractVehicle(rs));
            }
        }
        return list;
    }

    private Vehicle extractVehicle(ResultSet rs) throws SQLException {
        Vehicle v = new Vehicle();
        v.setVehicleId(rs.getInt("vehicle_id"));
        v.setVehicleName(rs.getString("vehicle_name"));
        v.setManufactureYear(rs.getInt("manufacture_year"));
        v.setPersonCapacity(rs.getInt("person_capacity"));
        v.setFuelType(rs.getString("fuel_type"));
        v.setLiterPerKm(rs.getFloat("liter_per_km"));
        v.setTransmissionType(rs.getString("transmission_type"));
        v.setRentPerDay(rs.getDouble("rent_per_day"));
        v.setVehicleImage(rs.getString("vehicle_image"));
        return v;
    }
}
