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
 * The type Menu Collaborator Organization UI.
 *
 * @author Berkelios
 */
public class MenuCollaboratorOrganizationUI implements Initializable {

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

    /**
     * Initialize.
     *
     * @param location local
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orgLabel.setText("Organization: " + ApplicationPOT.getInstance().getPlatform().getOrganizationsRegister().getOrganizationByUserEmail((ApplicationPOT.getInstance().getCurrentSession().getUserEmail())).getDesignation());
        nameLabel.setText("Name: " + ApplicationPOT.getInstance().getCurrentSession().getUserName());
    }

    /**
     * Register Freelancer Action.
     *
     * @param actionEvent the Action Event
     */
    @FXML
    public void registerFreelancerAction(ActionEvent actionEvent) {
        Stages.newWindow("/fxml/useCases/RegisterFreelancerWindow.fxml", "Register Freelancer", false);
    }

    /**
     * Create Task Action.
     *
     * @param actionEvent the Action Event
     */
    @FXML
    public void createTaskAction(ActionEvent actionEvent) {
        Stages.newWindow("/fxml/useCases/CreateTaskWindow.fxml", "Create Task", false);
    }

    /**
     * Freelancers Payments Action.
     *
     * @param event the Event
     */
    @FXML
    public void FreelancersPaymentsAction(ActionEvent event) {
        Stages.newWindow("/fxml/useCases/AnalyseFreelancerPaymentsWindow.fxml", "Analyse Freelancer Payments Statistics", false);
    }

    /**
     * Tasks Stats Action.
     *
     * @param event the Event
     */
    @FXML
    public void TasksStatsAction(ActionEvent event) {
        Stages.newWindow("/fxml/useCases/AnalyseTasksStatisticsWindow.fxml", "Analyse Tasks Statistics", false);
    }

    /**
     * Create Payment Transaction Action.
     *
     * @param actionEvent the Action Event
     */
    @FXML
    public void createPaymentTransactionAction(ActionEvent actionEvent) {
        Stages.newWindow("/fxml/useCases/PaymentTransactionWindow.fxml", "Create Payment Transaction", false);

    }

    /**
     * Load Transactions File Action.
     *
     * @param actionEvent the Action Event
     */
    public void LoadTransactionsFileAction(ActionEvent actionEvent) {
        Stages.newWindow("/fxml/useCases/LoadTransactionsFileWindow.fxml", "Load File", false);
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
