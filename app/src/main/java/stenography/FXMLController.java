package stenography;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable{
    @FXML
    private TextField messageField;
    @FXML
    private TextField keyField;
    @FXML
    private TextField outputFilenameField;
    @FXML
    private Button hideButton;
    @FXML
    private Button showButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button returnButton;

    protected File inputFilenameFile;
    protected File outputFilenameFile;
    protected SteganographyCryptography classInstance = new SteganographyCryptography();

    /**
     * Handling hide, show, return, and exit commands
     * @param event button clicks
     * @throws Exception fxmlLoader.load()
     */
    @FXML
    private void handleCommandButtonAction(MouseEvent event) throws Exception {
        if(event.getSource() == hideButton){
            StenographyApp.runScene(hideButton.getScene().getWindow(), "/fxml/encryptionScene.fxml");
        } else if (event.getSource() == showButton){
            StenographyApp.runScene(showButton.getScene().getWindow(), "/fxml/dencryptionScene.fxml");
        } else if (event.getSource() == exitButton){
            Platform.exit();
        } else if (event.getSource() == returnButton) {
            StenographyApp.runScene(returnButton.getScene().getWindow(), "/fxml/sceneOne.fxml");
        }
    }

    /**
     * Choosing source file and result directory
     */
    @FXML
    private void handleInputChooserButtonAction(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        inputFilenameFile = fileChooser.showOpenDialog(stage);
    }

    @FXML
    private void handleOutputChooserButtonAction(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        outputFilenameFile = directoryChooser.showDialog(stage);
    }

    /**
     * Validating submit form of the hide command
     */
    @FXML
    private void handleEncryptionButtonAction() {
        String messageToHide = messageField.getText();
        String password = keyField.getText();
        String outputFilename = outputFilenameField.getText();

        boolean isValid = FormValidator.validateEncryptionForm(messageToHide, password, outputFilename, inputFilenameFile, outputFilenameFile);
        if (isValid) {
            String inputFilePath = inputFilenameFile.getAbsolutePath();
            String outputFilePath = outputFilenameFile.getAbsolutePath() + "/" + outputFilename;
            String[] result = classInstance.hideCommand(inputFilePath, outputFilePath, messageToHide, password);
            AlertsController.toAlert(result[0], result[1], result[2]);
        }
    }

    /**
     * Validating submit form of the show command
     */
    @FXML
    private void handleDecryptionButtonAction() {
        String password = keyField.getText();
        boolean isValid = FormValidator.validateDecryptionForm(password, inputFilenameFile);
        if (isValid) {
            String inputFilePath = inputFilenameFile.getAbsolutePath();
            String[] result = classInstance.showCommand(inputFilePath, password);
            AlertsController.toAlert(result[0], result[1], result[2]);
        }
    }

    /**
     * Handling key ENTER event — when key is pressed the focus is on the parent.
     */
    @FXML
    private void handleMessageKeyAction(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
            messageField.getParent().requestFocus();
        }
    }

    @FXML
    private void handleEncryptionKeyAction(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
            keyField.getParent().requestFocus();
        }
    }

    @FXML
    private void handleOutputFilenameKeyAction(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
            outputFilenameField.getParent().requestFocus();
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
