package org.example.budgetplaner.view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.example.budgetplaner.view.menubar.createMenuBar;

public class Monatsvergleich {

    public static VBox createUI(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Einnahmen/Ausgaben pro Monat");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Anzahl in €");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Beispieldaten
        XYChart.Series<String, Number> einnahmen = new XYChart.Series<>();
        einnahmen.setName("Einnahmen");
        einnahmen.getData().add(new XYChart.Data<>("Jänner", 3000));
        einnahmen.getData().add(new XYChart.Data<>("Februar", 2800));

        XYChart.Series<String, Number> ausgaben = new XYChart.Series<>();
        ausgaben.setName("Ausgaben");
        ausgaben.getData().add(new XYChart.Data<>("Jänner", 1800));
        ausgaben.getData().add(new XYChart.Data<>("Februar", 2000));

        XYChart.Series<String, Number> überschuss = new XYChart.Series<>();
        überschuss.setName("Überschuss");
        überschuss.getData().add(new XYChart.Data<>("Jänner", 1200));
        überschuss.getData().add(new XYChart.Data<>("Februar", 800));

        barChart.getData().addAll(einnahmen, ausgaben, überschuss);

        VBox root = new VBox(10, barChart);
        return root;
    }

    public static Scene createMonatsvergleichScene(Stage primaryStage) {
        MenuBar menuBar = createMenuBar(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(createUI(primaryStage));
        return new Scene(root, 800, 600);
    }
}


