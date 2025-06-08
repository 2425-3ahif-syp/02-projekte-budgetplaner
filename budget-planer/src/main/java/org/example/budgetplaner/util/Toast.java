package org.example.budgetplaner.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;

public class Toast {

    public static void show(Window ownerWindow, String message) {
        Popup popup = new Popup();
        Label label = new Label(message);
        label.setStyle("-fx-background-radius: 5; -fx-background-color: rgba(0,0,0,0.75); -fx-text-fill: white; -fx-padding: 10px;");
        popup.getContent().add(label);
        popup.setAutoHide(true);
        double x = ownerWindow.getX() + ownerWindow.getWidth() / 2 - 100;
        double y = ownerWindow.getY() + ownerWindow.getHeight() - 80;
        popup.show(ownerWindow, x, y);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), ae -> popup.hide()));
        timeline.play();
    }
}
