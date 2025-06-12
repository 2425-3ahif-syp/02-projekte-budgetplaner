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
        mainPane.getStyleClass().add("main-pane");

        return mainPane;
    }

    public BorderPane createManualInputPane(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);

        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(40));
        formGrid.setVgap(15);
        formGrid.setHgap(10);
        formGrid.getStyleClass().add("manual-pane");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(60);
        formGrid.getColumnConstraints().addAll(col1, col2, col3);

        Label dateLabel = new Label("Datum:");
        dateLabel.setStyle("-fx-font-weight: bold;");
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(300);

        Label amountLabel = new Label("Betrag:");
        amountLabel.setStyle("-fx-font-weight: bold;");
        TextField amountField = new TextField();
        amountField.setPrefWidth(260);
        Label euroLabel = new Label("€");
        HBox amountFieldBox = new HBox(5, amountField, euroLabel);
        amountFieldBox.setAlignment(Pos.CENTER_LEFT);

        Label categoryLabel = new Label("Kategorie:");
        categoryLabel.setStyle("-fx-font-weight: bold;");
        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.setPrefWidth(300);
        categoryBox.setPromptText("Bitte wählen");
        categoryBox.getItems().addAll("Lebensmittel", "Freizeit", "Haushalt", "Kleidung");

        Button confirmButton = new Button("Bestätigen");
        Button switchToDragDropButton = new Button("Drag & Drop Eingabe");

        confirmButton.getStyleClass().add("primary-button");
        switchToDragDropButton.getStyleClass().add("primary-button");

        switchToDragDropButton.setOnAction(e -> mainPane.setCenter(dragDropPane));

        HBox buttonBox = new HBox(20, confirmButton, switchToDragDropButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);


        formGrid.add(dateLabel, 1, 5);
        formGrid.add(datePicker, 2, 5);

        formGrid.add(amountLabel, 1, 7);
        formGrid.add(amountFieldBox, 2, 7);

        formGrid.add(categoryLabel, 1, 9);
        formGrid.add(categoryBox, 2, 9);

        formGrid.add(buttonBox, 2, 11
        );

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(formGrid);

        return root;
    }

    private VBox createDragDropPane(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.getStyleClass().add("dragdrop-pane");

        Label title = new Label("Dateien per Drag & Drop hochladen");
        title.getStyleClass().add("dragdrop-title");

        VBox dropZone = new VBox();
        dropZone.getStyleClass().add("drop-zone");
        Label dropLabel = new Label("Dateien hierher ziehen oder auswählen...");
        dropLabel.getStyleClass().add("drop-label");
        dropZone.getChildren().add(dropLabel);

        ListView<String> fileListView = new ListView<>();
        fileListView.setPrefHeight(150);
        fileListView.getStyleClass().add("file-list");

        dropZone.setOnDragOver(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                dropZone.getStyleClass().remove("drop-zone");
                dropZone.getStyleClass().add("drop-zone-active");
            }
            event.consume();
        });

        dropZone.setOnDragExited(event -> {
            dropZone.getStyleClass().remove("drop-zone-active");
            dropZone.getStyleClass().add("drop-zone");
        });

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

        Button chooseFileBtn = new Button("Dateien auswählen");
        Button submitBtn = new Button("Bestätigen");
        Button switchToManualBtn = new Button("Manuell eingeben");

        chooseFileBtn.getStyleClass().add("primary-button");
        submitBtn.getStyleClass().add("primary-button");
        switchToManualBtn.getStyleClass().add("primary-button");

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
