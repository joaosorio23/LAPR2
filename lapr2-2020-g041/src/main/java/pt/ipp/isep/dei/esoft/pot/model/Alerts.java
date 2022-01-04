package pt.ipp.isep.dei.esoft.pot.model;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The type Alerts.
 *
 * @author Berkelios
 */
public class Alerts {

    /**
     * Create Alert.
     *
     * @param typeAlert the Alert's Type
     * @param header    the Header
     * @param message   the Message
     * @return the Alert
     */
    public static Alert createAlert(Alert.AlertType typeAlert, String header, String message) {

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(typeAlert);

        alert.setTitle("T4J");
        alert.setHeaderText(header);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Constants.ICON_ISEP));

        return alert;
    }

    /**
     * Back Alert.
     *
     * @param event   the Event
     * @param message the Message
     */
    public static void back(ActionEvent event, String message) {
        Alert alerta = Alerts.createAlert(Alert.AlertType.CONFIRMATION, "Action confirmation.", message);

        ((Button) alerta.getDialogPane().lookupButton(ButtonType.OK)).setText("YES");
        ((Button) alerta.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("NO");

        if (alerta.showAndWait().get() == ButtonType.CANCEL) {
            event.consume();
        } else {
            ((Node) event.getSource()).getScene().getWindow().hide();

        }
    }

    /**
     * Show Data and Confirmation.
     *
     * @param message the Message
     * @return boolean boolean
     */
    public static boolean showDataAndConfirmation(String message) {
        Alert alerts = Alerts.createAlert(Alert.AlertType.CONFIRMATION, "Confirmation of data...", message);

        ((Button) alerts.getDialogPane().lookupButton(ButtonType.OK)).setText("YES");
        ((Button) alerts.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("NO");

        return alerts.showAndWait().get() == ButtonType.OK;
    }

    /**
     * BackTF.
     *
     * @param event   the Event
     * @param message the Message
     * @return boolean boolean
     */
    public static boolean backTF(ActionEvent event, String message) {
        Alert alerta = Alerts.createAlert(Alert.AlertType.CONFIRMATION, "Action confirmation.", message);

        ((Button) alerta.getDialogPane().lookupButton(ButtonType.OK)).setText("YES");
        ((Button) alerta.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("NO");

        if (alerta.showAndWait().get() == ButtonType.CANCEL) {
            event.consume();
            return false;
        } else {
            ((Node) event.getSource()).getScene().getWindow().hide();
            return true;
        }
    }
}
