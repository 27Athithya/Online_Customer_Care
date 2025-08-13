package dao;


import model.VehicleReservation;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleReservationDAO implements BaseDAO<VehicleReservation> {

    @Override
    public boolean insert(VehicleReservation vr) throws SQLException {
        String sql = "INSERT INTO vehicle_reservations (vehicle_id, user_id, pickup_date, drop_date, total_rent) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vr.getVehicleId());
            stmt.setInt(2, vr.getUserId());
            stmt.setDate(3, new java.sql.Date(vr.getPickupDate().getTime()));
            stmt.setDate(4, new java.sql.Date(vr.getDropDate().getTime()));
            stmt.setDouble(5, vr.getTotalRent());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(VehicleReservation vr) throws SQLException {
        String sql = "UPDATE vehicle_reservations SET vehicle_id=?, user_id=?, pickup_date=?, drop_date=?, total_rent=? WHERE reservation_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vr.getVehicleId());
            stmt.setInt(2, vr.getUserId());
            stmt.setDate(3, new java.sql.Date(vr.getPickupDate().getTime()));
            stmt.setDate(4, new java.sql.Date(vr.getDropDate().getTime()));
            stmt.setDouble(5, vr.getTotalRent());
            stmt.setInt(6, vr.getReservationId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM vehicle_reservations WHERE reservation_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public VehicleReservation getById(int id) throws SQLException {
        String sql = "SELECT * FROM vehicle_reservations WHERE reservation_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return extractReservation(rs);
        }
        return null;
    }

    @Override
    public List<VehicleReservation> getAll() throws SQLException {
        List<VehicleReservation> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicle_reservations";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractReservation(rs));
            }
        }
        return list;
    }

    private VehicleReservation extractReservation(ResultSet rs) throws SQLException {
        VehicleReservation vr = new VehicleReservation();
        vr.setReservationId(rs.getInt("reservation_id"));
        vr.setVehicleId(rs.getInt("vehicle_id"));
        vr.setUserId(rs.getInt("user_id"));
        vr.setPickupDate(rs.getDate("pickup_date"));
        vr.setDropDate(rs.getDate("drop_date"));
        vr.setDurationDays(rs.getInt("duration_days"));
        vr.setTotalRent(rs.getDouble("total_rent"));
        return vr;
    }
}
