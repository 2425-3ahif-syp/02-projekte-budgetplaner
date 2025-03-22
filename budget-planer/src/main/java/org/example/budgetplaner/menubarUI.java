package org.example.budgetplaner;

import javafx.application.Application;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.example.budgetplaner.view.menubar;

public class menubarUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Account Verwaltung");

        // Menüleiste
        MenuBar menuBar = menubar.createMenuBar();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
