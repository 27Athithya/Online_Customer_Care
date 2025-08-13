package dao;

import model.User;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements BaseDAO<User> {

    @Override
    public boolean insert(User user) throws SQLException {
        String sql = "INSERT INTO users (name, dob, email, phone_number, gender, license_number, address, profile_image, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setDate(2, new java.sql.Date(user.getDob().getTime()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getLicenseNumber());
            stmt.setString(7, user.getAddress());
            stmt.setString(8, user.getProfileImage());
            stmt.setString(9, user.getPassword());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET name=?, dob=?, email=?, phone_number=?, gender=?, license_number=?, address=?, profile_image=?, password=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setDate(2, new java.sql.Date(user.getDob().getTime()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getLicenseNumber());
            stmt.setString(7, user.getAddress());
            stmt.setString(8, user.getProfileImage());
            stmt.setString(9, user.getPassword());
            stmt.setInt(10, user.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
            return null;
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractUserFromResultSet(rs));
            }
        }
        return list;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setDob(rs.getDate("dob"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setGender(rs.getString("gender"));
        user.setLicenseNumber(rs.getString("license_number"));
        user.setAddress(rs.getString("address"));
        user.setProfileImage(rs.getString("profile_image"));
        user.setPassword(rs.getString("password"));
        return user;
    }
    
    //
    
  /*  public User getByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? extractUserFromResultSet(rs) : null;
        }
    }
    
    */
    
    public User getByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? extractUserFromResultSet(rs) : null;
        }
    }

    //
    
    

	
    
    //
    
    
//    public byte[] getUserImageById(int userId) throws SQLException {
//        byte[] imageBytes = null;
//        String sql = "SELECT profile_image FROM users WHERE user_id=?";
//        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                imageBytes = rs.getBytes("profile_image");  // Assuming profile_image is a BLOB or byte[]
//            }
//        }
//        return imageBytes;
//    }

    
    
    
    
    //
}
