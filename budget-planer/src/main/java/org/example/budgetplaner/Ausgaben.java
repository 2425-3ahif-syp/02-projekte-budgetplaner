
package org.example.budgetplaner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.budgetplaner.controller.AusgabenController;

import java.sql.SQLException;


public class Ausgaben extends Application {
    @Override
    public void start(Stage stage) throws SQLException {
        AusgabenController controller = new AusgabenController();
        VBox root = controller.createUI(stage);
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Budget Übersicht");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
