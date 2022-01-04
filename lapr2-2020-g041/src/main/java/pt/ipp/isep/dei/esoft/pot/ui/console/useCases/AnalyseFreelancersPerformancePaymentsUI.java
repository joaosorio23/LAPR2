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
import pt.ipp.isep.dei.esoft.pot.controller.useCases.AnalyseFreelancersPerformancePaymentsController;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Analyse freelancers performance payments ui.
 *
 * @author Berkelios
 */
public class AnalyseFreelancersPerformancePaymentsUI implements Initializable {

    private AnalyseFreelancersPerformancePaymentsController appController;
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
        appController = new AnalyseFreelancersPerformancePaymentsController();

        HistogramOfAllFreelancers();

        if (appController.getListFreelancersPayments().size() != 2) {
            List<String> listStatistics = appController.getListFreelancersPayments();
            listStatistics.remove(listStatistics.size() - 1);
            listStatistics.remove(listStatistics.size() - 1);
            this.lstViewStats.getItems().addAll(listStatistics);
        }

        this.totalLabel.setText(String.format("Mean of the Payments: %.2f€%nStandard Deviation of the Payments: %.2f€", Double.parseDouble(appController.getListFreelancersPayments().get(appController.getListFreelancersPayments().size() - 2)), Double.parseDouble(appController.getListFreelancersPayments().get(appController.getListFreelancersPayments().size() - 1))));

        List<String> FreeIds = new ArrayList<>();
        FreeIds.add("All");
        for (int i = 0; i < appController.getListFreelancersWithPayments().size(); i++) {
            FreeIds.add(appController.getListFreelancersWithPayments().get(i).getId());
        }
        comboBox.getSelectionModel().select(FreeIds.get(0));
        comboBox.getItems().setAll(FreeIds);
    }

    @FXML
    private void comboBoxAction(ActionEvent event) {
        Histogram.getData().clear();
        String selected = comboBox.getSelectionModel().getSelectedItem();
        if (selected.equals("All")) {
            HistogramOfAllFreelancers();
        } else {

            XYChart.Series set = new XYChart.Series<>();

            set.getData().add(new XYChart.Data((String.format("]-∞, %.2f]", appController.getPaymentsFree(selected).get(3))), appController.getPaymentsFree(selected).get(0)));
            set.getData().add(new XYChart.Data((String.format("]%.2f, %.2f[", appController.getPaymentsFree(selected).get(3), appController.getPaymentsFree(selected).get(4))), appController.getPaymentsFree(selected).get(1)));
            set.getData().add(new XYChart.Data((String.format("[%.2f, +∞[", appController.getPaymentsFree(selected).get(4))), appController.getPaymentsFree(selected).get(2)));
            set.setName("Payments");

            Histogram.getData().addAll(set);
        }
    }

    /**
     * Back to menu action.
     *
     * @param actionEvent the action event
     */
    public void BackToMenuAction(ActionEvent actionEvent) {
        Alerts.back(actionEvent, "Do you really want to go back to the Menu?");
    }

    /**
     * Histogram of all freelancers.
     */
    public void HistogramOfAllFreelancers() {
        XYChart.Series set = new XYChart.Series<>();

        set.getData().add(new XYChart.Data(String.format("]-∞, %.2f]", appController.getPaymentsInterval().get(3)), appController.getPaymentsInterval().get(0)));
        set.getData().add(new XYChart.Data(String.format("]%.2f, %.2f[", appController.getPaymentsInterval().get(3), appController.getPaymentsInterval().get(4)), appController.getPaymentsInterval().get(1)));
        set.getData().add(new XYChart.Data(String.format("[%.2f, +∞[", appController.getPaymentsInterval().get(4)), appController.getPaymentsInterval().get(2)));

        set.setName("Payments");

        Histogram.getData().addAll(set);
    }
}
