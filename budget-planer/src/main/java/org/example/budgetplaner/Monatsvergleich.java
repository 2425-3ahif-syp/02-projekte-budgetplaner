package org.example.budgetplaner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.budgetplaner.view.MonatsvergleichController;

public class Monatsvergleich extends Application {
    @Override
    public void start(Stage stage) {
        MonatsvergleichController controller = new MonatsvergleichController();
        Scene scene = controller.createMonatsvergleichScene(stage);
        stage.setTitle("Monatsvergleich");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

