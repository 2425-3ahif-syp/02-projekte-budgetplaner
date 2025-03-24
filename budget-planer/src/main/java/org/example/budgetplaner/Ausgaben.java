package org.example.budgetplaner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import org.example.budgetplaner.view.AusgabenController;

public class Ausgaben extends Application {

    @Override
    public void start(Stage primaryStage) {
        AusgabenController controller = new AusgabenController();
        primaryStage.setTitle("Budgetplaner");

        // Hier wird die UI von AusgabenController erstellt
        StackPane root = new StackPane();
        root.getChildren().add(controller.createUI());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
