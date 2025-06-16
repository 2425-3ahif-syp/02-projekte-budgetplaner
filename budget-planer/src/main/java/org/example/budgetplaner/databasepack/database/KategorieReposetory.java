/*
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
        System.out.println("Creating new kategorie " + name);
        String sql = "INSERT INTO CATEGORYS (name) VALUES (?)";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKategorie(int id) {

        System.out.println("Deleting kategorie with id " + id);
        String sql = "DELETE FROM CATEGORYS WHERE id = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<KategorieModel> getCategories() {
        System.out.println("Fetching all categories");
        String sql = "SELECT * FROM CATEGORYS";
        List<KategorieModel> kategorieList = new java.util.ArrayList<>();
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Found category: " + rs.getString("name"));
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
        System.out.println("Fetching category id for name: " + name);
        String sql = "SELECT id FROM CATEGORYS WHERE name = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Category id for name " + name + " is " + rs.getInt("id"));
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 7;
    }
    public String getCategoryNameById(int id) {
        System.out.println("Fetching category name for id: " + id);
        String sql = "SELECT name FROM CATEGORYS WHERE id = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Category name for id " + id + " is " + rs.getString("name"));
                return rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

*/
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
        System.out.println("Creating new kategorie " + name);
        String sql = "INSERT INTO CATEGORYS (name) VALUES (?)";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKategorie(int id) {
        System.out.println("Deleting kategorie with id " + id);
        String sql = "DELETE FROM CATEGORYS WHERE id = ?" +
                     "limit 7;";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<KategorieModel> getCategories() {
        System.out.println("Fetching all categories");
        String sql = "SELECT distinct * FROM CATEGORYS" +
                     " limit 7;";

        List<KategorieModel> kategorieList = new java.util.ArrayList<>();
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Found category: " + rs.getString("name"));
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
        System.out.println("Fetching category id for name: " + name);
        String sql = "SELECT id FROM CATEGORYS WHERE name = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Category id for name " + name + " is " + rs.getInt("id"));
                    return rs.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getCategoryNameById(int id) {
        System.out.println("Fetching category name for id: " + id);
        String sql = "SELECT name FROM CATEGORYS WHERE id = ?";
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Category name for id " + id + " is " + rs.getString("name"));
                    return rs.getString("name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
