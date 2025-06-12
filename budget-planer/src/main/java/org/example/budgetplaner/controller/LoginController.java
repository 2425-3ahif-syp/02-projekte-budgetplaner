package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

import java.io.File;

public class LoginController {

    private static String gespeicherterName = "Max Mustermann";
    private static String gespeicherterGeburtstag = "01.01.2001";
    private static String gespeicherteEmail = "maxmustermann@gmail.com";
    private static String gespeichertesPasswort = "geheim123";

    // Variable für das Profilbild (URI oder Pfad)
    private static String profilBildPfad = null;

    public static VBox createLoginView(Stage primaryStage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));

        Label userLabel = new Label("Benutzername:");
        TextField userField = new TextField();

        Label passLabel = new Label("Passwort:");
        PasswordField passField = new PasswordField();

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            if (userField.getText().equals("Max Mustermann") && passField.getText().equals("geheim123")) {
                primaryStage.setScene(createAccountScene(primaryStage, false));
            } else {
                errorLabel.setText("Falscher Benutzername oder Passwort!");
            }
        });

        loginLayout.getChildren().addAll(userLabel, userField, passLabel, passField, loginButton, errorLabel);
        return loginLayout;
    }

    public static Scene createAccountScene(Stage primaryStage, boolean editMode) {
        BorderPane menuBar = createMenuBar(primaryStage);

        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(40));
        formGrid.setVgap(15);
        formGrid.setHgap(10);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        formGrid.getColumnConstraints().addAll(col1, col2);

        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        TextField nameField = new TextField(gespeicherterName);
        nameField.setEditable(editMode);
        nameField.setStyle("-fx-padding: 8px; -fx-background-color: #ffffff; -fx-border-radius: 5px;");
        nameField.setPrefWidth(200);

        Label geburtstagLabel = new Label("Geburtstag:");
        geburtstagLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        TextField geburtstagField = new TextField(gespeicherterGeburtstag);
        geburtstagField.setEditable(editMode);
        geburtstagField.setStyle("-fx-padding: 8px; -fx-background-color: #ffffff; -fx-border-radius: 5px;");

        Label emailLabel = new Label("E-Mail:");
        emailLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        TextField emailField = new TextField(gespeicherteEmail);
        emailField.setEditable(editMode);
        emailField.setStyle("-fx-padding: 8px; -fx-background-color: #ffffff; -fx-border-radius: 5px;");

        Label passwortLabel = new Label("Passwort:");
        passwortLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        PasswordField passwortField = new PasswordField();
        passwortField.setText(gespeichertesPasswort);
        passwortField.setEditable(editMode);
        passwortField.setStyle("-fx-padding: 8px; -fx-background-color: #ffffff; -fx-border-radius: 5px;");

        ToggleGroup currencyGroup = new ToggleGroup();
        RadioButton euroButton = new RadioButton("€");
        RadioButton dollarButton = new RadioButton("$");
        euroButton.setToggleGroup(currencyGroup);
        dollarButton.setToggleGroup(currencyGroup);
        euroButton.setSelected(true);
        euroButton.setDisable(!editMode);
        dollarButton.setDisable(!editMode);

        HBox currencyBox = new HBox(10, euroButton, dollarButton);

        String buttonStyle = """
            -fx-background-color: #f8c6cb;
            -fx-text-fill: #6a353f;
            -fx-font-weight: bold;
            -fx-background-radius: 10;
            -fx-padding: 8 20 8 20;
            """;

        String buttonHoverStyle = """
            -fx-background-color: #e3aeb0;
            -fx-text-fill: #6a353f;
            -fx-font-weight: bold;
            -fx-background-radius: 10;
            -fx-padding: 8 20 8 20;
            """;

        Button actionButton;
        if (editMode) {
            actionButton = new Button("Speichern");
            actionButton.setStyle(buttonStyle);
            actionButton.setOnMouseEntered(e -> actionButton.setStyle(buttonHoverStyle));
            actionButton.setOnMouseExited(e -> actionButton.setStyle(buttonStyle));

            actionButton.setOnAction(e -> {
                gespeicherterName = nameField.getText();
                gespeicherterGeburtstag = geburtstagField.getText();
                gespeicherteEmail = emailField.getText();
                gespeichertesPasswort = passwortField.getText();
                primaryStage.setScene(createAccountScene(primaryStage, false));
            });
        } else {
            actionButton = new Button("Bearbeiten");
            actionButton.setStyle(buttonStyle);
            actionButton.setOnMouseEntered(e -> actionButton.setStyle(buttonHoverStyle));
            actionButton.setOnMouseExited(e -> actionButton.setStyle(buttonStyle));

            actionButton.setOnAction(e -> primaryStage.setScene(createAccountScene(primaryStage, true)));
        }

        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(geburtstagLabel, 0, 1);
        formGrid.add(geburtstagField, 1, 1);
        formGrid.add(emailLabel, 0, 2);
        formGrid.add(emailField, 1, 2);
        formGrid.add(passwortLabel, 0, 3);
        formGrid.add(passwortField, 1, 3);
        formGrid.add(currencyBox, 1, 4);
        formGrid.add(actionButton, 1, 5);

        Image image;
        if (profilBildPfad != null) {
            image = new Image(profilBildPfad, true);
        } else {
            image = new Image("file:images/profile.png", true);
        }
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(250);

        VBox imageBox = new VBox(10, imageView);
        imageBox.setAlignment(Pos.TOP_CENTER);
        imageBox.setPadding(new Insets(40, 20, 20, 30));

        if (editMode) {
            Button changePicButton = new Button("Profilbild ändern");
            changePicButton.setStyle(buttonStyle);
            changePicButton.setOnMouseEntered(e -> changePicButton.setStyle(buttonHoverStyle));
            changePicButton.setOnMouseExited(e -> changePicButton.setStyle(buttonStyle));

            changePicButton.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Profilbild auswählen");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Bilddateien", "*.png", "*.jpg", "*.jpeg", "*.gif")
                );
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    profilBildPfad = selectedFile.toURI().toString();
                    imageView.setImage(new Image(profilBildPfad, true));
                }
            });

            imageBox.getChildren().add(changePicButton);
        }

        HBox contentBox = new HBox(150, formGrid, imageBox);
        contentBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(contentBox);
        root.setStyle("-fx-background-color: #fff0f2;");  // hellrosaner Hintergrund

        return new Scene(root, 1000, 600);
    }
}
