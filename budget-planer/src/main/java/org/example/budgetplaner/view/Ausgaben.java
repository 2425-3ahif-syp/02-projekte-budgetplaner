package org.example.budgetplaner.view;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class Ausgaben  {
    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {

        pieChart.getData().add(new PieChart.Data("Äpfel", 30));
        pieChart.getData().add(new PieChart.Data("Bananen", 20));
        pieChart.getData().add(new PieChart.Data("Orangen", 50));


        pieChart.setTitle("Ausgaben");
    }
}
