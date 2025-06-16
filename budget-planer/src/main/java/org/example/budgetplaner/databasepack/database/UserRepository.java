package org.example.budgetplaner.databasepack.database;

import org.example.budgetplaner.databasepack.database.Database;

import java.sql.*;
import java.time.LocalDate;

public class UserRepository {

    private final Connection connection = Database.getConnection();


    public void saveOrUpdateUser(String name, String geburtstag, String email, String passwort, String profilBildPfad) {
        try {
            String checkSql = "SELECT id FROM users LIMIT 1";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String updateSql = "UPDATE users SET full_name = ?, birthday = ?, email = ?, password = ?, profile_picture = ? WHERE id = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setString(1, name);
                updateStmt.setDate(2, Date.valueOf(LocalDate.parse(geburtstag, java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                updateStmt.setString(3, email);
                updateStmt.setString(4, passwort);
                updateStmt.setString(5, profilBildPfad);
                updateStmt.setInt(6, userId);
                updateStmt.executeUpdate();
                connection.setAutoCommit(true);
            } else {
                String insertSql = "INSERT INTO users (full_name, birthday, email, password, profile_picture) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setString(1, name);
                insertStmt.setDate(2, Date.valueOf(LocalDate.parse(geburtstag, java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                insertStmt.setString(3, email);
                insertStmt.setString(4, passwort);
                insertStmt.setString(5, profilBildPfad);
                insertStmt.executeUpdate();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
