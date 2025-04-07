package org.example.budgetplaner.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class BudgetPlanerController {

    private static final Map<String, TextField> textFieldMap = new HashMap<>();
    private static List<Integer> kategorieProzente = new ArrayList<>(List.of(20, 20, 20, 15, 15, 10));
    private static String ergebnisTyp = "Überschuss";
    private static PieChart pieChart;



    public static PieChart createPieChart() {
        pieChart = new PieChart();

        updatePieChart();

        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(true);
        pieChart.setLegendSide(javafx.geometry.Side.TOP);
        pieChart.setPrefWidth(400);

        return pieChart;
    }

    private static void updatePieChart() {
        pieChart.getData().clear();

        pieChart.getData().addAll(
                new PieChart.Data("Haushalt", kategorieProzente.get(0)),
                new PieChart.Data("Freizeit", kategorieProzente.get(1)),
                new PieChart.Data("Abos", kategorieProzente.get(2)),
                new PieChart.Data("Klamotten", kategorieProzente.get(3)),
                new PieChart.Data("Lebensmittel", kategorieProzente.get(4)),
                new PieChart.Data(ergebnisTyp, kategorieProzente.get(5))
        );

        // Prozentwerte im Chart anzeigen
        pieChart.getData().forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), ": ", data.pieValueProperty().asString("%.0f %%"))
                )
        );
    }

    public static VBox createInputFields() {
        VBox eingaben = new VBox(10);
        eingaben.setPadding(new Insets(20));

        String[] kategorien = {"Einnahmen", "Haushalt", "Freizeit", "Abos", "Klamotten", "Lebensmittel", "Überschuss"};

        for (String kategorie : kategorien) {
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER_LEFT);

            Label label = new Label(kategorie + ":");
            label.setPrefWidth(100);

            TextField textField = new TextField();
            textField.setPrefWidth(100);

            textFieldMap.put(kategorie, textField);

            hbox.getChildren().addAll(label, textField);
            eingaben.getChildren().add(hbox);
        }

        Button enterButton = new Button("Enter");
        enterButton.setOnAction(BudgetPlanerController::saveInputValues);
        eingaben.getChildren().add(enterButton);

        return eingaben;
    }

    private static void saveInputValues(ActionEvent actionEvent) {
        Map<String, String> inputValues = new HashMap<>();
        textFieldMap.forEach((kategorie, textField) -> inputValues.put(kategorie, textField.getText()));
        List<Double> numberList = new ArrayList<>();

        for (Map.Entry<String, String> entry : inputValues.entrySet()) {
            try {
                String cleaned = entry.getValue().replace(" ", "");
                numberList.add(Double.parseDouble(cleaned.isEmpty() ? "0" : cleaned));
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe bei: " + entry.getKey());
                return;
            }
        }

        if (numberList.isEmpty()) return;

        double einnahmen = numberList.get(0);
        numberList.remove(0);

        double ausgabenSumme = numberList.stream().mapToDouble(Double::doubleValue).sum();
        double rest = einnahmen - ausgabenSumme;

        if (einnahmen <= 0) return;

        ergebnisTyp = rest < 0 ? "Verlust" : "Überschuss";

        kategorieProzente.clear();
        for (double betrag : numberList) {
            kategorieProzente.add((int) Math.round(betrag / einnahmen * 100));
        }

        kategorieProzente.add((int) Math.round(Math.abs(rest) / einnahmen * 100));

        updatePieChart();
    }
    public static Scene createBudgetPlanerScene(Stage primaryStage) {
        MenuBar menuBar = createMenuBar(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(BudgetPlanerController.createPieChart());
        root.setRight(BudgetPlanerController.createInputFields());

        return new Scene(root, 1000, 600);

    }
}

