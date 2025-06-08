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

        // Initial Ansicht: manuelle Eingabe
        manualInputPane = createManualInputPane(primaryStage);
        dragDropPane = createDragDropPane(primaryStage);

        mainPane.setCenter(manualInputPane);

        return mainPane;
    }

    public BorderPane createManualInputPane(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);
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

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(contentBox);

        return root;
    }

    private VBox createDragDropPane(Stage primaryStage) {
        BorderPane menuBar = createMenuBar(primaryStage);
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");

        Label title = new Label("Dateien per Drag & Drop hochladen");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        VBox dropZone = new VBox();
        dropZone.setStyle("-fx-border-color: gray; -fx-border-style: dashed; -fx-padding: 40; -fx-alignment: center;");
        Label dropLabel = new Label("Dateien hierher ziehen oder auswählen...");
        dropZone.getChildren().add(dropLabel);

        ListView<String> fileListView = new ListView<>();

        dropZone.setOnDragOver(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        dropZone.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                for (File file : db.getFiles()) {
                    fileListView.getItems().add(file.getName());
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        Button chooseFileBtn = new Button("Dateien auswählen");
        chooseFileBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Dokumente", "*.pdf", "*.csv", "*.xlsx", "*.xls")
            );
            List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
            if (files != null) {
                for (File file : files) {
                    fileListView.getItems().add(file.getName());
                }
            }
        });

        Button submitBtn = new Button("Bestätigen");
        submitBtn.setOnAction(e -> {
            System.out.println("Hochgeladene Dateien:");
            fileListView.getItems().forEach(System.out::println);
        });

        Button switchToManualBtn = new Button("Manuell eingeben");
        switchToManualBtn.setOnAction(e -> mainPane.setCenter(manualInputPane));

        HBox bottomButtons = new HBox(10, switchToManualBtn, submitBtn);
        root.getChildren().addAll(title, dropZone, chooseFileBtn, fileListView, bottomButtons);

        return root;
    }
}
