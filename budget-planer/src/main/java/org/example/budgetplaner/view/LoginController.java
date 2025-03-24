package org.example.budgetplaner.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class LoginController {


    public static MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        Menu accountMenu = new Menu("Account");
        accountMenu.setStyle("-fx-font-weight: bold;");
        MenuItem accountItem = new MenuItem("Mein Account");
        accountItem.setOnAction(e -> primaryStage.setScene(createAccountScene(primaryStage, false)));
        accountMenu.getItems().add(accountItem);

        Menu ausgabenMenu = new Menu("Ausgaben");
        Menu planungMenu = new Menu("Planung");
        Menu monatsvergleichMenu = new Menu("Monatsvergleich");
        Menu datenimportMenu = new Menu("Datenimport");

        menuBar.getMenus().addAll(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        menuBar.setStyle("-fx-background-color: grey;");

        return menuBar;
    }

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
        MenuBar menuBar = createMenuBar(primaryStage);

        final String[] gespeicherterName = {"Max Mustermann"};
        final String[] gespeicherterGeburtstag = {"01.01.2001"};
        final String[] gespeicherteEmail = {"maxmustermann@gmail.com"};
        final String[] gespeichertesPasswort = {"geheim123"};

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(gespeicherterName[0]);
        nameField.setEditable(editMode);

        Label geburtstagLabel = new Label("Geburtstag:");
        TextField geburtstagField = new TextField(gespeicherterGeburtstag[0]);
        geburtstagField.setEditable(editMode);

        Label emailLabel = new Label("E-Mail:");
        TextField emailField = new TextField(gespeicherteEmail[0]);
        emailField.setEditable(editMode);

        Label passwortLabel = new Label("Passwort:");
        PasswordField passwortField = new PasswordField();
        passwortField.setText(gespeichertesPasswort[0]);
        passwortField.setEditable(editMode);

        Button editButton = new Button("Bearbeiten");
        editButton.setOnAction(e -> primaryStage.setScene(createAccountScene(primaryStage, true)));

        if (editMode) {
            Button saveButton = new Button("Speichern");
            saveButton.setOnAction(e -> {
                gespeicherterName[0] = nameField.getText();
                gespeicherterGeburtstag[0] = (geburtstagField.getText());
                gespeicherteEmail[0] = (emailField.getText());
                gespeichertesPasswort[0] = (passwortField.getText());
                primaryStage.setScene(createAccountScene(primaryStage, false));
            });

            grid.add(saveButton, 1, 5);
        } else {
            grid.add(editButton, 1, 5);
        }

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(geburtstagLabel, 0, 1);
        grid.add(geburtstagField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(passwortLabel, 0, 3);
        grid.add(passwortField, 1, 3);

        ToggleGroup currencyGroup = new ToggleGroup();
        RadioButton euroButton = new RadioButton("€");
        RadioButton dollarButton = new RadioButton("$");
        euroButton.setToggleGroup(currencyGroup);
        dollarButton.setToggleGroup(currencyGroup);
        euroButton.setSelected(true);

        if (editMode) {
            euroButton.setDisable(false);
            dollarButton.setDisable(false);
        }
        else {
            euroButton.setDisable(true);
            dollarButton.setDisable(true);
        }


        HBox currencyBox = new HBox(10, euroButton, dollarButton);
        grid.add(currencyBox, 1, 4);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(grid);

        return new Scene(root, 600, 400);
    }
}
