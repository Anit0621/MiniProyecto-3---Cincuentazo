module com.example._50zo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example._50zo to javafx.fxml;
    exports com.example._50zo;
}