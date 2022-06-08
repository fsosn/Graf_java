module graf {
    requires javafx.controls;
    requires javafx.fxml;


    opens graf to javafx.fxml;
    exports graf;
}