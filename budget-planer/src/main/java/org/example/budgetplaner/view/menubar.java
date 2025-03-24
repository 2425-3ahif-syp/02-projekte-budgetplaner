package org.example.budgetplaner.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import static org.example.budgetplaner.view.BudgetPlanerController.createBudgetPlanerScene;
import static org.example.budgetplaner.view.LoginController.createAccountScene;


public class menubar {
    public static MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        Menu accountMenu = new Menu("Account");
        Menu ausgabenMenu = new Menu("Ausgaben");
        Menu planungMenu = new Menu("Planung");
        Menu monatsvergleichMenu = new Menu("Monatsvergleich");
        Menu datenimportMenu = new Menu("Datenimport");

        MenuItem accountItem = new MenuItem("Account Übersicht");
        accountItem.setOnAction(e -> primaryStage.setScene(createAccountScene(primaryStage, false)));

        MenuItem budgetPlanerItem = new MenuItem("BudgetPlaner");
        budgetPlanerItem.setOnAction(e -> primaryStage.setScene(createBudgetPlanerScene(primaryStage)));

        MenuItem ausgabenItem = new MenuItem("Ausgaben");
        ausgabenItem.setOnAction(e -> primaryStage.setScene(AusgabenController.createAusgabenScene(primaryStage)));

        accountMenu.getItems().add(accountItem);
        planungMenu.getItems().add(budgetPlanerItem);
        ausgabenMenu.getItems().add(ausgabenItem);


        menuBar.getMenus().addAll(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        menuBar.setStyle("-fx-background-color: grey;");

        return menuBar;
    }
}
