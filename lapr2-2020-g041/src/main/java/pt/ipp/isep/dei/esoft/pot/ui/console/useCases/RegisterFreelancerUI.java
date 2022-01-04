package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.RegisterFreelancerController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;
import pt.ipp.isep.dei.esoft.pot.model.enun.Country;
import pt.ipp.isep.dei.esoft.pot.model.enun.LevelOfExpertise;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Register Freelancer UI.
 *
 * @author Berkelios
 */
public class RegisterFreelancerUI implements Initializable {

    /**
     * The IBAN initial.
     */
    @FXML
    public Label initialLabel;
    /**
     * The App Controller.
     */
    private RegisterFreelancerController appController;
    /**
     * The Levels Of Expertise.
     */
    @FXML
    private ComboBox<LevelOfExpertise> cmbLevels;
    /**
     * The ID.
     */
    @FXML
    private TextField id;
    /**
     * The Name.
     */
    @FXML
    private TextField name;
    /**
     * The Email.
     */
    @FXML
    private TextField email;
    /**
     * The NIF.
     */
    @FXML
    private TextField nif;
    /**
     * The IBAN.
     */
    @FXML
    private TextField iban;
    /**
     * The Address.
     */
    @FXML
    private TextField address;
    /**
     * The Countries.
     */
    @FXML
    private ComboBox<Country> cmbCountries;

    /**
     * Initialize.
     *
     * @param location local
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.appController = new RegisterFreelancerController();
        this.cmbLevels.getSelectionModel().select(LevelOfExpertise.JUNIOR);
        this.cmbLevels.getItems().setAll(LevelOfExpertise.values());
        this.cmbCountries.getSelectionModel().select(Country.PT);
        this.cmbCountries.getItems().setAll(Country.values());
        this.initialLabel.setText(" " + this.cmbCountries.getValue().initialIBAN);
    }

    /**
     * Register Freelancer Action.
     */
    @FXML
    private void registerFreelancerAction() {
        if (appController.newFreelancer(this.id.getText().trim(), this.name.getText().trim(), this.email.getText().trim(), this.nif.getText().trim(), this.iban.getText().trim(), this.address.getText().trim(), this.cmbCountries.getValue(), this.cmbLevels.getValue())) {
            if (showDataAndConfirmation()) {
                if (appController.registerFreelancer()) {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Success...", "Successfully registered Freelancer!").show();
                } else {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Error...", "Error registering the Freelancer!").show();
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
        return Alerts.showDataAndConfirmation(appController.getFreelancerString());
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
        id.clear();
        name.clear();
        email.clear();
        nif.clear();
        iban.clear();
        address.clear();
        this.cmbLevels.getSelectionModel().select(LevelOfExpertise.JUNIOR);
        this.cmbCountries.getSelectionModel().select(Country.PT);
        this.initialLabel.setText(" " + this.cmbCountries.getValue().initialIBAN);
    }

    /**
     * Selection Change Committed.
     */
    @FXML
    private void cmbItems_SelectionChangeCommitted() {
        this.initialLabel.setText(" " + this.cmbCountries.getValue().initialIBAN);
    }
}
