
package org.example.meineseite;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.meineseite.HelloController;

public class Ausgaben extends Application {
    @Override
    public void start(Stage stage) {
        HelloController controller = new HelloController();
        VBox root = controller.createUI();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Budget Übersicht");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
