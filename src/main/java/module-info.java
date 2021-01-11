module org.fdev {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
//    requires java.dekstop;
    requires javafx.swing;

    opens org.fdev.controller to javafx.fxml;
    exports org.fdev;


}