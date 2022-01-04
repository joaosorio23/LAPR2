package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.SetPaymentDayController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Set payment day ui.
 *
 * @author Berkelios
 */
public class SetPaymentDayUI implements Initializable {

    private SetPaymentDayController appController;

    @FXML
    private TextField txtDay;
    @FXML
    private TextField txtHour;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.appController = new SetPaymentDayController();
    }

    @FXML
    private void setPaymentDayAction(ActionEvent event) {
        if (appController.convertToInt(this.txtDay.getText(), this.txtHour.getText())) {
            if (showDataAndConfirmation()) {
                if (appController.setPaymentSchedule()) {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Success...", "Successfully registered the Payment Day!").show();
                } else {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Error...", "Unsuccessfully registered the Payment Day!").show();
                }
                clearData();
            }
        }
    }

    private boolean showDataAndConfirmation() {
        return Alerts.showDataAndConfirmation(("Day: " + this.txtDay.getText().trim() + " Hour: " + this.txtHour.getText().trim()));
    }

    @FXML
    private void backToMenuAction(ActionEvent event) {
        Alerts.back(event, "Do you really want to go back to the Menu?");
    }

    /**
     * Clear data.
     */
    public void clearData() {
        txtDay.clear();
        txtHour.clear();
    }
}
