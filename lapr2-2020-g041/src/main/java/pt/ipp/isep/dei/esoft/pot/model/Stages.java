package pt.ipp.isep.dei.esoft.pot.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;

import java.io.IOException;

/**
 * The type Stages.
 *
 * @author Berkelios
 */
public class Stages {

    /**
     * New Window.
     *
     * @param filePath the File Path
     * @param title    the Title
     * @param doLogout the Do Logout (True or False)
     */
    public static void newWindow(String filePath, String title, boolean doLogout) {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            FXMLLoader loader = new FXMLLoader(Stages.class.getResource(filePath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Constants.ICON_ISEP));
            stage.setOnCloseRequest(event -> {
                Alert alerta = Alerts.createAlert(Alert.AlertType.CONFIRMATION, "Confirmation of closing action...", "Do you really want to close?");

                ((Button) alerta.getDialogPane().lookupButton(ButtonType.OK)).setText("YES");
                ((Button) alerta.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("NO");

                if (alerta.showAndWait().get() == ButtonType.CANCEL) {
                    event.consume();
                } else {
                    if (doLogout) {
                        ApplicationPOT.getInstance().doLogout();
                    }
                }
            });
            stage.show();
        } catch (IOException ex) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }
}
