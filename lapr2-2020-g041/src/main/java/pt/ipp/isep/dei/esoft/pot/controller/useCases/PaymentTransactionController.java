package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import javafx.scene.control.Alert;
import pt.ipp.isep.dei.esoft.autorizacao.model.UserSession;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.*;
import pt.ipp.isep.dei.esoft.pot.model.list.PaymentTransactionList;
import pt.ipp.isep.dei.esoft.pot.model.list.TasksList;
import pt.ipp.isep.dei.esoft.pot.model.register.FreelancersRegister;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Payment transaction controller.
 *
 * @author Berkelios
 */
public class PaymentTransactionController {

    private Platform m_Platform;
    private FreelancersRegister rfr;
    private Freelancer m_ofree;
    private Task m_oTask;
    private TasksList tl;
    private Organization org;
    private OrganizationsRegister rorgs;
    private PaymentTransactionList ptl;
    private PaymentTransaction m_oPaymentTransaction;

    /**
     * Instantiates a new Payment transaction controller.
     */
    public PaymentTransactionController() {
        this.m_Platform = ApplicationPOT.getInstance().getPlatform();
        this.rfr = this.m_Platform.getFreelancersRegister();
        this.rorgs = this.m_Platform.getOrganizationsRegister();
    }

    /**
     * Sets data.
     *
     * @param taskEnd      the task end
     * @param taskDelay    the task delay
     * @param taskDescwork the task descwork
     * @param freel        the freel
     * @param task         the task
     * @return the data
     */
    public boolean setData(LocalDate taskEnd, String taskDelay, String taskDescwork, Freelancer freel, Task task) {
        try {
            this.m_ofree = freel;
            this.m_oTask = task;
            this.m_oTask.newTaskExecution(taskEnd, taskDelay, taskDescwork);
            return true;
        } catch (IllegalArgumentException iae) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", iae.getMessage()).show();
            return false;
        }
    }

    /**
     * New payment transaction boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean newPaymentTransaction(String id) {
        try {
            this.ptl = this.org.getPaymentTransactionList();
            this.m_oPaymentTransaction = this.ptl.newPaymentTransaction(id, this.m_ofree, this.m_oTask);
            return this.ptl.validatePaymentTransaction(this.m_oPaymentTransaction);
        } catch (IllegalArgumentException iae) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", iae.getMessage()).show();
            this.m_oPaymentTransaction = null;
            return false;
        }
    }

    /**
     * Register payment transaction boolean.
     *
     * @return the boolean
     */
    public boolean registerPaymentTransaction() {
        this.m_ofree.setPayment(this.m_oPaymentTransaction.getValue());
        return this.ptl.registerPaymentTransaction(this.m_oPaymentTransaction);
    }

    /**
     * Gets payment transaction string.
     *
     * @return the payment transaction string
     */
    public String getPaymentTransactionString() {
        return this.m_oPaymentTransaction.toString();
    }

    /**
     * Gets value string.
     *
     * @return the value string
     */
    public String getValueString() {
        return ("\nTOTAL VALUE: " + String.valueOf(this.m_oPaymentTransaction.getValue()) + "€");
    }

    /**
     * Gets freelancers id.
     *
     * @return the freelancers id
     */
    public List<String> getFreelancersID() {
        List<String> listaID = new ArrayList<>();
        for (int i = 0; i < this.rfr.getFreelancers().size(); i++) {
            Freelancer freel = this.rfr.getFreelancers().get(i);
            listaID.add(freel.getId());
        }
        return listaID;
    }

    /**
     * Gets freelancer by id.
     *
     * @param freelID the freel id
     * @return the freelancer by id
     */
    public Freelancer getFreelancerByID(String freelID) {
        return this.rfr.getFreelancerByID(freelID);
    }

    /**
     * Gets task id.
     *
     * @return the task id
     */
    public List<String> getTaskID() {
        ApplicationPOT app = ApplicationPOT.getInstance();
        UserSession sessao = app.getCurrentSession();
        String strUserEmail = sessao.getUserEmail();
        this.org = this.rorgs.getOrganizationByUserEmail(strUserEmail);
        this.tl = this.org.getTasksList();
        List<Task> listAux = tl.getTasks();
        if (!this.org.getPaymentTransactionList().getPaymentTransactions().isEmpty()) {
            for (int i = 0; i < this.org.getPaymentTransactionList().getPaymentTransactions().size(); i++) {
                PaymentTransaction pt = this.org.getPaymentTransactionList().getPaymentTransactions().get(i);
                if (pt.hasTask()) {
                    if (listAux.contains(pt.getTask())) {
                        listAux.remove(pt.getTask());
                    }
                }
            }
        }
        List<String> listaID = new ArrayList<>();
        if (!listAux.isEmpty()) {
            for (int i = 0; i < listAux.size(); i++) {
                Task task = listAux.get(i);
                listaID.add(task.getID());
            }
        }

        return listaID;
    }

    /**
     * Gets task by id.
     *
     * @param taskID the task id
     * @return the task by id
     */
    public Task getTaskByID(String taskID) {
        return this.tl.getTaskById(taskID);
    }
}
