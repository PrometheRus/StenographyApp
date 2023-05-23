package stenography;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

import java.util.Objects;

public class AlertsController {

    public static void toAlert(String type, String header, String context) {

        Alert alert = new Alert(Alert.AlertType.valueOf(type));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(AlertsController.class.getResource("/css/cssStyles.css")).toExternalForm());
        dialogPane.getStyleClass().add("myAlerts");

        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
