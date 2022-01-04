package pt.ipp.isep.dei.esoft.pot.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;
import pt.ipp.isep.dei.esoft.pot.model.Constants;

import java.io.IOException;

/**
 * The type Main APP.
 *
 * @author Berkelios
 */
public class MainApp extends Application {

    /**
     * The Entry point of Application.
     *
     * @param args the Input Arguments
     */
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start.
     *
     * @param stage the Stage
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginWindow.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("Login T4J");
            stage.setScene(scene);

            stage.setOnCloseRequest(event -> {
                Alert alert = Alerts.createAlert(Alert.AlertType.CONFIRMATION, "Confirmation of closing action...", "Do you really want to close the application?");

                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("YES");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("NO");

                if (alert.showAndWait().get() == ButtonType.CANCEL) {
                    event.consume();
                } else {
                    ApplicationPOT.getInstance().saveInformation();
                    System.exit(0);
                }
            });
            stage.setResizable(false);
            stage.getIcons().add(new Image(Constants.ICON_ISEP));
            stage.show();
        } catch (IOException ex) {
            Alerts.createAlert(Alert.AlertType.CONFIRMATION, "Problems with application startup!", ex.getMessage()).show();
        }
    }
}
