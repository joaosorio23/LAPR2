package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.LoadTransactionsFileController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * The type Load transactions file ui.
 */
public class LoadTransactionsFileUI implements Initializable {

    private FileChooser.ExtensionFilter filter;
    private FileChooser fc;
    /**
     * The Text field.
     */
    @FXML
    private LoadTransactionsFileController appController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appController = new LoadTransactionsFileController();
    }

    /**
     * Load filebtn.
     *
     * @param actionEvent the action event
     */
    public void LoadFilebtn(ActionEvent actionEvent) {
        fc = new FileChooser();
        String csv = ".csv";
        String txt = ".txt";
        filter = appController.filterType("*.csv");
        fc.getExtensionFilters().add(filter);
        filter = appController.filterType("*.txt");
        fc.getExtensionFilters().add(filter);
        File f = fc.showOpenDialog(null);

        try {
            if (f != null && f.getName().contains(csv)) {
                if (appController.loadFileCsv(f)) {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Success...", "File successfully loaded!").show();
                }
            } else if (f != null && f.getName().contains(txt)) {
                if (appController.loadFileTxt(f)) {
                    Alerts.createAlert(Alert.AlertType.INFORMATION, "Success...", "File successfully loaded!").show();
                }
            }
        } catch (FileNotFoundException fnf) {
            Alerts.createAlert(Alert.AlertType.INFORMATION, "Error...", fnf.getMessage()).show();
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException | NullPointerException nsee) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Error...", nsee.getMessage()).show();
        }

    }

    /**
     * Back to menu action.
     *
     * @param event the event
     */
    public void backToMenuAction(ActionEvent event) {
        Alerts.back(event, "Do you really want to go back to the Menu?");
    }
}
