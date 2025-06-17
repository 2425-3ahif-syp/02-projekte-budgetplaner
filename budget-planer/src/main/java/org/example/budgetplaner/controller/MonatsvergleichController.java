/*package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.budgetplaner.databasepack.database.KategorieRepository;
import org.example.budgetplaner.databasepack.database.transactions.TransactionRepository;
import org.example.budgetplaner.databasepack.database.transactions.Transactions;
import org.example.budgetplaner.util.Toast;

import java.util.*;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class MonatsvergleichController {
    private static final TransactionRepository transactionRepository = new TransactionRepository();
    private static final KategorieRepository kategorieRepository = new KategorieRepository();

    public static VBox createUI(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Monate");

        NumberAxis yAxis = new NumberAxis(0, 4000, 500);
        yAxis.setLabel("Betrag (€)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Monatsvergleich");
        barChart.setLegendVisible(false);
        barChart.setAnimated(true);

        barChart.setStyle("-fx-background-color: #ffe9ea;");

        int monat1 = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int jahr1 = Calendar.getInstance().get(Calendar.YEAR);

        int monat2 = monat1 - 1;
        int jahr2 = Calendar.getInstance().get(Calendar.YEAR);

        if (monat2 < 1) {
            monat2 = 12;
            jahr2--;
        }

        if (!transactionRepository.existsByMonthAndYear(monat1, jahr1)) {
            Toast.show(primaryStage, "Keine Daten für den aktuellen Monat vorhanden");
            return new VBox(new Label("Keine Daten für den aktuellen Monat vorhanden"));
        }

        List<Transactions> transactionsMonat1 = transactionRepository.findByMonth(monat1, jahr1);
        List<Transactions> transactionsMonat2 = transactionRepository.findByMonth(monat2, jahr2);

        Map<String, String> farben = Map.of(
                "Haushalt", "#40b4ff",
                "Freizeit", "#8e5dbf",
                "Abos", "#66c2ff",
                "Klamotten", "#a066a0",
                "Lebensmittel", "#3a7cb8",
                "Überschuss", "#63457f",
                "Einnahmen", "#6bdcff"
        );

        List<HBox> legendenEintraege = new ArrayList<>();

        Map<String, Map<String, Double>> daten = new HashMap<>();

        for (Transactions t : transactionsMonat1) {
            String kategorie = kategorieRepository.getCategoryNameById(t.getCategoryId());
            daten.putIfAbsent(kategorie, new HashMap<>());
            daten.get(kategorie).put(monat1 + "/" + jahr1, daten.get(kategorie).getOrDefault(monat1 + "/" + jahr1, 0.0) + t.getAmount());
        }
        for (Transactions t : transactionsMonat2) {
            String kategorie = kategorieRepository.getCategoryNameById(t.getCategoryId());
            daten.putIfAbsent(kategorie, new HashMap<>());
            daten.get(kategorie).put(monat2 + "/" + jahr2, daten.get(kategorie).getOrDefault(monat2 + "/" + jahr2, 0.0) + t.getAmount());
        }

        for (String kategorie : daten.keySet()) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(kategorie);

            Map<String, Double> monatswerte = daten.get(kategorie);
            String farbe = farben.getOrDefault(kategorie, randomColor());

            for (Map.Entry<String, Double> entry : monatswerte.entrySet()) {
                final String monthLabel = entry.getKey();
                final Double value = entry.getValue();
                XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(monthLabel, value);

                dataPoint.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.setStyle("-fx-bar-fill: " + farbe + ";");
                        String tooltipText = monthLabel + ": " + value + " €";
                        Tooltip.install(newNode, new Tooltip(tooltipText));
                    }
                });

                serie.getData().add(dataPoint);
            }

            barChart.getData().add(serie);
            legendenEintraege.add(createLegendItem(kategorie, farbe));
        }

        HBox legend = new HBox(10);
        legend.setPadding(new Insets(10));
        legend.setAlignment(Pos.CENTER);
        legend.getChildren().addAll(legendenEintraege);

        Toast.show(primaryStage, "Monatsvergleich erfolgreich geladen");

        return new VBox(legend, barChart);
    }

    private static HBox createLegendItem(String name, String color) {
        Rectangle rect = new Rectangle(15, 15);
        rect.setFill(Paint.valueOf(color));
        Label label = new Label(name);
        HBox box = new HBox(5, rect, label);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private static String randomColor() {
        Random rand = new Random();
        return String.format("#%06x", rand.nextInt(0xFFFFFF + 1));
    }

    public static Scene createMonatsvergleichScene(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(createUI(primaryStage));
        root.getStyleClass().add("main-pane");

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(MonatsvergleichController.class.getResource("/css/index.css").toExternalForm());
        return scene;
    }
}

 */
