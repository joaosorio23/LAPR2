package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import javafx.scene.control.Alert;
import pt.ipp.isep.dei.esoft.autorizacao.model.UserSession;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;
import pt.ipp.isep.dei.esoft.pot.model.Organization;
import pt.ipp.isep.dei.esoft.pot.model.Platform;
import pt.ipp.isep.dei.esoft.pot.model.Task;
import pt.ipp.isep.dei.esoft.pot.model.list.TasksList;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

/**
 * The type Create Task Controller.
 *
 * @author Berkelios
 */
public class CreateTaskController {

    /**
     * The Organizations Register.
     */
    private OrganizationsRegister rorgs;
    /**
     * The Platform.
     */
    private Platform m_oPlataforma;
    /**
     * The Task.
     */
    private Task m_oTask;
    /**
     * The Task List.
     */
    private TasksList tl;

    /**
     * Instantiates a new Create Task Controller.
     */
    public CreateTaskController() {
        this.m_oPlataforma = ApplicationPOT.getInstance().getPlatform();
        this.rorgs = this.m_oPlataforma.getOrganizationsRegister();
    }

    /**
     * New Task.
     *
     * @param id       the ID
     * @param desc     the Description
     * @param duration the Duration
     * @param cost     the Cost per Hour
     * @param cat      the Category
     * @return boolean boolean
     */
    public boolean newTask(String id, String desc, String duration, String cost, String cat) {
        try {

            ApplicationPOT app = ApplicationPOT.getInstance();
            UserSession session = app.getCurrentSession();
            String strUserEmail = session.getUserEmail();
            Organization org = this.rorgs.getOrganizationByUserEmail(strUserEmail);
            this.tl = org.getTasksList();
            this.m_oTask = this.tl.newTask(id, desc, duration, cost, cat);
            return this.tl.validateTask(this.m_oTask);
        } catch (IllegalArgumentException iae) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", iae.getMessage()).show();
            this.m_oTask = null;
            return false;
        }
    }

    /**
     * Register Task.
     *
     * @return boolean boolean
     */
    public boolean registerTask() {
        return this.tl.registerTask(this.m_oTask);
    }

    /**
     * Gets Task String.
     *
     * @return the Task String
     */
    public String getTaskString() {
        return this.m_oTask.toString();
    }
}
