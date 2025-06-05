package org.example.budgetplaner.database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:h2:./data/budget;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // Tabelle anlegen, wenn nicht vorhanden
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS eintraege (
                    id IDENTITY PRIMARY KEY,
                    kategorie VARCHAR(255) NOT NULL,
                    betrag DOUBLE NOT NULL,
                    monat VARCHAR(20) NOT NULL,
                    jahr INT NOT NULL
                );
            """);

            // Nur Beispieldaten einfügen, wenn Tabelle leer
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM eintraege");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate("""
                    INSERT INTO eintraege (kategorie, betrag, monat, jahr) VALUES
                    ('Haushalt', 900, 'Jänner', 2025),
                    ('Haushalt', 1000, 'Februar', 2025),
                    ('Freizeit', 300, 'Jänner', 2025),
                    ('Freizeit', 200, 'Februar', 2025),
                    ('Abos', 80, 'Jänner', 2025),
                    ('Abos', 60, 'Februar', 2025),
                    ('Klamotten', 250, 'Jänner', 2025),
                    ('Klamotten', 300, 'Februar', 2025),
                    ('Lebensmittel', 200, 'Jänner', 2025),
                    ('Lebensmittel', 250, 'Februar', 2025),
                    ('Überschuss', 950, 'Jänner', 2025),
                    ('Überschuss', 1400, 'Februar', 2025),
                    ('Einnahmen', 3000, 'Jänner', 2025),
                    ('Einnahmen', 3200, 'Februar', 2025);
                """);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    /**
     * Gibt für jede Kategorie (Haushalt, Freizeit usw.) eine Map von Monat → Betrag zurück.
     */
    public static Map<String, Map<String, Double>> getMonatsdaten(int jahr) {
        Map<String, Map<String, Double>> daten = new HashMap<>();

        String sql = """
            SELECT monat, kategorie, SUM(betrag) AS summe
            FROM eintraege
            WHERE jahr = ?
            GROUP BY monat, kategorie
        """;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jahr);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String monat = rs.getString("monat");
                String kategorie = rs.getString("kategorie");
                double betrag = rs.getDouble("summe");

                daten.computeIfAbsent(kategorie, k -> new HashMap<>())
                        .put(monat, betrag);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daten;
    }
}
