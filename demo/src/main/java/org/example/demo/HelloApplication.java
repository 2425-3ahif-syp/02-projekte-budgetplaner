package org.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.demo.HelloController;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Erstellen des Controllers
        HelloController controller = new HelloController(primaryStage);

        // Erstellen und Setzen der ersten Szene
        Scene mainScene = HelloController.createMainScene();
        primaryStage.setTitle("Hauptseite");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
