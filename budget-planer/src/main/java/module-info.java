module org.example.budgetplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.example.budgetplaner to javafx.fxml;
    exports org.example.budgetplaner;
}