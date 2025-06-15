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

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Error: File 'database.properties' not found.");
                return;
            }

            prop.load(input);
            url = prop.getProperty("url");
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

    public static Connection getConnection() {
        return connection;
    }

    private void initialize() {
        String createCategoryTable = "CREATE TABLE IF NOT EXISTS Categorys (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL" +
                ");";

        String createTransactionTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id IDENTITY PRIMARY KEY," +
                "date DATE NOT NULL," +
                "amount REAL NOT NULL," +
                "category_id INTEGER NOT NULL," +
                "is_income BOOLEAN," +
                "FOREIGN KEY (category_id) REFERENCES Categorys(id)" +
                ");";

        String createUserTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL," +
                "full_name VARCHAR(255)," +
                "password VARCHAR(255) NOT NULL," +
                "email VARCHAR(200)," +
                "profile_picture VARCHAR(255)," +
                "birthday DATE" +
                ");";

        String createBudgetTable = "CREATE TABLE IF NOT EXISTS budget (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "betrag DECIMAL(10,2) NOT NULL," +
                "monat INT NOT NULL," +
                "jahr INT NOT NULL," +
                "category_id INTEGER NOT NULL," +
                "FOREIGN KEY (category_id) REFERENCES Categorys(id)" +
                ");";

        String createCashFlowTable = "CREATE TABLE IF NOT EXISTS cash_flow (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "datum DATE NOT NULL," +
                "betrag DECIMAL(10,2) NOT NULL," +
                "category_id INTEGER NOT NULL," +
                "type VARCHAR(255)," +
                "FOREIGN KEY (category_id) REFERENCES Categorys(id)" +
                ");";

        String[] insertCategories = {
                "INSERT INTO Categorys (name) VALUES ('Einnahmen');",
                "INSERT INTO Categorys (name) VALUES ('Haushalt');",
                "INSERT INTO Categorys (name) VALUES ('Freizeit');",
                "INSERT INTO Categorys (name) VALUES ('Abos');",
                "INSERT INTO Categorys (name) VALUES ('Kleidung');",
                "INSERT INTO Categorys (name) VALUES ('Lebensmittel');",
                "INSERT INTO Categorys (name) VALUES ('Sonstiges');"
        };

        String[] insertTransactions = {
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-06-01', 2500.00, 1, true);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-06-02', 300.00, 2, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-06-03', 120.00, 3, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-06-04', 15.99, 4, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-06-05', 80.00, 5, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-06-06', 200.00, 6, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-06-07', 50.00, 7, false);",

                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-05-01', 2500.00, 1, true);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-05-02', 300.00, 2, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-05-03', 120.00, 3, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-05-04', 15.99, 4, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-05-05', 80.00, 5, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-05-06', 200.00, 6, false);",
                "INSERT INTO transactions (date, amount, category_id, is_income) VALUES ('2025-05-07', 50.00, 7, false);"
        };

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCategoryTable);
            stmt.execute(createTransactionTable);
            stmt.execute(createUserTable);
            stmt.execute(createBudgetTable);
            stmt.execute(createCashFlowTable);

            for (String category : insertCategories) {
                stmt.execute(category);
            }
            for (String transaction : insertTransactions) {
                stmt.execute(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}