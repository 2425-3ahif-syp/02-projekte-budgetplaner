package org.example.budgetplaner.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.budgetplaner.AccountUI;


public class LoginController {

    public static MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();
        Menu accountMenu = new Menu("Account");
        Menu ausgabenMenu = new Menu("Ausgaben");
        Menu planungMenu = new Menu("Planung");
        accountMenu.setStyle("-fx-font-weight: bold;"); // Fettgedruckt
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

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {

            if (userField.getText().equals("Max Mustermann") && passField.getText().equals("geheim123")) {
                primaryStage.setScene(createAccountScene(primaryStage));
            }
        });

        loginLayout.getChildren().addAll(userLabel, userField, passLabel, passField, loginButton);
        return loginLayout;
    }

    public static Scene createAccountScene(Stage primaryStage) {

        MenuBar menuBar = LoginController.createMenuBar();


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField("Max Mustermann");
        nameField.setEditable(false);


        Label geburtstagLabel = new Label("Geburtstag:");
        TextField geburtstagField = new TextField("01.01.2001");
        geburtstagField.setEditable(false);


        Label emailLabel = new Label("E-Mail:");
        TextField emailField = new TextField("maxmustermann@gmail.com");
        emailField.setEditable(false);

        Label passwortLabel = new Label("Passwort:");
        PasswordField passwortField = new PasswordField();
        passwortField.setText("geheim123");
        passwortField.setEditable(false);

        Button editButton = new Button("Bearbeiten");
        Button saveButton = new Button("Speichern");
        saveButton.setDisable(true);

        editButton.setOnAction(e -> {
            nameField.setEditable(true);
            geburtstagField.setEditable(true);
            emailField.setEditable(true);
            passwortField.setEditable(true);
            editButton.setDisable(true);
            saveButton.setDisable(false);
        });

        saveButton.setOnAction(e -> {
            nameField.setEditable(false);
            geburtstagField.setEditable(false);
            emailField.setEditable(false);
            passwortField.setEditable(false);
            editButton.setDisable(false);
            saveButton.setDisable(true);
        });

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(geburtstagLabel, 0, 1);
        grid.add(geburtstagField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(passwortLabel, 0, 3);
        grid.add(passwortField, 1, 3);
        grid.add(editButton, 0, 5);
        grid.add(saveButton, 1, 5);


        ToggleGroup currencyGroup = new ToggleGroup();
        RadioButton euroButton = new RadioButton("€");
        RadioButton dollarButton = new RadioButton("$");
        euroButton.setToggleGroup(currencyGroup);
        dollarButton.setToggleGroup(currencyGroup);
        euroButton.setSelected(true);
        euroButton.setDisable(true);
        dollarButton.setDisable(true);

        HBox currencyBox = new HBox(10, euroButton, dollarButton);
        grid.add(currencyBox, 1, 4);


        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setLeft(grid);


        return new Scene(root, 600, 400);
    }

}
