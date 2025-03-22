package org.example.budgetplaner.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.budgetplaner.accountUI;


public class loginView {
    private static VBox loginLayout;
    private TextField userField;
    private PasswordField passField;
    private static Button loginButton;

    public static VBox createLoginView(Stage primaryStage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));

        Label userLabel = new Label("Benutzername:");
        TextField userField = new TextField();

        Label passLabel = new Label("Passwort:");
        PasswordField passField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            primaryStage.setScene(accountUI.createAccountScene(primaryStage));
        });

        loginLayout.getChildren().addAll(userLabel, userField, passLabel, passField, loginButton);
        return loginLayout;
    }

    public static VBox getLayout() {
        return loginLayout;
    }

    public static Button getLoginButton() {
        return loginButton;
    }
}