package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.budgetplaner.databasepack.database.KategorieReposetory;
import org.example.budgetplaner.databasepack.database.TransactionRepository;
import org.example.budgetplaner.model.TransactionsModel;
import org.example.budgetplaner.util.Toast;

import java.util.*;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class MonatsvergleichController {
    private static final TransactionRepository transactionRepository = new TransactionRepository();
    private static final KategorieReposetory kategorieRepository = new KategorieReposetory();

    public static VBox createUI(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Monate");

        NumberAxis yAxis = new NumberAxis(0, 4000, 500);
        yAxis.setLabel("Betrag (€)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Monatsvergleich");
        barChart.setLegendVisible(false);
        barChart.setAnimated(true);

        barChart.setStyle("-fx-background-color: #ffe9ea;");

        int monat1 = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int jahr1 = Calendar.getInstance().get(Calendar.YEAR);

        int monat2 = monat1 - 1;
        int jahr2 = Calendar.getInstance().get(Calendar.YEAR);

        if (monat2 < 1) {
            monat2 = 12;
            jahr2--;
        }

        if (!transactionRepository.existsByMonthAndYear(monat1, jahr1)) {
            Toast.show(primaryStage, "Keine Daten für den aktuellen Monat vorhanden");
            return new VBox(new Label("Keine Daten für den aktuellen Monat vorhanden"));
        }

        List<TransactionsModel> transactionsMonat1 = transactionRepository.findByMonth(monat1, jahr1);
        List<TransactionsModel> transactionsMonat2 = transactionRepository.findByMonth(monat2, jahr2);

        Map<String, String> farben = Map.of(
                "Haushalt", "#40b4ff",
                "Freizeit", "#8e5dbf",
                "Abos", "#66c2ff",
                "Klamotten", "#a066a0",
                "Lebensmittel", "#3a7cb8",
                "Überschuss", "#63457f",
                "Einnahmen", "#6bdcff"
        );

        List<HBox> legendenEintraege = new ArrayList<>();

        Map<String, Map<String, Double>> daten = new HashMap<>();

        for (TransactionsModel t : transactionsMonat1) {
            String kategorie = kategorieRepository.getCategoryNameById(t.getCategoryId());
            if (kategorie == null) continue;
            daten.putIfAbsent(kategorie, new HashMap<>());
            daten.get(kategorie).put(monat1 + "/" + jahr1, daten.get(kategorie).getOrDefault(monat1 + "/" + jahr1, 0.0) + t.getAmount());
        }

        for (TransactionsModel t : transactionsMonat2) {
            String kategorie = kategorieRepository.getCategoryNameById(t.getCategoryId());
            if (kategorie == null) continue;
            daten.putIfAbsent(kategorie, new HashMap<>());
            daten.get(kategorie).put(monat2 + "/" + jahr2, daten.get(kategorie).getOrDefault(monat2 + "/" + jahr2, 0.0) + t.getAmount());
        }

        for (String kategorie : daten.keySet()) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(kategorie);

            Map<String, Double> monatswerte = daten.get(kategorie);
            String farbe = farben.getOrDefault(kategorie, randomColor());

            for (Map.Entry<String, Double> entry : monatswerte.entrySet()) {
                final String monthLabel = entry.getKey();
                final Double value = entry.getValue();
                XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(monthLabel, value);

                dataPoint.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.setStyle("-fx-bar-fill: " + farbe + ";");
                        String tooltipText = monthLabel + ": " + value + " €";
                        Tooltip.install(newNode, new Tooltip(tooltipText));
                    }
                });

                serie.getData().add(dataPoint);
            }

            barChart.getData().add(serie);
            legendenEintraege.add(createLegendItem(kategorie, farbe));
        }

        HBox legend = new HBox(10);
        legend.setPadding(new Insets(10));
        legend.setAlignment(Pos.CENTER);
        legend.getChildren().addAll(legendenEintraege);

        Toast.show(primaryStage, "Monatsvergleich erfolgreich geladen");

        return new VBox(legend, barChart);
    }

    private static HBox createLegendItem(String name, String color) {
        Rectangle rect = new Rectangle(15, 15);
        rect.setFill(Paint.valueOf(color));
        Label label = new Label(name);
        HBox box = new HBox(5, rect, label);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private static String randomColor() {
        Random rand = new Random();
        return String.format("#%06x", rand.nextInt(0xFFFFFF + 1));
    }

    public static Scene createMonatsvergleichScene(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(createUI(primaryStage));
        root.getStyleClass().add("main-pane");

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(MonatsvergleichController.class.getResource("/css/index.css").toExternalForm());
        return scene;
    }
}