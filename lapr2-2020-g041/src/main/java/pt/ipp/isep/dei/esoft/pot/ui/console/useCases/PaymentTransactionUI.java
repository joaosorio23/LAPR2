/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.PaymentTransactionController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;
import pt.ipp.isep.dei.esoft.pot.model.Stages;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Berkelios
 */
public class PaymentTransactionUI implements Initializable {

    @FXML
    private TextField delay;
    @FXML
    private TextField description;
    @FXML
    private TextField ID;
    @FXML
    private ComboBox<String> cmbFreelancers;
    @FXML
    private ComboBox<String> cmbTasks;
    @FXML
    private DatePicker date;

    private PaymentTransactionController appController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.appController = new PaymentTransactionController();
        if (!appController.getFreelancersID().isEmpty()) {
            this.cmbFreelancers.getSelectionModel().select(appController.getFreelancersID().get(0));
            this.cmbFreelancers.getItems().setAll(appController.getFreelancersID());
        }
        if (!appController.getTaskID().isEmpty()) {
            this.cmbTasks.getSelectionModel().select(appController.getTaskID().get(0));
            this.cmbTasks.getItems().setAll(appController.getTaskID());
        }
    }

    @FXML
    private void CreateTransactionAction(ActionEvent event) {
        if (this.cmbTasks.getValue() == null) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", "Create one Task to Continue!").show();
        } else {
            if (this.cmbFreelancers.getValue() == null) {
                Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", "Create one Freelancer to Continue!").show();
            } else {
                if (appController.setData(this.date.getValue(), this.delay.getText().trim(), this.description.getText().trim(), appController.getFreelancerByID(this.cmbFreelancers.getValue()), appController.getTaskByID(this.cmbTasks.getValue()))) {
                    if (appController.newPaymentTransaction(ID.getText().trim())) {
                        if (showDataAndConfirmation()) {
                            if (appController.registerPaymentTransaction()) {
                                Alerts.createAlert(Alert.AlertType.INFORMATION, "Success...", "Successfully registered Transaction!").show();
                            } else {
                                Alerts.createAlert(Alert.AlertType.INFORMATION, "Error...", "Error registering the Transaction!").show();
                            }
                            clearData();
                        }
                    }
                }
            }
        }
    }

    private boolean showDataAndConfirmation() {
        return Alerts.showDataAndConfirmation(appController.getPaymentTransactionString() + appController.getValueString());
    }

    /**
     * Create freelancer action.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void createFreelancerAction(ActionEvent actionEvent) {
        if (Alerts.backTF(actionEvent, "Do you really want to create a new Freelancer?")) {
            Stages.newWindow("/fxml/useCases/RegisterFreelancerWindow.fxml", "Register Freelancer", false);
        }
    }

    /**
     * Create task action.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void CreateTaskAction(ActionEvent actionEvent) {
        if (Alerts.backTF(actionEvent, "Do you really want to create a new Task?")) {
            Stages.newWindow("/fxml/useCases/CreateTaskWindow.fxml", "Create Task", false);
        }
    }

    @FXML
    private void BackToMenuAction(ActionEvent actionEvent) {
        Alerts.back(actionEvent, "Do you really want to go back to the Menu?");
    }

    /**
     * Clear data.
     */
    public void clearData() {
        this.delay.clear();
        this.description.clear();
        this.ID.clear();
        this.date.getEditor().clear();
        if (!appController.getFreelancersID().isEmpty()) {
            this.cmbFreelancers.getSelectionModel().select(appController.getFreelancersID().get(0));
            this.cmbFreelancers.getItems().setAll(appController.getFreelancersID());
        } else {
            this.cmbFreelancers.getItems().clear();
        }
        if (!appController.getTaskID().isEmpty()) {
            this.cmbTasks.getSelectionModel().select(appController.getTaskID().get(0));
            this.cmbTasks.getItems().setAll(appController.getTaskID());
        } else {
            this.cmbTasks.getItems().clear();
        }
    }
}
