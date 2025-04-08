module org.example.lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires static lombok;


    opens org.example.lab5 to javafx.fxml;
    exports org.example.lab5;
    exports org.example.lab5.model;
    opens org.example.lab5.model to javafx.fxml;
    exports org.example.lab5.view;
    opens org.example.lab5.view to javafx.fxml;
    exports org.example.lab5.controller;
    opens org.example.lab5.controller to javafx.fxml;
}