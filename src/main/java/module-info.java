module com.example._50zo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example._50zo to javafx.fxml;
    opens com.example._50zo.controller to javafx.fxml;
    exports com.example._50zo;
    exports  com.example._50zo.controller to javafx.fxml;
}