package org.example.budgetplaner.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.budgetplaner.databasepack.database.KategorieReposetory;
import org.example.budgetplaner.databasepack.database.transactions.TransactionRepository;
import org.example.budgetplaner.databasepack.database.transactions.Transactions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AusgabenController {

    private final TransactionRepository transactionRepository;
    private final KategorieReposetory kategorieReposetory;

    public AusgabenController() {
        this.transactionRepository = new TransactionRepository();
        this.kategorieReposetory = new KategorieReposetory();
    }


        public VBox createUI(Stage primaryStage) {
            PieChart pieChart = new PieChart();
            pieChart.getStyleClass().add("main-pane");
            pieChart.setPrefSize(800, 600);

            Map<String, Double> daten = new HashMap<>();

            try {
                List<Transactions> transactions = transactionRepository.findAll();
                double einnahmen = 0;
                double ausgaben = 0;

                for (Transactions transaction : transactions) {
                    double betrag = transaction.getAmount();
                    if (transaction.getType()) {
                        einnahmen += betrag;
                    } else {
                        ausgaben += Math.abs(betrag);
                        String categoryName = kategorieReposetory.getCategoryNameById(transaction.getCategoryId());
                        if (categoryName == null) categoryName = "Unbekannt";
                        daten.put(categoryName, daten.getOrDefault(categoryName, 0.0) + Math.abs(betrag));
                    }
                }

                for (Map.Entry<String, Double> entry : daten.entrySet()) {
                    pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }

                double überschuss = einnahmen - ausgaben;
                pieChart.getData().add(new PieChart.Data("Einnahmen", einnahmen));
                pieChart.getData().add(new PieChart.Data("Überschuss", Math.max(überschuss, 0)));

            } catch (Exception e) {
                e.printStackTrace();
            }

            pieChart.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    Platform.runLater(() -> {
                        for (int j = 0; j < pieChart.getData().size(); j++) {
                            Node node = pieChart.getData().get(j).getNode();
                            if (node != null) {
                                node.getStyleClass().add("default-color" + j);
                            }
                        }
                    });
                }
            });

            // Menüleiste hinzufügen
            BorderPane menuBar = Menubar.createMenuBar(primaryStage);

            VBox root = new VBox(0, menuBar, pieChart);
            root.getStyleClass().add("main-pane");

            return root;
        }

    

    public Scene createAusgabenScene(Stage primaryStage) {
        VBox root = createUI(primaryStage);
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/css/index.css").toExternalForm());
        return scene;
    }
}
