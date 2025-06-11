package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.budgetplaner.databasepack.database.DatabaseManager;
import org.example.budgetplaner.util.Toast;
import java.util.*;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class MonatsvergleichController {

    public static VBox createUI(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Monate");

        NumberAxis yAxis = new NumberAxis(0, 4000, 500);
        yAxis.setLabel("Betrag (€)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Monatsvergleich");
        barChart.setLegendVisible(false);
        barChart.setAnimated(true);

        Map<String, Map<String, Double>> daten = DatabaseManager.getMonatsdaten(2025);

        Map<String, String> farben = Map.of(
                "Haushalt",    "#40b4ff",
                "Freizeit",    "#8e5dbf",
                "Abos",        "#66c2ff",
                "Klamotten",   "#a066a0",
                "Lebensmittel","#3a7cb8",
                "Überschuss",  "#63457f",
                "Einnahmen",   "#6bdcff"
        );

        List<HBox> legendenEintraege = new ArrayList<>();

        for (String kategorie : daten.keySet()) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(kategorie);

            Map<String, Double> monatswerte = daten.get(kategorie);
            String farbe = farben.getOrDefault(kategorie, randomColor());

            for (Map.Entry<String, Double> entry : monatswerte.entrySet()) {
                XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(entry.getKey(), entry.getValue());

                dataPoint.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.setStyle("-fx-bar-fill: " + farbe + ";");

                        String tooltipText = entry.getKey() + ": " + entry.getValue() + " €";
                        Tooltip.install(newNode, new Tooltip(tooltipText));
                    }
                });

                serie.getData().add(dataPoint);
            }

            barChart.getData().add(serie);

            legendenEintraege.add(createLegendItem(kategorie, farbe));
        }

        HBox legend = new HBox(10);
        legend.setPadding(new Insets(10));
        legend.setAlignment(Pos.CENTER);
        legend.getChildren().addAll(legendenEintraege);

        Toast.show(primaryStage, "Monatsvergleich erfolgreich geladen");

        return new VBox(legend, barChart);
    }

    private static HBox createLegendItem(String name, String color) {
        Rectangle rect = new Rectangle(15, 15);
        rect.setFill(Paint.valueOf(color));
        javafx.scene.control.Label label = new javafx.scene.control.Label(name);
        HBox box = new HBox(5, rect, label);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private static String randomColor() {
        Random rand = new Random();
        return String.format("#%06x", rand.nextInt(0xFFFFFF + 1));
    }

    public static Scene createMonatsvergleichScene(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(createUI(primaryStage));

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(MonatsvergleichController.class
                .getResource("/css/style.css")
                .toExternalForm());
        return scene;
    }
}
