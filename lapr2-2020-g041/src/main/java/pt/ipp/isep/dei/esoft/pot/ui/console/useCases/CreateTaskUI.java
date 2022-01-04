package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.CreateTaskController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Create Task UI.
 *
 * @author Berkelios
 */
public class CreateTaskUI implements Initializable {

    /**
     * The ID.
     */
    @FXML
    public TextField id;
    /**
     * The Category.
     */
    @FXML
    public TextField category;
    /**
     * The Brief Description.
     */
    @FXML
    public TextField bDescription;
    /**
     * The Duration.
     */
    @FXML
    public TextField duration;
    /**
     * The Cost per Hour.
     */
    @FXML
    public TextField cost;
    /**
     * The App Controller.
     */
    private CreateTaskController appController;

    /**
     * Initialize.
     *
     * @param location local
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appController = new CreateTaskController();
    }

    /**
     * Create Task Action.
     *
     * @param actionEvent the Action event
     */
    @FXML
    public void createTaskAction(ActionEvent actionEvent) {
        if (appController.newTask(this.id.getText().trim(), this.bDescription.getText().trim(), this.duration.getText().trim(), this.cost.getText().trim(), this.category.getText().trim())) {
            if (showDataAndConfirmation()) {
                if (appController.registerTask()) {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Success...", "Successfully creating the Task!").show();
                } else {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Error...", "Error creating the Task!").show();
                }
                clearData();
            }
        }
    }

    /**
     * Show Data And Confirmation
     *
     * @return boolean
     */
    private boolean showDataAndConfirmation() {
        return Alerts.showDataAndConfirmation(appController.getTaskString());
    }

    /**
     * Back To Menu Action
     *
     * @param event event
     */
    @FXML
    private void backToMenuAction(ActionEvent event) {
        Alerts.back(event, "Do you really want to go back to the Menu?");
    }

    /**
     * Clear Data.
     */
    private void clearData() {
        this.id.clear();
        this.category.clear();
        this.bDescription.clear();
        this.duration.clear();
        this.cost.clear();
    }
}
