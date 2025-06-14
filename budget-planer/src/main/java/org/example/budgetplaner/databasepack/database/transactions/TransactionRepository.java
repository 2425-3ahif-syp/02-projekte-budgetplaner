package org.example.budgetplaner.databasepack.database.transactions;

import org.example.budgetplaner.databasepack.database.Database;
import org.example.budgetplaner.databasepack.database.transactions.Transactions;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private final Connection connection;

    /*
    public TransactionRepository(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }*/

    public TransactionRepository() {
        this.connection = Database.getInstance().getConnection();
    }

    /*
        private void createTableIfNotExists() {
            String sql = """
                        CREATE TABLE IF NOT EXISTS transactions (
                            id IDENTITY PRIMARY KEY,
                            date DATE NOT NULL,
                            amount DOUBLE NOT NULL,
                            categorie_id INTEGER NOT NULL,
                            type VARCHAR(10) -- "income" or "expense"
                        );
                    """;

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                throw new RuntimeException("Fehler beim Erstellen der Tabelle: " + e.getMessage(), e);
            }
        }
    */
    public void saveTransaction(LocalDate date, double amount, String category, boolean isIncome) {
        String sql = "INSERT INTO transactions (date, amount, category, type) VALUES (?, ?, ?, ?)";

        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setDouble(2, amount);
            pstmt.setString(3, category);
            pstmt.setString(4, isIncome ? "income" : "expense");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern der Transaktion: " + e.getMessage(), e);
        }
    }

    public List<Transactions> findAll() {
        List<Transactions> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transactions t = new Transactions(
                        rs.getLong("id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("amount"),
                        rs.getInt("category_id"),
                        rs.getString("type")
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen der Transaktionen: " + e.getMessage(), e);
        }

        return transactions;
    }

    public void deleteTransactionById(long id) {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen der Transaktion: " + e.getMessage(), e);
        }
    }

    public void deleteAll() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM transactions");
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen aller Transaktionen: " + e.getMessage(), e);
        }
    }

    public List<Transactions> findByMonth(int month, int year) {
        List<Transactions> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE MONTH(date) = ? AND YEAR(date) = ? ORDER BY date DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transactions t = new Transactions(
                            rs.getLong("id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getDouble("amount"),
                            rs.getInt("categorie_id"),
                            rs.getString("type")
                    );
                    transactions.add(t);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen der Transaktionen: " + e.getMessage(), e);
        }

        return transactions;
    }


    public boolean existsByMonthAndYear(int month, int year) {
        String sql = "SELECT 1 FROM transactions WHERE EXTRACT(MONTH FROM date) = ? AND EXTRACT(YEAR FROM date) = ? LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Überprüfen des Monats: " + e.getMessage(), e);
        }
    }
}
