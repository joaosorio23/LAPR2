/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esoft.pot.ui.console.useCases;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.apache.commons.math.MathException;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.AnalyseFreelancersPerformanceDelaysController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Analyse freelancers performance delays ui.
 *
 * @author Berkelios
 */
public class AnalyseFreelancersPerformanceDelaysUI implements Initializable {

    private AnalyseFreelancersPerformanceDelaysController appController;
    @FXML
    private BarChart<?, ?> Histogram;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private ListView<String> lstViewStats;
    @FXML
    private Label totalLabel;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label probabilityLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appController = new AnalyseFreelancersPerformanceDelaysController();

        HistogramOfAllFreelancers();

        if (appController.getListFreelancersDelays().size() != 2) {
            List<String> listStatistics = appController.getListFreelancersDelays();
            listStatistics.remove(listStatistics.size() - 1);
            listStatistics.remove(listStatistics.size() - 1);
            this.lstViewStats.getItems().addAll(listStatistics);
        }

        this.totalLabel.setText(String.format("Mean of the Delays: %.2f%nStandard Deviation of the Delays: %.2f", Double.parseDouble(appController.getListFreelancersDelays().get(appController.getListFreelancersDelays().size() - 2)), Double.parseDouble(appController.getListFreelancersDelays().get(appController.getListFreelancersDelays().size() - 1))));

        List<String> FreeIds = new ArrayList<>();
        FreeIds.add("All");
        for (int i = 0; i < appController.getListFreelancersWithPayments().size(); i++) {
            FreeIds.add(appController.getListFreelancersWithPayments().get(i).getId());
        }
        comboBox.getSelectionModel().select(FreeIds.get(0));
        comboBox.getItems().setAll(FreeIds);

        if (appController.getListFreelancersDelays().size() != 2) {
            try {
                this.probabilityLabel.setText(appController.getProbability());
            } catch (MathException e) {
                e.printStackTrace();
            }
        } else {
            this.probabilityLabel.setText("0,00%");
        }

    }

    @FXML
    private void comboBoxAction(ActionEvent event) {
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
     * Histogram of all freelancers.
     */
    public void HistogramOfAllFreelancers() {
        XYChart.Series set = new XYChart.Series<>();

        set.getData().add(new XYChart.Data(String.format("]-∞, %.2f]", appController.getDelaysInterval().get(3)), appController.getDelaysInterval().get(0)));
        set.getData().add(new XYChart.Data(String.format("]%.2f, %.2f[", appController.getDelaysInterval().get(3), appController.getDelaysInterval().get(4)), appController.getDelaysInterval().get(1)));
        set.getData().add(new XYChart.Data(String.format("[%.2f, +∞[", appController.getDelaysInterval().get(4)), appController.getDelaysInterval().get(2)));

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
