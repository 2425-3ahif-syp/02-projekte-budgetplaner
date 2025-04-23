package org.example.budgetplaner.controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DatenimportController{


    private BorderPane mainPane;
    private VBox manualInputPane;
    private VBox dragDropPane;


    void createManualInputPane(Stage primaryStage) {
        Label dateLabel = new Label("Datum:");
        TextField dateField = new TextField();

        Label amountLabel = new Label("Betrag:");
        TextField amountField = new TextField();

        Label euroLabel = new Label("€");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Lebensmittel", "Freizeit", "Haushalt", "Kleidung");
        categoryBox.setPromptText("Kategorie");

        Button confirmButton = new Button("Bestätigen");
        Button switchToDragDropButton = new Button("Drag & Drop Eingabe");

        switchToDragDropButton.setOnAction(e -> mainPane.setCenter(dragDropPane));

        HBox dateBox = new HBox(10, dateLabel, dateField);
        HBox amountBox = new HBox(10, amountLabel, amountField, euroLabel);
        VBox inputBox = new VBox(15, dateBox, amountBox, categoryBox);
        inputBox.setAlignment(Pos.CENTER_LEFT);

        VBox buttonBox = new VBox(10, confirmButton, switchToDragDropButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox contentBox = new HBox(60, inputBox, buttonBox);
        contentBox.setPadding(new Insets(50));
        contentBox.setAlignment(Pos.CENTER);

        manualInputPane = new VBox(contentBox);
    }

    private void createDragDropPane() {
        Label uploadLabel = new Label("Dokument mit\n Drag & Drop hochlade ");
        uploadLabel.setStyle("-fx-border-color: black; -fx-border-style: dashed; -fx-padding: 60;-fx-alignment: center;");
        uploadLabel.setPrefSize(300, 200);
        uploadLabel.setAlignment(Pos.CENTER);

        uploadLabel.setOnDragOver(event -> {
            if (event.getGestureSource() != uploadLabel && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        uploadLabel.setOnDragDropped(event -> {
            event.setDropCompleted(true);
            event.consume();
        });

        Button confirmButton = new Button("Bestätigen");
        Button switchToManualButton = new Button("Manuelle Eingabe");
        switchToManualButton.setOnAction(e -> mainPane.setCenter(manualInputPane));

        VBox rightBox = new VBox(10, confirmButton, switchToManualButton);
        rightBox.setAlignment(Pos.CENTER);

        HBox dragBox = new HBox(60, uploadLabel, rightBox);
        dragBox.setPadding(new Insets(50));
        dragBox.setAlignment(Pos.CENTER);

        dragDropPane = new VBox(dragBox);
    }
}
