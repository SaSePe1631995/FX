module com.example.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;


    opens com.example.fx to javafx.fxml;
    exports com.example.fx;
    exports com.example.fx.controllers;
    opens com.example.fx.controllers to javafx.fxml;
}