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
import java.util.Objects;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class AusgabenController {

    private static Connection connection;

    public AusgabenController()  {
        try {
            connection = H2DatabaseExample.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] getValuesFromDB(String query)  {
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                String value = rs.getString(1);
                System.out.println("Gefundener Wert aus DB: " + value);  // Debugging-Ausgabe
                result.append(value).append(",");
            }

            return result.toString().split(",");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static VBox createUI(Stage primaryStage) throws SQLException {
        String[] categories = {"Haushalt", "Freizeit", "Abos", "Klamotten", "Lebensmittel", "Überschuss"};
        double[] values = new double[categories.length];

        String[] verwendungszwecke = getValuesFromDB("SELECT verwendungszweck FROM transactions");
        String[] betraege = getValuesFromDB("SELECT betrag FROM transactions");


        System.out.println("Länge der verwendungszwecke: " + verwendungszwecke.length);
        System.out.println("Länge der betraege: " + betraege.length);

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


        System.out.println("Finale Werte für das PieChart:");
        for (double value : values) {
            System.out.println(value);
        }


        PieChart pieChart = new PieChart();
        for (int i = 0; i < categories.length; i++) {
            PieChart.Data slice = new PieChart.Data(categories[i], values[i]);
            pieChart.getData().add(slice);
            System.out.println("Hinzufügen der Kategorie: " + categories[i] + " mit Wert: " + values[i]);
        }


        connection.close();

        return new VBox(10, pieChart);
    }

    public static Scene createAusgabenScene(Stage primaryStage) throws SQLException {
        // MenuBar erstellen
        MenuBar menuBar = createMenuBar(primaryStage);
        System.out.println("Menü wird erstellt");


        menuBar.getMenus().stream()
                .filter(menu -> menu.getText().equals("Ausgaben"))
                .findFirst().ifPresent(menu -> {
                    menu.getStyleClass().add("highlighted");
                    System.out.println("CSS-Klasse 'highlighted' zu Ausgaben-Menü hinzugefügt");
                });


        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(createUI(primaryStage));

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(Objects.requireNonNull(AusgabenController.class.getResource("/css/style.css")).toExternalForm());


        return scene;
    }

}
