package org.example.budgetplaner.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

import static org.example.budgetplaner.controller.AusgabenController.createAusgabenScene;
import static org.example.budgetplaner.controller.BudgetPlanerController.createBudgetPlanerScene;
import static org.example.budgetplaner.controller.LoginController.createAccountScene;

public class Menubar {

    // Methode zum Zurücksetzen aller Menü-Stile
    private static void resetMenuStyles(Menu... menus) {
        for (Menu menu : menus) {
            menu.setStyle(""); // Zurücksetzen auf Standard
        }
    }

    public static MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        Menu accountMenu = new Menu("Account");
        Menu ausgabenMenu = new Menu("Ausgaben");
        Menu planungMenu = new Menu("Planung");
        Menu monatsvergleichMenu = new Menu("Monatsvergleich");
        Menu datenimportMenu = new Menu("Datenimport");

        MenuItem accountItem = new MenuItem("Account Übersicht");
        accountItem.setOnAction(e -> {
            primaryStage.setScene(createAccountScene(primaryStage, false));
            resetMenuStyles(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
            accountMenu.setStyle("-fx-font-weight: bold;");
        });

        MenuItem budgetPlanerItem = new MenuItem("BudgetPlaner");
        budgetPlanerItem.setOnAction(e -> {
            primaryStage.setScene(createBudgetPlanerScene(primaryStage));
            resetMenuStyles(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
            planungMenu.setStyle("-fx-font-weight: bold;");
        });

        MenuItem ausgabenItem = new MenuItem("Ausgaben");
        ausgabenItem.setOnAction(e -> {
            try {
                primaryStage.setScene(createAusgabenScene(primaryStage));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        MenuItem monatsItem = new MenuItem("Monatsvergleich");
        monatsItem.setOnAction(e -> {
            primaryStage.setScene(MonatsvergleichController.createMonatsvergleichScene(primaryStage));
            resetMenuStyles(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
            monatsvergleichMenu.setStyle("-fx-font-weight: bold;");
        });

        accountMenu.getItems().add(accountItem);
        planungMenu.getItems().add(budgetPlanerItem);
        ausgabenMenu.getItems().add(ausgabenItem);
        monatsvergleichMenu.getItems().add(monatsItem);

        menuBar.getMenus().addAll(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        menuBar.setStyle("-fx-background-color: grey;");

        return menuBar;
    }
}
