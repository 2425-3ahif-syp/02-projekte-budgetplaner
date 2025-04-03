package org.example.budgetplaner.controller;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.budgetplaner.database.H2DatabaseExample;

import java.sql.*;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class AusgabenController {

    private static Connection connection;

    public AusgabenController() throws SQLException {
        this.connection = H2DatabaseExample.getConnection();
    }

    private static String[] getValuesFromDB(String query) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append(rs.getString(1)).append(",");
            }

            return result.toString().split(",");
        }
    }

    public static VBox createUI(Stage primaryStage) throws SQLException {
        String[] categories = {"Haushalt", "Freizeit", "Abos", "Klamotten", "Lebensmittel", "Überschuss"};
        double[] values = {0, 0, 0, 0, 0, 0};

        String[] verwendungszwecke = getValuesFromDB("SELECT verwendungszweck FROM transactions");
        String[] betraege = getValuesFromDB("SELECT betrag FROM transactions");

        for (int i = 0; i < verwendungszwecke.length; i++) {
            double betrag = Double.parseDouble(betraege[i].trim());

            if (verwendungszwecke[i].toLowerCase().contains("wasserrechnung") ||
                    verwendungszwecke[i].toLowerCase().contains("miete") ||
                    verwendungszwecke[i].toLowerCase().contains("strom") ||
                    verwendungszwecke[i].toLowerCase().contains("heizungsrechnung")) {
                values[0] += betrag;
            }
            if (verwendungszwecke[i].toLowerCase().contains("freunde") ||
                    verwendungszwecke[i].toLowerCase().contains("pizzaabend")) {
                values[1] += betrag;
            }
            if (verwendungszwecke[i].toLowerCase().contains("klamotten") ||
                    verwendungszwecke[i].toLowerCase().contains("bestellnr.")) {
                values[3] += betrag;
            }
            if (verwendungszwecke[i].toLowerCase().contains("monatsbeitrag") ||
                    verwendungszwecke[i].toLowerCase().contains("abo")) {
                values[2] += betrag;
            }
            if (verwendungszwecke[i].toLowerCase().contains("einkauf")) {
                values[4] += betrag;
            }
        }


        PieChart pieChart = new PieChart();

        for (int i = 0; i < categories.length; i++) {

                PieChart.Data slice = new PieChart.Data(categories[i], values[i]);
                pieChart.getData().add(slice);

        }

        return new VBox(10, pieChart);
    }
    public static Scene createAusgabenScene(Stage primaryStage) throws SQLException {
        MenuBar menuBar = createMenuBar(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(createUI(primaryStage));
        return new Scene(root, 800, 600);
    }
}
