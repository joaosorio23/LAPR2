package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.RegisterOrganizationController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Register Organization UI.
 *
 * @author Berkelios
 */
public class RegisterOrganizationUI implements Initializable {

    /**
     * The Designation.
     */
    @FXML
    public TextField desig;
    /**
     * The Manager's Name.
     */
    @FXML
    public TextField nameM;
    /**
     * The Manager's Email.
     */
    @FXML
    public TextField emailM;
    /**
     * The Collaborator's Name.
     */
    @FXML
    public TextField nameC;
    /**
     * The Collaborator's Email.
     */
    @FXML
    public TextField emailC;
    /**
     * The App Controller.
     */
    private RegisterOrganizationController appController;

    /**
     * Initialize.
     *
     * @param location local
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.appController = new RegisterOrganizationController();
    }

    /**
     * Register Freelancer Action.
     *
     * @throws IOException the IO Exception
     */
    @FXML
    private void registerFreelancerAction() throws IOException {
        if (appController.newOrganization(this.desig.getText().trim(), this.nameM.getText().trim(), this.emailM.getText().trim(), this.nameC.getText().trim(), this.emailC.getText().trim())) {
            if (showDataAndConfirmation()) {
                if (appController.registerOrganization()) {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Success...", "Successfully registered Organization!").show();
                } else {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Error...", "Error registering the Organization!").show();
                }
                clearData();
            }
        }
    }

    /**
     * Show Data And Confirmation.
     *
     * @return the Option
     */
    private boolean showDataAndConfirmation() {
        return Alerts.showDataAndConfirmation(appController.getOrganizationString());
    }

    /**
     * Back To Menu Action.
     *
     * @param event the Event
     */
    @FXML
    private void backToMenuAction(ActionEvent event) {
        Alerts.back(event, "Do you really want to go back to the Menu?");
    }

    /**
     * Clear data.
     */
    public void clearData() {
        this.desig.clear();
        this.nameM.clear();
        this.emailM.clear();
        this.nameC.clear();
        this.emailC.clear();
    }
}
