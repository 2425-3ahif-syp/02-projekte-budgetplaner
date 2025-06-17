package org.example.budgetplaner.databasepack.database;

import org.example.budgetplaner.databasepack.database.Database;
import org.example.budgetplaner.model.UserModel;
import org.h2.engine.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserRepository {

    private final Connection connection;

    public UserRepository() {
        this.connection = Database.getInstance().getConnection();
    }

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
                updateStmt.setDate(2, Date.valueOf(LocalDate.parse(geburtstag, DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                updateStmt.setString(3, email);
                updateStmt.setString(4, passwort);
                updateStmt.setString(5, profilBildPfad);
                updateStmt.setInt(6, userId);
                updateStmt.executeUpdate();
            } else {
                String insertSql = "INSERT INTO users (full_name, birthday, email, password, profile_picture) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setString(1, name);
                insertStmt.setDate(2, Date.valueOf(LocalDate.parse(geburtstag, DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                insertStmt.setString(3, email);
                insertStmt.setString(4, passwort);
                insertStmt.setString(5, profilBildPfad);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserModel findByNameAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE FULL_NAME = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new UserModel(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getDate("birthday").toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("profile_picture")
                    );
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen des Benutzers: " + e.getMessage(), e);
        }
    }
}
