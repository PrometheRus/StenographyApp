module Stenography.StenographyApp.main {
    requires java.desktop;
    requires kotlin.stdlib;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens stenography to javafx.fxml;
    exports stenography;

}