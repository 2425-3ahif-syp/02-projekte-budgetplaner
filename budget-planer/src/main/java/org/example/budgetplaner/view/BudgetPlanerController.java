package org.example.budgetplaner.view;

import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.budgetplaner.Ausgaben;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetPlanerController {

    private static final Map<String, TextField> textFieldMap = new HashMap<>();
    private static List<Integer> kategorieProzente = new ArrayList<>(List.of(20, 20, 20, 15, 15, 10));
    private static String ergebnisTyp;


    public static MenuBar createMenuBar() {


        MenuBar menuBar = new MenuBar();

        Menu accountMenu = new Menu("Account");
        Menu ausgabenMenu = new Menu("Ausgaben");
        Menu planungMenu = new Menu("Planung");
        Menu monatsvergleichMenu = new Menu("Monatsvergleich");
        Menu datenimportMenu = new Menu("Datenimport");




        menuBar.getMenus().addAll(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        menuBar.setStyle("-fx-background-color: grey;");

        return menuBar;
    }



    public static PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(
                new PieChart.Data("Haushalt", kategorieProzente.get(0)),
                new PieChart.Data("Freizeit", kategorieProzente.get(1)),
                new PieChart.Data("Abos", kategorieProzente.get(2)),
                new PieChart.Data("Klamotten", kategorieProzente.get(3)),
                new PieChart.Data("Lebensmittel", kategorieProzente.get(4)),
                new PieChart.Data(ergebnisTyp, kategorieProzente.get(5))
        );

        pieChart.getData().forEach(data -> {
            String name = data.getName();
            if (name != null) {
                data.nameProperty().unbind();
                data.setName(name.split(":")[0]);
            }
        });

        pieChart.getData().forEach(data ->
                data.nameProperty().bind(
                        data.pieValueProperty().asString("%.0f%%")
                )
        );

        return pieChart;
    }

    public static VBox createInputFields() {
        VBox eingaben = new VBox(10);
        String[] kategorien = {"Einnahmen", "Haushalt", "Freizeit", "Abos", "Klamotten", "Lebensmittel", "Überschuss"};
        for (String kategorie : kategorien) {
            HBox hbox = new HBox(5);
            TextField textField = new TextField();
            textFieldMap.put(kategorie, textField);
            hbox.getChildren().addAll(new Label(kategorie + ":"), textField);
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
            String kategorie = entry.getKey();
            String value = entry.getValue();

            try {
                value = value.replace(" ", "");
                numberList.add(Double.parseDouble(value.isEmpty() ? "0" : value));
            } catch (NumberFormatException e) {
                System.out.println("Kategorie: " + kategorie + ", Value: " + value + " is not a number.");
            }
        }

        if (numberList.isEmpty()) {
            return;
        }

        double einnahmen = numberList.get(0);
        numberList.remove(0);
        double sum = numberList.stream().mapToDouble(Double::doubleValue).sum();

        if (einnahmen < sum) {
            numberList.add(einnahmen - sum);
            kategorieProzente.set(5, 0);
            ergebnisTyp = "Verlust";
        } else {
            numberList.add(einnahmen - sum);
            kategorieProzente.set(5, (int) Math.round((einnahmen - sum) / einnahmen * 100));
            ergebnisTyp = "Überschuss";
        }

        for (int i = 0; i < numberList.size() - 1; i++) {
            kategorieProzente.set(i, (int) Math.round(numberList.get(i) / einnahmen * 100));
        }

        createPieChart();
    }
}

