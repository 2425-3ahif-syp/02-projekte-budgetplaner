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
}