package org.example.budgetplaner.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.budgetplaner.database.H2DatabaseExample;

import java.sql.*;
import java.util.Map;

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
        double[] values = new double[categories.length];

        String[] verwendungszwecke = getValuesFromDB("SELECT verwendungszweck FROM transactions");
        String[] betraege = getValuesFromDB("SELECT betrag FROM transactions");

        int len = Math.min(verwendungszwecke.length, betraege.length);

        Map<String, Integer> keywordToCategoryIndex = Map.ofEntries(
                Map.entry("miet", 0),
                Map.entry("strom", 0),
                Map.entry("heizung", 0),
                Map.entry("wasser", 0),

                Map.entry("freunde", 1),
                Map.entry("pizza", 1),
                Map.entry("abendessen", 1),

                Map.entry("abo", 2),
                Map.entry("monatsbeitrag", 2),

                Map.entry("bestellnr.", 3),
                Map.entry("klamotten", 3),

                Map.entry("einkauf", 4),
                Map.entry("lebensmittel", 4)
        );

        for (int i = 0; i < len; i++) {
            String verwendung = verwendungszwecke[i].toLowerCase();
            double betrag;
            try {
                betrag = Double.parseDouble(betraege[i].trim());
            } catch (NumberFormatException e) {
                continue;
            }


            if (betrag < 0) {
                betrag = -betrag;
            }

            boolean matched = false;
            for (Map.Entry<String, Integer> entry : keywordToCategoryIndex.entrySet()) {
                if (verwendung.contains(entry.getKey())) {
                    values[entry.getValue()] += betrag;
                    matched = true;
                    break;
                }
            }


            if (!matched) {
                values[5] += betrag;
            }
        }

        PieChart pieChart = new PieChart();

        for (int i = 0; i < categories.length; i++) {
            PieChart.Data slice = new PieChart.Data(categories[i], values[i]);
            pieChart.getData().add(slice);
        }

        pieChart.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> {
                    for (int i = 0; i < pieChart.getData().size(); i++) {
                        Node node = pieChart.getData().get(i).getNode();
                        if (node != null) {
                            node.getStyleClass().add("default-color" + i);
                        }
                    }
                });
            }
        });

        return new VBox(10, pieChart);
    }

    public static Scene createAusgabenScene(Stage primaryStage) throws SQLException {

        MenuBar menuBar = createMenuBar(primaryStage);
        VBox ui = createUI(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(ui);    

        Scene scene = new Scene(root, 800, 600);
        return scene;
    }
}
