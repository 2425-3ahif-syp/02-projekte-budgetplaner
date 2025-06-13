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
import org.example.budgetplaner.databasepack.database.BudgetReposetory;
import org.example.budgetplaner.databasepack.database.KategorieReposetory;
import org.example.budgetplaner.model.BudgetModel;
import org.example.budgetplaner.model.KategorieModel;

import java.sql.SQLException;
import java.util.*;


import static org.example.budgetplaner.controller.Menubar.createMenuBar;


public class BudgetPlanerController {

    private static final KategorieReposetory kategorieReposetory = new KategorieReposetory();
    private static final BudgetReposetory budgetReposetory = new BudgetReposetory();
    private static final Map<String, TextField> textFieldMap = new HashMap<>();
    private static String ergebnisTyp = "Überschuss";
    private static PieChart pieChart;


    public static PieChart createPieChart() {
        pieChart = new PieChart();

        updatePieChart();

        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(true);
        pieChart.setLegendSide(javafx.geometry.Side.TOP);
        pieChart.setPrefWidth(400);
        pieChart.getStyleClass().add("main-pane");
        return pieChart;
    }

    private static void updatePieChart() {
        pieChart.getData().clear();
        List<Integer> latestYearAndMonth = budgetReposetory.getLatestYearAndMonth();
        int month = latestYearAndMonth.get(0);
        int year = latestYearAndMonth.get(1);
        List<BudgetModel> budgetList = budgetReposetory.getBudgetModelsByMonthAndYear(month, year);
        int einnahmenId = kategorieReposetory.getCategoryIdByName("Einnahmen");
        float ausgaben = 0;
        float einnahmen = 0;

        for (BudgetModel budgetModel : budgetList) {
            if (budgetModel.getKategorieId() != einnahmenId) {
                ausgaben += budgetModel.getBetrag();
            } else {
                einnahmen += budgetModel.getBetrag();
            }
        }
        for (BudgetModel budgetModel : budgetList) {
            if (einnahmen > ausgaben) {
                ergebnisTyp = "Überschuss";
            } else {
                ergebnisTyp = "Defizit";
            }
            if (budgetModel.getKategorieId() != einnahmenId) {
                pieChart.getData().add(
                        new PieChart.Data(kategorieReposetory.getCategoryNameById(budgetModel.getKategorieId()), ((budgetModel.getBetrag() / einnahmen)))
                );
            }
        }
        if(ergebnisTyp == "Überschuss") {
            pieChart.getData().add(
                    new PieChart.Data("Überschuss", ((einnahmen - ausgaben) / einnahmen))
            );
        } else {
            pieChart.getData().add(
                    new PieChart.Data("Defizit", ((ausgaben - einnahmen) / einnahmen))
            );
        }
        pieChart.getData().forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), ": ", data.pieValueProperty().asString("%.0f %%"))
                )
        );
    }

    public static VBox createInputFields() {
        VBox eingaben = new VBox(10);
        eingaben.setPadding(new Insets(20));

        List<KategorieModel> kategorien = kategorieReposetory.getCategories();


        for (KategorieModel kategorie : kategorien) {
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER_LEFT);

            Label label = new Label(kategorie + ":");
            label.setPrefWidth(100);
            label.getStyleClass().add("-fx-font-weight: bold;");

            TextField textField = new TextField();
            textField.setPrefWidth(100);
            textField.getStyleClass().add("text-field");

            textFieldMap.put(kategorie.getName(), textField);

            hbox.getChildren().addAll(label, textField);
            eingaben.getChildren().add(hbox);
        }

        Button enterButton = new Button("Enter");
        enterButton.setOnAction(BudgetPlanerController::saveInputValues);
        enterButton.getStyleClass().add("primary-button");
        eingaben.getChildren().add(enterButton);


        return eingaben;
    }

    private static void saveInputValues(ActionEvent actionEvent) {

        for (Map.Entry<String, TextField> entry : textFieldMap.entrySet()) {
            String label = entry.getKey();
            String value = entry.getValue().getText();
            System.out.println(label + ": " + value);
            try {
                int kategorieId = kategorieReposetory.getCategoryIdByName(label);
                float amount = Float.parseFloat(value);
                int month = java.time.LocalDate.now().getMonthValue();
                int year = java.time.LocalDate.now().getYear();
                budgetReposetory.createNewBudgetPlan(amount, month, year, kategorieId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ungültiger Betrag für " + label + ": " + value);
                alert.showAndWait();
                return;
            }
        }

    }

    public static Scene createBudgetPlanerScene(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.getStyleClass().add("main-pane");
        root.setCenter(BudgetPlanerController.createPieChart());
        root.setRight(BudgetPlanerController.createInputFields());

        Scene scene = new Scene(root, 1000, 600);

        scene.getStylesheets().add(
                BudgetPlanerController.class.getResource("/css/index.css").toExternalForm()
        );

        return scene;

    }
}

