package org.example.budgetplaner.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.budgetplaner.databasepack.database.UserRepository;
import org.example.budgetplaner.model.UserModel;

import java.io.File;

import static org.example.budgetplaner.controller.Menubar.createMenuBar;

public class LoginController {

    private static UserModel currentUser;

    public static VBox createLoginView(Stage primaryStage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));
        loginLayout.getStyleClass().add("main-pane");

        Label userLabel = new Label("Benutzername:");
        userLabel.getStyleClass().add("dragdrop-title");

        TextField userField = new TextField();
        userField.getStyleClass().add("text-field");

        Label passLabel = new Label("Passwort:");
        passLabel.getStyleClass().add("dragdrop-title");

        PasswordField passField = new PasswordField();
        passField.getStyleClass().add("password-field");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("primary-button");

        loginButton.setOnAction(e -> {
            UserRepository userRepository = new UserRepository();
            UserModel user = userRepository.findByNameAndPassword(userField.getText(), passField.getText());

            if (user != null) {
                currentUser = user;
                primaryStage.setScene(createAccountScene(primaryStage, false));
            } else {
                errorLabel.setText("Falscher Benutzername oder Passwort!");
            }
        });

        loginLayout.getChildren().addAll(userLabel, userField, passLabel, passField, loginButton, errorLabel);
        return loginLayout;
    }

    public static Scene createAccountScene(Stage primaryStage, boolean editMode) {
        BorderPane menuBar = createMenuBar(primaryStage);

        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(40));
        formGrid.setVgap(15);
        formGrid.setHgap(10);
        formGrid.getStyleClass().add("manual-pane");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        formGrid.getColumnConstraints().addAll(col1, col2);

        // Felder
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-font-weight: bold;");

        TextField nameField = new TextField(currentUser.getName());
        nameField.setEditable(editMode);
        nameField.getStyleClass().add("text-field");

        Label geburtstagLabel = new Label("Geburtstag:");
        geburtstagLabel.setStyle("-fx-font-weight: bold;");

        TextField geburtstagField = new TextField(currentUser.getGeburtstag());
        geburtstagField.setEditable(editMode);
        geburtstagField.getStyleClass().add("text-field");

        Label emailLabel = new Label("E-Mail:");
        emailLabel.setStyle("-fx-font-weight: bold;");

        TextField emailField = new TextField(currentUser.getEmail());
        emailField.setEditable(editMode);
        emailField.getStyleClass().add("text-field");

        Label passwortLabel = new Label("Passwort:");
        passwortLabel.setStyle("-fx-font-weight: bold;");

        PasswordField passwortField = new PasswordField();
        passwortField.setText(currentUser.getPasswort());
        passwortField.setEditable(editMode);
        passwortField.getStyleClass().add("password-field");

        // WährungsradioButtons (optional)
        ToggleGroup currencyGroup = new ToggleGroup();
        RadioButton euroButton = new RadioButton("€");
        RadioButton dollarButton = new RadioButton("$");
        euroButton.setToggleGroup(currencyGroup);
        dollarButton.setToggleGroup(currencyGroup);
        euroButton.setSelected(true);
        euroButton.setDisable(!editMode);
        dollarButton.setDisable(!editMode);

        HBox currencyBox = new HBox(10, euroButton, dollarButton);

        Button actionButton = new Button();

        if (editMode) {
            actionButton.setText("Speichern");
            actionButton.setOnAction(e -> {
                currentUser.setName(nameField.getText());
                currentUser.setGeburtstag(geburtstagField.getText());
                currentUser.setEmail(emailField.getText());
                currentUser.setPasswort(passwortField.getText());

                new UserRepository().saveOrUpdateUser(
                        currentUser.getName(),
                        currentUser.getGeburtstag(),
                        currentUser.getEmail(),
                        currentUser.getPasswort(),
                        currentUser.getProfilBildPfad()
                );

                primaryStage.setScene(createAccountScene(primaryStage, false));
            });

        } else {
            actionButton.setText("Bearbeiten");
            actionButton.setOnAction(e -> primaryStage.setScene(createAccountScene(primaryStage, true)));
        }

        actionButton.getStyleClass().add("primary-button");

        // Grid befüllen
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(geburtstagLabel, 0, 1);
        formGrid.add(geburtstagField, 1, 1);
        formGrid.add(emailLabel, 0, 2);
        formGrid.add(emailField, 1, 2);
        formGrid.add(passwortLabel, 0, 3);
        formGrid.add(passwortField, 1, 3);
        formGrid.add(currencyBox, 1, 4);
        formGrid.add(actionButton, 1, 5);

        // Profilbild
        Image image = (currentUser.getProfilBildPfad() != null)
                ? new Image(currentUser.getProfilBildPfad(), true)
                : new Image("file:images/profile.jpg", true);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(false);

        Circle clip = new Circle(120, 110, 110);
        imageView.setClip(clip);

        VBox imageBox = new VBox(10, imageView);
        imageBox.setAlignment(Pos.TOP_CENTER);
        imageBox.setPadding(new Insets(40, 20, 20, 30));

        if (editMode) {
            Button changePicButton = new Button("Profilbild ändern");
            changePicButton.getStyleClass().add("primary-button");
            changePicButton.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Profilbild auswählen");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Bilddateien", "*.png", "*.jpg", "*.jpeg", "*.gif")
                );
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    String newPath = selectedFile.toURI().toString();
                    currentUser.setProfilBildPfad(newPath);
                    imageView.setImage(new Image(newPath, true));
                }
            });
            imageBox.getChildren().add(changePicButton);
        }

        HBox contentBox = new HBox(150, formGrid, imageBox);
        contentBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(contentBox);
        root.getStyleClass().add("manual-pane");

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(LoginController.class.getResource("/css/index.css").toExternalForm());

        return scene;
    }

}
