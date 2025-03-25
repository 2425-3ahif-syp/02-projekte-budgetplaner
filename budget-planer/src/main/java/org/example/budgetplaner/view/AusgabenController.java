package org.example.budgetplaner.view;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.example.budgetplaner.view.menubar.createMenuBar;


public class AusgabenController  {

    public static VBox createUI(Stage primaryStage) {
        String[] categories = {"Haushalt", "Freizeit", "Abos", "Klamotten", "Lebensmittel", "Überschuss"};
        double[] values = {20, 15, 10, 10, 25, 20};
        double total = 0;
        for (double value : values) total += value;

        PieChart pieChart = new PieChart();
        for (int i = 0; i < categories.length; i++) {
            PieChart.Data slice = new PieChart.Data(categories[i], values[i]);
            double percentage = (values[i] / total) * 100;
            slice.setName(String.format("%s: %.2f%%", categories[i], percentage));  // Prozentsatz hinzufügen
            pieChart.getData().add(slice);
        }


        VBox root = new VBox(10, pieChart);
        return root;
    }

    public static Scene createAusgabenScene(Stage primaryStage) {
        MenuBar menuBar = createMenuBar(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(createUI(primaryStage));
        return new Scene(root, 800, 600);
    }
}
