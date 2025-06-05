package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class MonatsvergleichController {

    public static VBox createUI(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Einnahmen/Ausgaben pro Monat");

        NumberAxis yAxis = new NumberAxis(0, 4000, 500);
        yAxis.setLabel("Anzahl in €");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Monatsvergleich");
        barChart.setLegendVisible(false);

        Map<String, Map<String, Double>> daten = DatabaseManager.getMonatsdaten(2025);

        Map<String, String> farben = Map.of(
                "Haushalt", "#40b4ff",
                "Freizeit", "#8e5dbf",
                "Abos", "#66c2ff",
                "Klamotten", "#a066a0",
                "Lebensmittel", "#3a7cb8",
                "Überschuss", "#63457f",
                "Einnahmen", "#6bdcff"
        );

        List<HBox> legendenEintraege = new ArrayList<>();

        for (String kategorie : daten.keySet()) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(kategorie);

            Map<String, Double> monatswerte = daten.get(kategorie);
            for (Map.Entry<String, Double> entry : monatswerte.entrySet()) {
                serie.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            barChart.getData().add(serie);

            String farbe = farben.getOrDefault(kategorie, randomColor());
            legendenEintraege.add(createLegendItem(kategorie, farbe));
        }

        HBox legend = new HBox(10);
        legend.setPadding(new Insets(10));
        legend.setAlignment(Pos.CENTER);
        legend.getChildren().addAll(legendenEintraege);

        return new VBox(legend, barChart);
    }

    private static HBox createLegendItem(String name, String color) {
        Rectangle rect = new Rectangle(15, 15);
        rect.setFill(Paint.valueOf(color));
        Label label = new Label(name);
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
        scene.getStylesheets().add(MonatsvergleichController.class.getResource("/css/style.css").toExternalForm());
        return scene;
    }
}


