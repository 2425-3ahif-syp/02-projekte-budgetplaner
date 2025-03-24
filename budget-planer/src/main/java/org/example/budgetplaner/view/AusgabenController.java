package org.example.budgetplaner.view;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AusgabenController  {
    public static Stage primaryStage;



    public VBox createUI() {
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
}
