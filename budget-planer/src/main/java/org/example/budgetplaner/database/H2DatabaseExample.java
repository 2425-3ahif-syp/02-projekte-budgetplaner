package org.example.budgetplaner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class H2DatabaseExample {

    public static void main(String[] args) {
        String url = "jdbc:h2:./database/budgetDB";
        String user = "sa";
        String password = "";

        try (Connection conn = getConnection(url, user, password)) {
            createTable(conn);
            insertFakeData(conn);
            System.out.println("Daten erfolgreich eingefügt!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS transactions (
                id IDENTITY PRIMARY KEY,
                datum DATE,
                valuta DATE,
                empfaenger VARCHAR(255),
                verwendungszweck VARCHAR(255),
                betrag DECIMAL(10,2),
                saldo DECIMAL(10,2)
            )
            """;
        conn.createStatement().execute(sql);
    }

    private static void insertFakeData(Connection conn) throws SQLException {
        String sql = """
            INSERT INTO transactions (datum, valuta, empfaenger, verwendungszweck, betrag, saldo)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            Object[][] fakeData = {
                    {"2025-03-01", "2025-03-01", "Supermarkt XY", "Einkauf Lebensmittel", -52.30, 1947.70},
                    {"2025-03-02", "2025-03-02", "Arbeitgeber AG", "Gehaltseingang", 2500.00, 4447.70},
                    {"2025-03-03", "2025-03-03", "Miete März", "Mietzahlung", -850.00, 3597.70},
                    {"2025-03-05", "2025-03-05", "Stromanbieter", "Abschlagszahlung Strom", -90.00, 3507.70},
                    {"2025-03-07", "2025-03-07", "Online-Shop ABC", "Bestellnr. 123456", -120.00, 3387.70},
                    {"2025-03-10", "2025-03-10", "Freund Max", "Rückzahlung Pizzaabend", 25.00, 3412.70},
                    {"2025-03-15", "2025-03-15", "Tankstelle DEF", "Tanken", -65.00, 3347.70},
                    {"2025-03-20", "2025-03-20", "Streaming-Dienst", "Abo März", -12.99, 3334.71},
                    {"2025-03-25", "2025-03-25", "Restaurant GHI", "Abendessen mit Freunden", -45.50, 3289.21},
                    {"2025-03-30", "2025-03-30", "Fitnessstudio", "Monatsbeitrag", -29.99, 3259.22}
            };

            for (Object[] entry : fakeData) {
                stmt.setString(1, (String) entry[0]);
                stmt.setString(2, (String) entry[1]);
                stmt.setString(3, (String) entry[2]);
                stmt.setString(4, (String) entry[3]);
                stmt.setBigDecimal(5, new java.math.BigDecimal(entry[4].toString()));  // betrag
                stmt.setBigDecimal(6, new java.math.BigDecimal(entry[5].toString()));  // saldo
                stmt.executeUpdate();
            }
        }
    }
}

