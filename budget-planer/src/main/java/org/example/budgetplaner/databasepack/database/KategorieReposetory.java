package org.example.budgetplaner.databasepack.database;

import org.example.budgetplaner.model.KategorieModel;

import java.sql.Connection;
import java.util.List;

public class KategorieReposetory {
    private final Connection connection;

    public KategorieReposetory() {
        this.connection = Database.getInstance().getConnection();
    }

    public void createNewKategorie(String name) {
        String sql = "INSERT INTO kategorie (name) VALUES (?)";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKategorie(int id) {
        String sql = "DELETE FROM kategorie WHERE id = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<KategorieModel> getCategories() {
        String sql = "SELECT * FROM kategorie";
        List<KategorieModel> kategorieList = new java.util.ArrayList<>();
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                kategorieList.add(new KategorieModel(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kategorieList;
    }

    public int getCategoryIdByName(String name) {
        String sql = "SELECT id FROM kategorie WHERE name = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 7;
    }
    public String getCategoryNameById(int id) {
        String sql = "SELECT name FROM kategorie WHERE id = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
