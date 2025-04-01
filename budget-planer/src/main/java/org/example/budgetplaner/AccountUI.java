package org.example.budgetplaner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.budgetplaner.view.AusgabenController;
import org.example.budgetplaner.view.LoginController;



public class AccountUI extends Application {
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Account Verwaltung");

            LoginController loginView = new LoginController();
            Scene loginScene = new Scene(loginView.createLoginView(primaryStage), 400, 300);
            primaryStage.setScene(loginScene);

            primaryStage.show();

        }


    public static void main(String[] args) {
        launch(args);
    }
}

