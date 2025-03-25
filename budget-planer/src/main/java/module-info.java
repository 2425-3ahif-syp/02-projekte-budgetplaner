module org.example.budgetplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.budgetplaner to javafx.fxml;
    exports org.example.budgetplaner;
}