package org.example.budgetplaner.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class menubar {
    public static MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu accountMenu = new Menu("Account");
        Menu ausgabenMenu = new Menu("Ausgaben");
        Menu planungMenu = new Menu("Planung");
        Menu monatsvergleichMenu = new Menu("Monatsvergleich");
        Menu datenimportMenu = new Menu("Datenimport");


        menuBar.getMenus().addAll(accountMenu, ausgabenMenu, planungMenu, monatsvergleichMenu, datenimportMenu);
        return menuBar;

    }
    public static void openAusgabenFenster() {
        try {
            FXMLLoader loader = new FXMLLoader(Menubar.class.getResource("/ausgaben.fxml"));
            Parent root = loader.load();

            Stage ausgabenStage = new Stage();
            ausgabenStage.setTitle("Ausgaben");
            ausgabenStage.setScene(new Scene(root, 400, 300));
            ausgabenStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}