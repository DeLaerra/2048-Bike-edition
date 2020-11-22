module com.rin {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    opens com.rin.controller to javafx.fxml;
    exports com.rin.controller;

    opens com.rin to javafx.fxml;
    exports com.rin;
}