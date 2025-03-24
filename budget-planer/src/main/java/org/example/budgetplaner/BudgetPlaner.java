package org.example.budgetplaner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.budgetplaner.view.BudgetPlanerController;

import static org.example.budgetplaner.view.menubar.createMenuBar;


public class BudgetPlaner extends Application {

    @Override
    public void start(Stage stage) {
        MenuBar menuBar = createMenuBar(stage);
        PieChart pieChart = BudgetPlanerController.createPieChart();
        VBox eingaben = BudgetPlanerController.createInputFields();
        eingaben.setPadding(new Insets(10));
        eingaben.setAlignment(Pos.CENTER);

        VBox root = new VBox(menuBar);
        Label heading = new Label("Planung");
        heading.setStyle("-fx-font-weight: bold; fx-font-size: 50px;");
        heading.setPadding(new Insets(10));
        heading.setAlignment(Pos.CENTER);
        HBox content = new HBox(10, pieChart, eingaben);
        content.setPadding(new Insets(20));
        root.getChildren().addAll(heading, content);

        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Budget Planer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}