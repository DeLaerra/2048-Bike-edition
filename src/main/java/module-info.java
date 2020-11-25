module com.rin {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    opens com.rin.controller to javafx.controls, javafx.fxml, org.slf4j;
    exports com.rin.controller;

    opens com.rin to javafx.controls, javafx.fxml, org.slf4j;
    exports com.rin;
}