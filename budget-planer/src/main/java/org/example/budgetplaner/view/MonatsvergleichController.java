package org.example.budgetplaner.view;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.example.budgetplaner.view.menubar.createMenuBar;



    public class MonatsvergleichController {

        public static VBox createUI(Stage primaryStage) {
            // Erstelle BarChart
            BarChart<String, Number> barChart = new BarChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
            barChart.setTitle("Monatsvergleich");

            // Daten für Einnahmen
            XYChart.Series<String, Number> einnahmen = new XYChart.Series<>();
            einnahmen.setName("Einnahmen");
            einnahmen.getData().add(new XYChart.Data<>("Jänner", 3000));
            einnahmen.getData().add(new XYChart.Data<>("Februar", 2800));

            // Daten für Ausgaben
            XYChart.Series<String, Number> ausgaben = new XYChart.Series<>();
            ausgaben.setName("Ausgaben");
            ausgaben.getData().add(new XYChart.Data<>("Jänner", 1800));
            ausgaben.getData().add(new XYChart.Data<>("Februar", 2000));

            // Daten für Überschuss
            XYChart.Series<String, Number> überschuss = new XYChart.Series<>();
            überschuss.setName("Überschuss");
            überschuss.getData().add(new XYChart.Data<>("Jänner", 1200));
            überschuss.getData().add(new XYChart.Data<>("Februar", 800));

            // Füge Daten dem Chart hinzu
            barChart.getData().addAll(einnahmen, ausgaben, überschuss);

            // Erstelle und gebe VBox mit dem BarChart zurück
            return new VBox(10, barChart);
        }

        public static Scene createMonatsvergleichScene(Stage primaryStage) {
            MenuBar menuBar = createMenuBar(primaryStage);

            BorderPane root = new BorderPane();
            root.setTop(menuBar);
            root.setCenter(createUI(primaryStage));
            return new Scene(root, 800, 600);
        }
    }

