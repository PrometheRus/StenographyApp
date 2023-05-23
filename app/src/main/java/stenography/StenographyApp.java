package stenography;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class StenographyApp extends Application {
    static double width = Screen.getPrimary().getBounds().getWidth() / 2;
    static double height = Screen.getPrimary().getBounds().getHeight() / 2;

    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage.setTitle("Stenography");
        FXMLLoader fxmlLoader = new FXMLLoader(StenographyApp.class.getResource("/fxml/sceneOne.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setScene(scene);
        stage.show();
    }

    public static void runScene(Window window, String fxml) throws IOException {
        Stage runnableStage = (Stage) window;
        FXMLLoader fxmlLoader = new FXMLLoader(StenographyApp.class.getResource(fxml));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, StenographyApp.width, StenographyApp.height);
        runnableStage.setScene(scene);
        runnableStage.show();
    }
}
