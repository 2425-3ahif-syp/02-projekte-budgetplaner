package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class MonatsvergleichController {

    public static VBox createUI(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Einnahmen/Ausgaben pro Monat");

        NumberAxis yAxis = new NumberAxis(0, 3500, 500); // Min = 0, Max = 4000, Schritt = 500
        yAxis.setLabel("Anzahl in €");


        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Monatsvergleich");
        barChart.setLegendVisible(false);

        // Datenserien
        XYChart.Series<String, Number> haushalt = new XYChart.Series<>();
        haushalt.setName("Haushalt");
        haushalt.getData().add(new XYChart.Data<>("Jänner", 900));
        haushalt.getData().add(new XYChart.Data<>("Februar", 1000));

        XYChart.Series<String, Number> freizeit = new XYChart.Series<>();
        freizeit.setName("Freizeit");
        freizeit.getData().add(new XYChart.Data<>("Jänner", 300));
        freizeit.getData().add(new XYChart.Data<>("Februar", 200));

        XYChart.Series<String, Number> abos = new XYChart.Series<>();
        abos.setName("Abos");
        abos.getData().add(new XYChart.Data<>("Jänner", 80));
        abos.getData().add(new XYChart.Data<>("Februar", 60));

        XYChart.Series<String, Number> klamotten = new XYChart.Series<>();
        klamotten.setName("Klamotten");
        klamotten.getData().add(new XYChart.Data<>("Jänner", 250));
        klamotten.getData().add(new XYChart.Data<>("Februar", 300));

        XYChart.Series<String, Number> lebensmittel = new XYChart.Series<>();
        lebensmittel.setName("Lebensmittel");
        lebensmittel.getData().add(new XYChart.Data<>("Jänner", 200));
        lebensmittel.getData().add(new XYChart.Data<>("Februar", 250));

        XYChart.Series<String, Number> überschuss = new XYChart.Series<>();
        überschuss.setName("Überschuss");
        überschuss.getData().add(new XYChart.Data<>("Jänner", 950));
        überschuss.getData().add(new XYChart.Data<>("Februar", 1400));

        XYChart.Series<String, Number> einnahmen = new XYChart.Series<>();
        einnahmen.setName("Einnahmen");
        einnahmen.getData().add(new XYChart.Data<>("Jänner", 3000));
        einnahmen.getData().add(new XYChart.Data<>("Februar", 3200));

        barChart.getData().addAll(
                haushalt,
                freizeit,
                abos,
                klamotten,
                lebensmittel,
                überschuss,
                einnahmen
        );

        // Manuelle Legende
        HBox legend = new HBox(10);
        legend.setPadding(new Insets(10));
        legend.setAlignment(Pos.CENTER);

        legend.getChildren().addAll(
                createLegendItem("Haushalt", "#40b4ff"),
                createLegendItem("Freizeit", "#8e5dbf"),
                createLegendItem("Abos", "#66c2ff"),
                createLegendItem("Klamotten", "#a066a0"),
                createLegendItem("Lebensmittel", "#3a7cb8"),
                createLegendItem("Überschuss", "#63457f"),
                createLegendItem("Einnahmen", "#6bdcff")
        );

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

    public static Scene createMonatsvergleichScene(Stage primaryStage) {
        MenuBar menuBar = createMenuBar(primaryStage);
        Menu monatsvergleich = menuBar.getMenus().stream()
                .filter(menu -> menu.getText().equals("Monatsvergleich"))
                .findFirst().orElse(null);
        if (monatsvergleich != null) {
            monatsvergleich.getStyleClass().add("monatsvergleich");
        }

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(createUI(primaryStage));

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(MonatsvergleichController.class.getResource("/css/style.css").toExternalForm());
        return scene;
    }
}


