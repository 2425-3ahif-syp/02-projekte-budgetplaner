module org.example.bufgetplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.bufgetplanner to javafx.fxml;
    exports org.example.bufgetplanner;
}