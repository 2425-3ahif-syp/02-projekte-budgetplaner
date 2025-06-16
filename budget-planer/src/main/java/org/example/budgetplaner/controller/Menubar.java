
package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class Menubar {

    public static BorderPane createMenuBar(Stage primaryStage) {
        Button login = new Button("Account Information");
        Button budgetPlaner = new Button("Budget Planer");
        Button ausgaben = new Button("Ausgaben Übersicht");
        Button monatsVergleich = new Button("Monats Vergleich");
        Button datenimport = new Button("Datenimport");

        List<Button> menuButtons = List.of(login, budgetPlaner, ausgaben, monatsVergleich, datenimport);

        HBox menubar = new HBox();
        menubar.setSpacing(0);
        menubar.setPadding(new Insets(0));
        menubar.setStyle("-fx-background-color: #f8c6cb; -fx-border-color: #f6ddde; -fx-border-width: 0 0 1 0;");
        menubar.setPrefHeight(50);

        for (Button btn : menuButtons) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setMinHeight(50);
            btn.setCursor(Cursor.HAND);
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6a353f; -fx-font-size: 14px; -fx-font-weight: bold;");

            btn.setOnMouseEntered(e -> {
                if (!btn.getStyleClass().contains("active")) {
                    btn.setStyle("-fx-background-color: #f7a1a3; -fx-text-fill: #4b2228; -fx-font-weight: bold;");
                }
            });
            btn.setOnMouseExited(e -> {
                if (!btn.getStyleClass().contains("active")) {
                    btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6a353f; -fx-font-weight: bold;");
                }
            });

            HBox.setHgrow(btn, Priority.ALWAYS);
        }

        Consumer<Button> setActiveButton = (activeBtn) -> {
            for (Button btn : menuButtons) {
                btn.getStyleClass().remove("active");
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6a353f; -fx-font-weight: normal;");
            }
            activeBtn.getStyleClass().add("active");
            activeBtn.setStyle("-fx-background-color: #f697a7; -fx-text-fill: white; -fx-font-weight: bold;");
        };


        login.setOnAction(e -> {
            Scene loginScene = new LoginController().createAccountScene(primaryStage, false);
            primaryStage.setScene(loginScene);
            primaryStage.show();
            setActiveButton.accept(login);
        });

        budgetPlaner.setOnAction(e -> {
            Scene budgetScene = new BudgetPlanerController().createBudgetPlanerScene(primaryStage);
            primaryStage.setScene(budgetScene);
            primaryStage.show();
            setActiveButton.accept(budgetPlaner);
        });

        ausgaben.setOnAction(e -> {
            Scene ausgabenScene = new AusgabenController().createAusgabenScene(primaryStage);
            primaryStage.setScene(ausgabenScene);
            primaryStage.show();
            setActiveButton.accept(ausgaben);
        });

        monatsVergleich.setOnAction(e -> {
            Scene vergleichScene = new MonatsvergleichController().createMonatsvergleichScene(primaryStage);
            primaryStage.setScene(vergleichScene);
            primaryStage.show();
            setActiveButton.accept(monatsVergleich);
        });

        datenimport.setOnAction(e -> {
            Parent datenimportRoot = new DatenimportController().createContent(primaryStage);
            Scene datenimportScene = new Scene(datenimportRoot, 1000, 600);
            datenimportScene.getStylesheets().add(Menubar.class.getResource("/css/index.css").toExternalForm());
            primaryStage.setScene(datenimportScene);
            primaryStage.show();
            setActiveButton.accept(datenimport);
        });

        menubar.getChildren().addAll(menuButtons);

        BorderPane root = new BorderPane();
        root.setTop(menubar);

        return root;
    }


}




/*
package org.example.budgetplaner.controller;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.sql.SQLException;
import static org.example.budgetplaner.controller.BudgetPlanerController.createBudgetPlanerScene;
import static org.example.budgetplaner.controller.LoginController.createAccountScene;

public class Menubar {

    private static void resetMenuStyles(MenuItem... menuItems) {
        for (MenuItem menuItem : menuItems) {
            menuItem.setStyle("");
        }
    }

    public static MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

        MenuItem accountMenuItem = new MenuItem("Account Übersicht");
        MenuItem planungMenuItem = new MenuItem("Planung Übersicht");
        MenuItem ausgabenMenuItem = new MenuItem("Ausgaben Übersicht");
        MenuItem monatsvergleichMenuItem = new MenuItem("Monatsvergleich Übersicht");
        MenuItem datenimportMenuItem = new MenuItem("Datenimport Übersicht");

        accountMenuItem.setOnAction(e -> {
            primaryStage.setScene(createAccountScene(primaryStage, false));
            resetMenuStyles(accountMenuItem, ausgabenMenuItem, planungMenuItem, monatsvergleichMenuItem, datenimportMenuItem);
            accountMenuItem.setStyle("-fx-font-weight: bold;");
        });
        ausgabenMenuItem.setOnAction(e -> {
            try {
                primaryStage.setScene(AusgabenController.createAusgabenScene(primaryStage));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            resetMenuStyles(accountMenuItem, ausgabenMenuItem, planungMenuItem, monatsvergleichMenuItem, datenimportMenuItem);
            ausgabenMenuItem.setStyle("-fx-font-weight: bold;");
        });

        planungMenuItem.setOnAction(e -> {
            primaryStage.setScene(createBudgetPlanerScene(primaryStage));
            resetMenuStyles(accountMenuItem, ausgabenMenuItem, planungMenuItem, monatsvergleichMenuItem, datenimportMenuItem);
            planungMenuItem.setStyle("-fx-font-weight: bold;");
        });

        monatsvergleichMenuItem.setOnAction(e -> {
            primaryStage.setScene(MonatsvergleichController.createMonatsvergleichScene(primaryStage));
            resetMenuStyles(accountMenuItem, ausgabenMenuItem, planungMenuItem, monatsvergleichMenuItem, datenimportMenuItem);
            monatsvergleichMenuItem.setStyle("-fx-font-weight: bold;");
        });

        menuBar.getMenus().addAll(
                new Menu("Account", null, accountMenuItem),
                new Menu("Ausgaben", null, ausgabenMenuItem),
                new Menu("Planung", null, planungMenuItem),
                new Menu("Monatsvergleich", null, monatsvergleichMenuItem),
                new Menu("Daten Import", null, datenimportMenuItem)
        );
        menuBar.setStyle("-fx-background-color: grey; -fx-font-size: 16px; -fx-padding: 10px;");

        return menuBar;
    }
}

 */
