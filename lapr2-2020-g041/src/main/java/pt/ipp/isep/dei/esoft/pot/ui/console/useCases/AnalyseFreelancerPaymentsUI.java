/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.AnalyseFreelancerPaymentsController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;

/**
 * The type Analyse freelancer payments ui.
 *
 * @author Admin
 */
public class AnalyseFreelancerPaymentsUI implements Initializable {

    @FXML
    private ListView<String> lstViewFreePayments;

    private AnalyseFreelancerPaymentsController appController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appController = new AnalyseFreelancerPaymentsController();

        lstViewFreePayments.getItems().addAll(appController.getListStats());

    }

    /**
     * Sort by name action.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void sortByNameAction(ActionEvent actionEvent) {
        lstViewFreePayments.getItems().clear();
        lstViewFreePayments.getItems().addAll(appController.getListStats());
    }

    /**
     * Sort by payments action.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void sortByPaymentsAction(ActionEvent actionEvent) {
        lstViewFreePayments.getItems().clear();
        lstViewFreePayments.getItems().addAll(appController.getFreeSortedByPayment());
    }

    /**
     * Back to menu action.
     *
     * @param actionEvent the action event
     */
    public void BackToMenuAction(ActionEvent actionEvent) {
        Alerts.back(actionEvent, "Do you really want to go back to the Menu?");
    }
}
