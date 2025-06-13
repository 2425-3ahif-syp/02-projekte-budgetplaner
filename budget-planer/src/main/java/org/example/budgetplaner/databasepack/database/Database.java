package org.example.budgetplaner.databasepack.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Database {

    private static Database instance;

    private static String url;
    private static String username;
    private static String password;
    private static Connection connection;

    private Database() {
        try {
            loadProperties();
            connection = DriverManager.getConnection(url, username, password);
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadProperties() {
        Properties prop = new Properties();

        // Load properties file
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Error: File 'database.properties' not found.");
                return;
            }

            prop.load(input);
            url = "jdbc:h2:tcp://localhost:%s/./%s".formatted(prop.getProperty("port"), prop.getProperty("database"));
            username = prop.getProperty("username");
            password = prop.getProperty("password");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void initialize() {

        String createTransactionTable = "   CREATE TABLE IF NOT EXISTS transactions (\n" +
                "                id INT AUTO_INCREMENT PRIMARY KEY," +
                "                datum DATE,\n" +
                "                valuta DATE,\n" +
                "                empfaenger VARCHAR(255),\n" +
                "                verwendungszweck VARCHAR(255),\n" +
                "                betrag DECIMAL(10,2),\n" +
                "                saldo DECIMAL(10,2)\n" +
                "            )";

        String createUserTable = "CREATE TABLE IF NOT EXISTS users (\n" +
                "                id PRIMARY AUTIINCREAMENT KEY,\n" +
                "                username VARCHAR(255)  NOT NULL,\n" +
                "                full_name  VARCHAR(255),\n" +
                "                password VARCHAR(255) NOT NULL,\n" +
                "                email VARCHAR(200)" +
                "            )";// Profile picture and Birthday adden

        String createBudgetTable = "CREATE TABLE IF NOT EXISTS budget (\n" +
                "                id INT AUTO_INCREMENT PRIMARY KEY," +
                "                betrag DECIMAL(10,2) NOT NULL,\n" +
                "                monat INT NOT NULL,\n" +
                "                jahr INT NOT NULL\n" +
                "                kategorie_id INTEGER NOT NULL,\n" +
                "                FOREIGN KEY (kategorie_id) REFERENCES Categorys(id)" +
                "            )";

        String createCashFlowTable = "CREATE TABLE IF NOT EXISTS cash_flow (\n" +
                "                id INT AUTO_INCREMENT PRIMARY KEY," +
                "                datum DATE NOT NULL,\n" +
                "                betrag DECIMAL(10,2) NOT NULL,\n" +
                "                kategorie_id INTEGER NOT NULL,\n" +
                "                FOREIGN KEY (kategorie_id) REFERENCES Categorys(id)" +
                "                type VARCHAR(255)\n" +
                "            )";

        String createKategorienTable = "CREATE TABLE IF NOT EXISTS Categorys (\n" +
                "                id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "                name VARCHAR(255) NOT NULL\n" +
                "            )";

        String[] insertCategories = {
                "INSERT INTO Categorys (name) VALUES ('Einnahmen');",
                "INSERT INTO Categorys (name) VALUES ('Haushalt');",
                "INSERT INTO Categorys (name) VALUES ('Freizeit');",
                "INSERT INTO Categorys (name) VALUES ('Abos');",
                "INSERT INTO Categorys (name) VALUES ('Kleidung');",
                "INSERT INTO Categorys (name) VALUES ('Lebensmittel');"
        };


        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTransactionTable);
            stmt.execute(createUserTable);
            stmt.execute(createBudgetTable);
            stmt.execute(createCashFlowTable);
            stmt.execute(createKategorienTable);
            for (String category : insertCategories) {
                stmt.execute(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
