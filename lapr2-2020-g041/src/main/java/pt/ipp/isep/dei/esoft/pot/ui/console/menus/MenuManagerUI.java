package pt.ipp.isep.dei.esoft.pot.ui.console.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;
import pt.ipp.isep.dei.esoft.pot.model.Stages;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Menu Manager UI.
 */
public class MenuManagerUI implements Initializable {

    /**
     * The Organization label.
     */
    @FXML
    public Label orgLabel;
    /**
     * The Name label.
     */
    @FXML
    public Label nameLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orgLabel.setText("Organization: " + ApplicationPOT.getInstance().getPlatform().getOrganizationsRegister().getOrganizationByUserEmail((ApplicationPOT.getInstance().getCurrentSession().getUserEmail())).getDesignation());
        nameLabel.setText("Name: " + ApplicationPOT.getInstance().getCurrentSession().getUserName());
    }

    /**
     * Set Payment Day Action
     *
     * @param event the event
     */
    @FXML
    private void setPaymentDayAction(ActionEvent event) {
        Stages.newWindow("/fxml/useCases/SetPaymentDayWindow.fxml", "Set Payment Day", false);
    }

    /**
     * Freelancers Payments Action.
     *
     * @param event the event
     */
    @FXML
    public void FreelancersPaymentsAction(ActionEvent event) {
        Stages.newWindow("/fxml/useCases/AnalyseFreelancerPaymentsWindow.fxml", "Analyse Freelancer Payments Statistics", false);
    }

    /**
     * Tasks Stats Action.
     *
     * @param event the event
     */
    @FXML
    public void TasksStatsAction(ActionEvent event) {
        Stages.newWindow("/fxml/useCases/AnalyseTasksStatisticsWindow.fxml", "Analyse Taks Statistics", false);
    }

    /**
     * Exit Action.
     *
     * @param actionEvent the Action Event
     */
    public void exitAction(ActionEvent actionEvent) {
        Alerts.back(actionEvent, "Do you really want to Log Out?");
        ApplicationPOT.getInstance().doLogout();
    }
}
