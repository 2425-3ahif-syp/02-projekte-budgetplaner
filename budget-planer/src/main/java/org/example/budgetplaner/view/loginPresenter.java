package org.example.budgetplaner.view;

import javafx.stage.Stage;
import org.example.budgetplaner.AccountUI;
import org.example.budgetplaner.AccountUI;


public class loginPresenter {
    private loginView loginview;
    private Stage primaryStage;

    public loginPresenter(loginView loginview, Stage primaryStage) {
        this.loginview = loginview;
        this.primaryStage = primaryStage;
    }

    public void LoginPresenter(loginView loginview, Stage primaryStage) {
        this.loginview = loginview;
        this.primaryStage = primaryStage;
        setupListeners();
    }

    private void setupListeners() {
        loginView.getLoginButton().setOnAction(e -> {
            primaryStage.setScene(AccountUI.createAccountScene(primaryStage));
        });
    }
}
