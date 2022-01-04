/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.AnalyseTasksStatisticsController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;

/**
 * The type Analyse tasks statistics ui.
 *
 * @author Admin
 */
public class AnalyseTasksStatisticsUI implements Initializable {

    @FXML
    private BarChart<?, ?> Histogram;

    @FXML
    private ListView<String> lstViewStats;

    @FXML
    private Label totalLabel;

    @FXML
    private ComboBox<String> comboBox;

    private AnalyseTasksStatisticsController appController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appController = new AnalyseTasksStatisticsController();

        HistogramOfAllFreelancers();

        List<String> FreeIds = new ArrayList<>();
        FreeIds.add("All");
        for (int i = 0; i < appController.getLstFree().size(); i++) {
            FreeIds.add(appController.getLstFree().get(i).getId());
        }
        comboBox.getSelectionModel().select(FreeIds.get(0));
        comboBox.getItems().setAll(FreeIds);

        totalLabel.setText(appController.getFreeList().get(appController.getFreeList().size() - 1));
        if (appController.getFreeList().size() != 1) {

            appController.getFreeList().remove(appController.getFreeList().size() - 1);
            lstViewStats.getItems().addAll(appController.getFreeList());
        }
    }

    /**
     * Combo box action.
     *
     * @param event the event
     */
    @FXML
    public void comboBoxAction(ActionEvent event) {
        Histogram.getData().clear();
        String selected = comboBox.getSelectionModel().getSelectedItem();
        if (selected.equals("All")) {
            HistogramOfAllFreelancers();
        } else {

            XYChart.Series set = new XYChart.Series<>();

            set.getData().add(new XYChart.Data((String.format("]-∞, %.2f]", appController.getDelaysFree(selected).get(3))), appController.getDelaysFree(selected).get(0)));
            set.getData().add(new XYChart.Data((String.format("]%.2f, %.2f[", appController.getDelaysFree(selected).get(3), appController.getDelaysFree(selected).get(4))), appController.getDelaysFree(selected).get(1)));
            set.getData().add(new XYChart.Data((String.format("[%.2f, +∞[", appController.getDelaysFree(selected).get(4))), appController.getDelaysFree(selected).get(2)));
            set.setName("Delays");

            Histogram.getData().addAll(set);
        }
    }

    /**
     * Sort by name action.
     *
     * @param event the event
     */
    @FXML
    public void sortByNameAction(ActionEvent event) {
        lstViewStats.getItems().clear();
        ArrayList<Double> test = appController.getListStats();
        if (appController.getFreeList().size() != 1) {
            appController.getFreeList().remove(appController.getFreeList().size() - 1);
            lstViewStats.getItems().addAll(appController.getFreeList());
        }
    }

    /**
     * Sort by payments action.
     *
     * @param event the event
     */
    @FXML
    public void sortByPaymentsAction(ActionEvent event) {
        lstViewStats.getItems().clear();
        lstViewStats.getItems().addAll(appController.getFreeSortedByPayment());
        lstViewStats.getItems().remove(lstViewStats.getItems().size() - 1);
        lstViewStats.getItems().remove(lstViewStats.getItems().size() - 1);
    }

    /**
     * Histogram of all freelancers.
     */
    public void HistogramOfAllFreelancers() {
        XYChart.Series set = new XYChart.Series<>();

        set.getData().add(new XYChart.Data(String.format("]-∞, %.2f]", appController.getListStats().get(3)), appController.getListStats().get(0)));
        set.getData().add(new XYChart.Data(String.format("]%.2f, %.2f[", appController.getListStats().get(3), appController.getListStats().get(4)), appController.getListStats().get(1)));
        set.getData().add(new XYChart.Data(String.format("[%.2f, +∞[", appController.getListStats().get(4)), appController.getListStats().get(2)));

        set.setName("Delays");

        Histogram.getData().addAll(set);
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
