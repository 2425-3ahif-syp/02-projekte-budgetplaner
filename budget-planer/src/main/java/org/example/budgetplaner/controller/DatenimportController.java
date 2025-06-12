package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class DatenimportController {

    private BorderPane mainPane;
    private BorderPane manualInputPane;
    private VBox dragDropPane;

    public Parent createContent(Stage primaryStage) {
        mainPane = new BorderPane();

        manualInputPane = createManualInputPane(primaryStage);
        dragDropPane = createDragDropPane(primaryStage);

        mainPane.setCenter(manualInputPane);
        mainPane.setStyle("-fx-background-color: #f7f8fa;");

        return mainPane;
    }

    public BorderPane createManualInputPane(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);

        Label dateLabel = new Label("Datum:");
        dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(300);

        Label amountLabel = new Label("Betrag:");
        amountLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        TextField amountField = new TextField();
        amountField.setPrefWidth(300);
        Label euroLabel = new Label("€");
        euroLabel.setStyle("-fx-font-size: 14px;");
        HBox amountFieldBox = new HBox(5, amountField, euroLabel);
        amountFieldBox.setAlignment(Pos.CENTER);

        Label categoryLabel = new Label("Kategorie:");
        categoryLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.setPrefWidth(300);
        categoryBox.setPromptText("Bitte wählen");
        categoryBox.getItems().addAll("Lebensmittel", "Freizeit", "Haushalt", "Kleidung");



        Button confirmButton = new Button("Bestätigen");
        Button switchToDragDropButton = new Button("Drag & Drop Eingabe");

        String buttonStyle = """
        -fx-background-color: #f8c6cb;
        -fx-text-fill: #6a353f;
        -fx-font-weight: bold;
        -fx-background-radius: 10;
        -fx-padding: 8 20 8 20;
        """;
        confirmButton.setStyle(buttonStyle);
        switchToDragDropButton.setStyle(buttonStyle);

        switchToDragDropButton.setOnAction(e -> mainPane.setCenter(dragDropPane));

        HBox dateBox = new HBox(10, dateLabel, datePicker);
        dateBox.setAlignment(Pos.CENTER);

        HBox amountInputBox = new HBox(10, amountField, euroLabel);
        amountInputBox.setAlignment(Pos.CENTER);

        HBox amountBox = new HBox(5, amountLabel, amountInputBox);
        amountBox.setAlignment(Pos.CENTER);

        HBox categoryBoxContainer = new HBox(10, categoryLabel, categoryBox);
        categoryBoxContainer.setAlignment(Pos.CENTER);

        VBox inputFieldsBox = new VBox(20, dateBox, amountBox, categoryBoxContainer);
        inputFieldsBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(20, confirmButton, switchToDragDropButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainContent = new VBox(30, inputFieldsBox, buttonBox);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(50));
        mainContent.setStyle("-fx-background-color: #fff0f2;");

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(mainContent);

        return root;
    }




    private VBox createDragDropPane(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);
        BorderPane.setMargin(menuBar, Insets.EMPTY);
        VBox root = new VBox(15);
        root.setPadding(new Insets(30, 0, 30, 0));
        root.setStyle("-fx-background-color: #fef7f8;");

        Label title = new Label("Dateien per Drag & Drop hochladen");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #a55a66;");

        VBox dropZone = new VBox();
        dropZone.setStyle("""
        -fx-border-color: #f7c6c7;
        -fx-border-style: dashed;
        -fx-padding: 60;
        -fx-alignment: center;
        -fx-background-color: white;
        -fx-background-radius: 12;
        -fx-border-radius: 12;
        -fx-effect: dropshadow(gaussian, rgba(247,198,199,0.2), 10,0,0,4);
        """);
        Label dropLabel = new Label("Dateien hierher ziehen oder auswählen...");
        dropLabel.setStyle("-fx-text-fill: #bb8891; -fx-font-size: 16;");
        dropZone.getChildren().add(dropLabel);

        dropZone.setOnDragOver(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                dropZone.setStyle("""
                -fx-border-color: #f7a1a3;
                -fx-border-style: solid;
                -fx-padding: 60;
                -fx-alignment: center;
                -fx-background-color: #ffe9ea;
                -fx-background-radius: 12;
                -fx-border-radius: 12;
                -fx-effect: dropshadow(gaussian, rgba(247,161,163,0.4), 15,0,0,6);
                """);
            }
            event.consume();
        });

        dropZone.setOnDragExited(event -> {
            dropZone.setStyle("""
            -fx-border-color: #f7c6c7;
            -fx-border-style: dashed;
            -fx-padding: 60;
            -fx-alignment: center;
            -fx-background-color: white;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-effect: dropshadow(gaussian, rgba(247,198,199,0.2), 10,0,0,4);
            """);
        });

        ListView<String> fileListView = new ListView<>();
        fileListView.setPrefHeight(150);
        fileListView.setStyle("""
        -fx-background-radius: 12;
        -fx-border-radius: 12;
        -fx-border-color: #f7c6c7;
        -fx-background-color: #fff0f2;
        """);

        dropZone.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                for (File file : db.getFiles()) {
                    if (!fileListView.getItems().contains(file.getName())) {
                        fileListView.getItems().add(file.getName());
                    }
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        String buttonStyle = """
        -fx-background-color: #f7c6c7;
        -fx-text-fill: #5a1a1d;
        -fx-font-weight: bold;
        -fx-background-radius: 8;
        -fx-padding: 12 40;
        -fx-font-size: 16;
        """;

        Button chooseFileBtn = new Button("Dateien auswählen");
        chooseFileBtn.setStyle(buttonStyle);
        chooseFileBtn.setMaxWidth(Double.MAX_VALUE);

        Button submitBtn = new Button("Bestätigen");
        submitBtn.setStyle(buttonStyle);
        submitBtn.setMaxWidth(Double.MAX_VALUE);

        Button switchToManualBtn = new Button("Manuell eingeben");
        switchToManualBtn.setStyle(buttonStyle);
        switchToManualBtn.setMaxWidth(Double.MAX_VALUE);

        chooseFileBtn.setOnMouseEntered(e -> chooseFileBtn.setStyle(
                "-fx-background-color: #e3aeb0; -fx-text-fill: #5a1a1d; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 12 40; -fx-font-size: 16;"));
        chooseFileBtn.setOnMouseExited(e -> chooseFileBtn.setStyle(buttonStyle));

        submitBtn.setOnMouseEntered(e -> submitBtn.setStyle(
                "-fx-background-color: #e3aeb0; -fx-text-fill: #5a1a1d; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 12 40; -fx-font-size: 16;"));
        submitBtn.setOnMouseExited(e -> submitBtn.setStyle(buttonStyle));

        switchToManualBtn.setOnMouseEntered(e -> switchToManualBtn.setStyle(
                "-fx-background-color: #e3aeb0; -fx-text-fill: #5a1a1d; -fx-font-weight: bold; -fx-background-radius: 12; -fx-padding: 12 40; -fx-font-size: 16;"));
        switchToManualBtn.setOnMouseExited(e -> switchToManualBtn.setStyle(buttonStyle));

        chooseFileBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Dokumente", "*.pdf", "*.csv", "*.xlsx", "*.xls")
            );
            List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
            if (files != null) {
                for (File file : files) {
                    if (!fileListView.getItems().contains(file.getName())) {
                        fileListView.getItems().add(file.getName());
                    }
                }
            }
        });

        submitBtn.setOnAction(e -> {
            System.out.println("Hochgeladene Dateien:");
            fileListView.getItems().forEach(System.out::println);
        });

        switchToManualBtn.setOnAction(e -> mainPane.setCenter(manualInputPane));

        HBox bottomButtons = new HBox(15, switchToManualBtn, submitBtn);
        bottomButtons.setAlignment(Pos.CENTER_RIGHT);

        root.getChildren().addAll(menuBar, title, dropZone, chooseFileBtn, fileListView, bottomButtons);

        return root;
    }

}
