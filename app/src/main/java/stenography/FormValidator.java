package stenography;

import java.io.File;

/**
 * Class for validation submit forms withing show and hide commands
 */
public class FormValidator {
    public static boolean validateEncryptionForm(String messageToHide, String password, String outputFilename, File inputFilenameFile, File outputFilenameFile) {
        if (messageToHide.isEmpty()) {
            AlertsController.toAlert("WARNING", "Message field is empty", "You did not provide message to hide");
            return false;
        } else if (password.isEmpty()) {
            AlertsController.toAlert("WARNING", "Key field is empty", "You did not provide encryption key");
            return false;
        } else if (inputFilenameFile == null) {
            AlertsController.toAlert("WARNING", "Input filename is empty", "You did not provide input filename");
            return false;
        } else if (outputFilenameFile == null) {
            AlertsController.toAlert("WARNING", "Output directory is empty", "You did not provide output directory");
            return false;
        } else if (outputFilename.isEmpty()) {
            AlertsController.toAlert("WARNING", "Output filename is empty", "You did not provide output filename");
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateDecryptionForm(String password, File inputFilenameFile) {
        if (password.isEmpty()) {
            AlertsController.toAlert("WARNING", "Key field is empty", "You did not provide decryption key");
            return false;
        } else if (inputFilenameFile == null) {
            AlertsController.toAlert("WARNING", "Input filename is empty", "You did not provide input filename");
            return false;
        } else {
            return true;
        }
    }
}
