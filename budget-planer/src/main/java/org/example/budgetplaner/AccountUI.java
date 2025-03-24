package org.example.budgetplaner;

        import javax.swing.text.Element;
        import javax.swing.text.html.ImageView;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.budgetplaner.view.loginPresenter;
import org.example.budgetplaner.view.loginView;
import org.example.budgetplaner.view.menubar;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;


public class AccountUI extends Application {
        @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Account Verwaltung");
            loginView loginview = new loginView();
            loginview.createLoginView(primaryStage);
            new loginPresenter(loginview, primaryStage);
            primaryStage.show();
    }

    public static Scene createAccountScene(Stage primaryStage) {

        MenuBar menuBar = menubar.createMenuBar();


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label geburtstagLabel = new Label("Geburtstag:");
        TextField geburtstagField = new TextField();

        Label emailLabel = new Label("E-Mail:");
        TextField emailField = new TextField();

        Label passwortLabel = new Label("Passwort:");
        PasswordField passwortField = new PasswordField();

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

        HBox currencyBox = new HBox(10, euroButton, dollarButton);
        grid.add(currencyBox, 1, 4);


        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setLeft(grid);


        return new Scene(root, 600, 400);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
