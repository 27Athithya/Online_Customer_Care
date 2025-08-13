package dao;


import model.Notification;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO implements BaseDAO<Notification> {

    @Override
    public boolean insert(Notification n) throws SQLException {
        String sql = "INSERT INTO notifications (subject, message) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, n.getSubject());
            stmt.setString(2, n.getMessage());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Notification n) throws SQLException {
        String sql = "UPDATE notifications SET subject=?, message=? WHERE notification_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, n.getSubject());
            stmt.setString(2, n.getMessage());
            stmt.setInt(3, n.getNotificationId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM notifications WHERE notification_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Notification getById(int id) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE notification_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return extractNotification(rs);
        }
        return null;
    }

    @Override
    public List<Notification> getAll() throws SQLException {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractNotification(rs));
            }
        }
        return list;
    }

    private Notification extractNotification(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setNotificationId(rs.getInt("notification_id"));
        n.setSubject(rs.getString("subject"));
        n.setMessage(rs.getString("message"));
        n.setCreatedAt(rs.getTimestamp("created_at"));
        return n;
    }
}
