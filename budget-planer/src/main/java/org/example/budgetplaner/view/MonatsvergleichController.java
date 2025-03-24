package org.example.budgetplaner.view;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class MonatsvergleichController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
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
    }
}
