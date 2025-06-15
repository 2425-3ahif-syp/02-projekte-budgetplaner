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
        int month, year;
        List<BudgetModel> budgetList = new ArrayList<>();

        if (latestYearAndMonth.size() >= 2) {
            month = latestYearAndMonth.get(0);
            year = latestYearAndMonth.get(1);
            budgetList.addAll(budgetReposetory.getBudgetModelsByMonthAndYear(month, year));
        } else {
            List<KategorieModel> categories = kategorieReposetory.getCategories();
            for (KategorieModel category : categories) {
                budgetList.add(new BudgetModel(category.getName(), 0.00f, 0, 0, category.getId()));
            }
        }

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

        // Avoid division by zero
        if (einnahmen == 0) {
            for (BudgetModel budgetModel : budgetList) {
                if (budgetModel.getKategorieId() != einnahmenId) {
                    pieChart.getData().add(
                            new PieChart.Data(kategorieReposetory.getCategoryNameById(budgetModel.getKategorieId()), 0)
                    );
                }
            }
            pieChart.getData().add(new PieChart.Data("Keine Einnahmen", 1));
        } else {
            ergebnisTyp = einnahmen > ausgaben ? "Überschuss" : "Defizit";
            for (BudgetModel budgetModel : budgetList) {
                if (budgetModel.getKategorieId() != einnahmenId) {
                    pieChart.getData().add(
                            new PieChart.Data(
                                    kategorieReposetory.getCategoryNameById(budgetModel.getKategorieId()),
                                    budgetModel.getBetrag() / einnahmen
                            )
                    );
                }
            }
            if ("Überschuss".equals(ergebnisTyp)) {
                pieChart.getData().add(
                        new PieChart.Data("Überschuss", (einnahmen - ausgaben) / einnahmen)
                );
            } else {
                pieChart.getData().add(
                        new PieChart.Data("Defizit", (ausgaben - einnahmen) / einnahmen)
                );
            }
        }

        pieChart.getData().forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName(), ": ", data.pieValueProperty().multiply(100).asString("%.0f %%"))
                )
        );
    }

    public static VBox createInputFields() {
        VBox eingaben = new VBox(10);
        eingaben.setPadding(new Insets(20));
        System.out.println("Creating input fields for budget categories...");
        List<KategorieModel> kategorien = kategorieReposetory.getCategories();
        System.out.println("Kategorien: " + kategorien.size());

        for (KategorieModel kategorie : kategorien) {
            System.out.println(kategorie.getName());
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER_LEFT);

            Label label = new Label(kategorie.getName() + ":");
            label.setPrefWidth(100);
            label.getStyleClass().add("bold-label"); // Use a CSS class, not a property

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

