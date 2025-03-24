package org.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloController {

    private static Stage primaryStage;

    // Konstruktor, der die Stage entgegennimmt
    public HelloController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Methode zur Erstellung der Hauptszene
    public static Scene createMainScene() {
        Button btnGoToAusgaben = new Button("Zur Ausgaben-Seite");

        // Button-Event, um zur Ausgaben-Seite zu wechseln
        btnGoToAusgaben.setOnAction(e -> showAusgabenPage());

        StackPane mainLayout = new StackPane();
        mainLayout.getChildren().add(btnGoToAusgaben);

        return new Scene(mainLayout, 300, 200);
    }

    // Methode zur Anzeige der Ausgaben-Seite
    private static void showAusgabenPage() {
        Button backButton = new Button("Zurück zur Hauptseite");

        // Button-Event, um zurück zur Hauptseite zu wechseln
        backButton.setOnAction(e -> primaryStage.setScene(createMainScene()));

        StackPane ausgabenLayout = new StackPane();
        ausgabenLayout.getChildren().add(backButton);

        Scene ausgabenScene = new Scene(ausgabenLayout, 300, 200);
        primaryStage.setScene(ausgabenScene);
    }
}
