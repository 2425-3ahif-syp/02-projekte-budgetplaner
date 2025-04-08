
package org.example.budgetplaner.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

import static org.example.budgetplaner.controller.BudgetPlanerController.createBudgetPlanerScene;
import static org.example.budgetplaner.controller.LoginController.createAccountScene;

public class Menubar {


    private static void resetMenuStyles(Menu... menus) {
        for (Menu menu : menus) {
            menu.setStyle("");
        }
    }

    public static MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-font-size: 10px; -fx-padding: 10px; -fx-spacing: 50px; -fx-font-weight: bold;");

        Menu accountMenu = new Menu("Account");
        Menu ausgabenMenu = new Menu("Ausgaben");
        Menu planungMenu = new Menu("Planung");
        Menu monatsvergleichMenu = new Menu("Monatsvergleich");
        Menu datenimportMenu = new Menu("Datenimport");

        MenuItem accountItem = new MenuItem("Account Übersicht");
        accountItem.setOnAction(e -> {
            primaryStage.setScene(createAccountScene(primaryStage, false));
            resetMenuStyles(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        });

        MenuItem budgetPlanerItem = new MenuItem("BudgetPlaner");
        budgetPlanerItem.setOnAction(e -> {
            primaryStage.setScene(createBudgetPlanerScene(primaryStage));
            resetMenuStyles(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        });

        MenuItem ausgabenItem = new MenuItem("Ausgaben");
        ausgabenItem.setOnAction(e -> {
            try {
                primaryStage.setScene(AusgabenController.createAusgabenScene(primaryStage));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            resetMenuStyles(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        });

        MenuItem monatsItem = new MenuItem("Monatsvergleich");
        monatsItem.setOnAction(e -> {
            primaryStage.setScene(MonatsvergleichController.createMonatsvergleichScene(primaryStage));
            resetMenuStyles(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        });

        accountMenu.getItems().add(accountItem);
        planungMenu.getItems().add(budgetPlanerItem);
        ausgabenMenu.getItems().add(ausgabenItem);
        monatsvergleichMenu.getItems().add(monatsItem);

        menuBar.getMenus().addAll(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        menuBar.setStyle("-fx-background-color: grey; -fx-font-size: 16px; -fx-padding: 10px; -fx-spacing: 100px; -fx-font-weight: bold;");

        return menuBar;
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
