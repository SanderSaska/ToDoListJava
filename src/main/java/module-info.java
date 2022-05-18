module com.example.ryhmatoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ryhmatoo to javafx.fxml;
    exports com.example.ryhmatoo;
}