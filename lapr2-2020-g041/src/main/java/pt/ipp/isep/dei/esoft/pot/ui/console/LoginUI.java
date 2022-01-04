package pt.ipp.isep.dei.esoft.pot.ui.console;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.autorizacao.model.UserRole;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.controller.LoginController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;
import pt.ipp.isep.dei.esoft.pot.model.Constants;
import pt.ipp.isep.dei.esoft.pot.model.Stages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Login UI.
 *
 * @author Berkelios
 */
public class LoginUI implements Initializable {

    /**
     * The App Controller.
     */
    private LoginController appController;
    /**
     * The Max of Attempts try to Login (3 Attempts)
     */
    private int maxAttempts;
    /**
     * The User's Email.
     */
    @FXML
    private TextField emailUser;
    /**
     * The User's Password.
     */
    @FXML
    private PasswordField passUser;

    /**
     * Initialize.
     *
     * @param url url
     * @param rb rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maxAttempts = 3;
        appController = new LoginController();
        appController.schedule();
    }

    /**
     * Login Action.
     */
    @FXML
    public void LoginAction() {
        String email = emailUser.getText();
        String pass = passUser.getText();
        if (email == null || pass == null || email.isEmpty() || pass.isEmpty()) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data error...", "The email and password fields must be filled in!").show();
            cleanData();
        } else {
            boolean success = appController.doLogin(email, pass);
            if (!success) {
                if (maxAttempts == 0) {
                    maxAttempts = 3;
                }
                maxAttempts--;
                Alerts.createAlert(Alert.AlertType.ERROR, "Data error...", "The email or password is incorrect!\nHave more " + maxAttempts + " attempts!").show();
                cleanData();
            } else {
                maxAttempts = 3;
                cleanData();
                redirectsToStage(appController.getUserRoles());
            }
        }
    }

    /**
     * Redirects To Stage.
     *
     * @param papers the Papers
     */
    private void redirectsToStage(List<UserRole> papers) {
        if (papers == null || papers.isEmpty()) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Error...", "The user has not been assigned any role/function!").show();
            appController.doLogout();
        }

        assert papers != null;
        UserRole paper = papers.get(0);//Right now there is only one user role for each one.

        if (paper.hasId(Constants.PAPER_ADMINISTRATOR)) {
            Stages.newWindow("/fxml/menus/MenuAdministratorWindow.fxml", "Menu Administrator", true);
        }
        if (paper.hasId(Constants.PAPER_MANAGER_ORGANIZATION)) {
            Stages.newWindow("/fxml/menus/MenuManagerWindow.fxml", "Menu Manager", true);
        }
        if (paper.hasId(Constants.PAPER_COLLABORATOR_ORGANIZATION)) {
            Stages.newWindow("/fxml/menus/MenuCollaboratorWindow.fxml", "Menu Collaborator", true);
        }
    }

    /**
     * Clean Data.
     */
    private void cleanData() {
        emailUser.clear();
        passUser.clear();
    }

    /**
     * Close Application.
     *
     * @param actionEvent event
     */
    @FXML
    public void closeAppAction(ActionEvent actionEvent) {
        if (Alerts.backTF(actionEvent, "Do you really want to close the Application?")) {
            ApplicationPOT.getInstance().saveInformation();
            System.exit(0);
        }
    }
}
