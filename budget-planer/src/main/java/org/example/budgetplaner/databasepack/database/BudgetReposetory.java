/*
package org.example.budgetplaner.databasepack.database;

import org.example.budgetplaner.model.BudgetModel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BudgetReposetory {
    private final Connection connection;

    public BudgetReposetory() {
        this.connection = Database.getInstance().getConnection();
    }

    public void createNewBudgetPlan(Float betrag, int monat, int jahr, int kategorieId) {
        String sql = "INSERT INTO budget (betrag, monat, jahr, kategorie_id) VALUES (?,?,?,?)";
        try (var pstmt = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            pstmt.setDouble(1, betrag); // Initial amount
            pstmt.setInt(2, monat); // Month
            pstmt.setInt(3, jahr); // Year
            pstmt.setInt(4, kategorieId); // Example category ID

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BudgetModel> getBudgetplanByMonth(int monat, int jahr) {
        List<BudgetModel> budgetList = new ArrayList<>();

        String sql = "SELECT * FROM budget WHERE monat = ? AND jahr = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, monat);
            pstmt.setInt(2, jahr);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                Float betrag = rs.getFloat("betrag");
                int kategorieId = rs.getInt("kategorie_id");

                BudgetModel budget = new BudgetModel(id, betrag, monat, jahr, kategorieId);
                budgetList.add(budget);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return budgetList;
    }
    public List<Integer> getLatestYearAndMonth() {
        List<Integer> result = new ArrayList<>();
        String sql = "SELECT jahr, MAX(monat) as max_monat FROM budget WHERE jahr = (SELECT MAX(jahr) FROM budget) GROUP BY jahr";
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int year = rs.getInt("jahr");
                int month = rs.getInt("max_monat");
                result.add(year);
                result.add(month);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<BudgetModel> getBudgetModelsByMonthAndYear(int month, int year) {
        List<BudgetModel> budgetList = new ArrayList<>();
        String sql = "SELECT * FROM budget WHERE monat = ? AND jahr = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                Float betrag = rs.getFloat("betrag");
                int monat = rs.getInt("monat");
                int jahr = rs.getInt("jahr");
                int kategorieId = rs.getInt("kategorie_id");
                budgetList.add(new BudgetModel(id, betrag, monat, jahr, kategorieId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return budgetList;
    }
}*/

package org.example.budgetplaner.databasepack.database;

import org.example.budgetplaner.model.BudgetModel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BudgetReposetory {
    private final Connection connection;

    public BudgetReposetory() {
        this.connection = Database.getInstance().getConnection();
    }

    public void createNewBudgetPlan(Float betrag, int monat, int jahr, int kategorieId) {

        System.out.println("Creating new budget plan");
        System.out.println("Betrag: " + betrag);
        System.out.println("Monat: " + monat);
        System.out.println("Jahr: " + jahr);
        System.out.println("Kategorie ID: " + kategorieId);

        String sql = "INSERT INTO budget (AMOUNT, MONTH_NUM, YEAR_NUM, CATEGORY_ID) VALUES (?,?,?,?)";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, betrag);
            pstmt.setInt(2, monat);
            pstmt.setInt(3, jahr);
            pstmt.setInt(4, kategorieId);
            pstmt.executeUpdate();
            System.out.println("New budget plan created");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<BudgetModel> getBudgetplanByMonth(int monat, int jahr) {
        List<BudgetModel> budgetList = new ArrayList<>();
        String sql = "SELECT * FROM budget WHERE MONTH_NUM = ? AND YEAR_NUM = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, monat);
            pstmt.setInt(2, jahr);
            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    Float betrag = rs.getFloat("betrag");
                    int kategorieId = rs.getInt("kategorie_id");
                    BudgetModel budget = new BudgetModel(id, betrag, monat, jahr, kategorieId);
                    budgetList.add(budget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return budgetList;
    }

    public List<Integer> getLatestYearAndMonth() {
        List<Integer> result = new ArrayList<>();
        String sql = "SELECT YEAR_NUM, MAX(MONTH_NUM) as max_monat FROM budget WHERE YEAR_NUM = (SELECT MAX(YEAR_NUM) FROM budget) GROUP BY YEAR_NUM";
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int year = rs.getInt("year_num");
                int month = rs.getInt("max_monat");
                result.add(year);
                result.add(month);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<BudgetModel> getBudgetModelsByMonthAndYear(int month, int year) {
        List<BudgetModel> budgetList = new ArrayList<>();
        String sql = "SELECT * FROM budget WHERE MONTH_NUM = ? AND YEAR_NUM = ? ";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    Float betrag = rs.getFloat("amount");
                    int monat = rs.getInt("month_num");
                    int jahr = rs.getInt("year_num");
                    int kategorieId = rs.getInt("category_id");
                    budgetList.add(new BudgetModel(id, betrag, monat, jahr, kategorieId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return budgetList;
    }
}