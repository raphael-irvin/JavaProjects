module com.example.stockmarketsimulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stockmarketsimulator to javafx.fxml;
    exports com.example.stockmarketsimulator;
}