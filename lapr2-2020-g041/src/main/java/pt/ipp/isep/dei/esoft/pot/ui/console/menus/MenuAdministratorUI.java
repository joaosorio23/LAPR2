package pt.ipp.isep.dei.esoft.pot.ui.console.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.SendEmailAutomaticallyTask;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;
import pt.ipp.isep.dei.esoft.pot.model.Stages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Menu Administrator UI.
 */
public class MenuAdministratorUI implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Register Organization Action.
     *
     * @param actionEvent the Action Event
     */
    @FXML
    public void registerOrganizationAction(ActionEvent actionEvent) {
        Stages.newWindow("/fxml/useCases/RegisterOrganizationWindow.fxml", "Register Organization", false);
    }

    @FXML
    private void SeeDelaysAction(ActionEvent event) {
        Stages.newWindow("/fxml/useCases/AnalyseFreelancersPerformanceDelaysWindow.fxml", "Analyse Freelancers Performance Delays", false);
    }

    @FXML
    private void SeePaymentsAction(ActionEvent event) {
        Stages.newWindow("/fxml/useCases/AnalyseFreelancersPerformancePaymentsWindow.fxml", "Analyse Freelancers Performance Delays", false);
    }

    /**
     * Send Email Action
     *
     * @param event event
     * @throws IOException the IO Exception
     */
    @FXML
    public void SendEmailAction(ActionEvent event) throws IOException {
        SendEmailAutomaticallyTask ctrl = new SendEmailAutomaticallyTask();
        int numberOfFreelancer = ctrl.getFreelancerToSendEmailTo();

        if (numberOfFreelancer == 0) {
            Alerts.createAlert(Alert.AlertType.WARNING, "Warning", "There aren´t freelancers to receive email...").show();
        } else {
            Alerts.createAlert(Alert.AlertType.INFORMATION, "Success", numberOfFreelancer + " freelancer(s) received an email").show();
        }
    }

    /**
     * Exit Action
     *
     * @param actionEvent event
     */
    @FXML
    public void exitAction(ActionEvent actionEvent) {
        Alerts.back(actionEvent, "Do you really want to Log Out?");
        ApplicationPOT.getInstance().doLogout();
    }
}
