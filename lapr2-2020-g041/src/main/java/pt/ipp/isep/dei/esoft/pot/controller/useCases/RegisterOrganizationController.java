package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import javafx.scene.control.Alert;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.*;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

import java.io.IOException;

/**
 * The type Register Organization Controller.
 *
 * @author Berkelios
 */
public class RegisterOrganizationController {

    /**
     * The App.
     */
    private ApplicationPOT m_oApp;
    /**
     * The Platform.
     */
    private Platform m_oPlatform;
    /**
     * The Organization.
     */
    private Organization m_oOrganization;
    /**
     * The Organizations Register.
     */
    private OrganizationsRegister or;

    /**
     * Instantiates a new Register Organization Controller.
     */
    public RegisterOrganizationController() {
        this.m_oApp = ApplicationPOT.getInstance();
        this.m_oPlatform = this.m_oApp.getPlatform();
        this.or = this.m_oPlatform.getOrganizationsRegister();
    }

    /**
     * New Organization.
     *
     * @param strDesig  the Designation
     * @param strNameM  the Manager's Name
     * @param strEmailM the Manager's Email
     * @param strNameC  the Collaborator's Name
     * @param strEmailC the Collaborator's Email
     * @return boolean boolean
     */
    public boolean newOrganization(String strDesig, String strNameM, String strEmailM, String strNameC, String strEmailC) {
        try {
            Manager oMan = Organization.newManager(strNameM, strEmailM);
            Collaborator oColab = Organization.newCollaborator(strNameC, strEmailC);
            this.m_oOrganization = this.or.newOrganization(strDesig, oMan, oColab);
            return this.or.validateOrganization(this.m_oOrganization);
        } catch (IllegalArgumentException iae) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", iae.getMessage()).show();
            this.m_oOrganization = null;
            return false;
        }
    }

    /**
     * Register Organization.
     *
     * @return boolean boolean
     * @throws IOException the IO Exception
     */
    public boolean registerOrganization() throws IOException {
        return this.or.registerOrganization(this.m_oOrganization);
    }

    /**
     * Gets Organization String.
     *
     * @return the Organization String
     */
    public String getOrganizationString() {
        return this.m_oOrganization.toString();
    }
}
