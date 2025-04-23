package org.example.budgetplaner;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.budgetplaner.controller.DatenimportController;


public class DatenimportView {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Datenimport");
        DatenimportController controller = new DatenimportController();
        
    }
}
