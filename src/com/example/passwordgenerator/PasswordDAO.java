package com.example.passwordgenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordDAO {
    public void savePassword(PasswordEntity passwordEntity) {
        String sql = "INSERT INTO Passwords(password, length, uppercase, digits, special) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, passwordEntity.getPassword());
            pstmt.setInt(2, passwordEntity.getLength());
            pstmt.setBoolean(3, passwordEntity.isUppercase());
            pstmt.setBoolean(4, passwordEntity.isDigits());
            pstmt.setBoolean(5, passwordEntity.isSpecial());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PasswordEntity> getAllPasswords() {
        List<PasswordEntity> passwordList = new ArrayList<>();
        String sql = "SELECT * FROM Passwords ORDER BY timestamp DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PasswordEntity password = new PasswordEntity(
                        rs.getString("password"),
                        rs.getInt("length"),
                        rs.getBoolean("uppercase"),
                        rs.getBoolean("digits"),
                        rs.getBoolean("special")
                );
                password.setId(rs.getInt("id"));
                password.setTimestamp(rs.getString("timestamp"));
                passwordList.add(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwordList;
    }
}
