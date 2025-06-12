package org.example.budgetplaner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.budgetplaner.controller.DatenimportController;
import org.example.budgetplaner.controller.LoginController;

import static javafx.application.Application.launch;


public class DatenimportView extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budgetplaner");

        DatenimportController controller = new DatenimportController();
        Scene scene = new Scene(controller.createContent(primaryStage), 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/css/index.css").toExternalForm());


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
