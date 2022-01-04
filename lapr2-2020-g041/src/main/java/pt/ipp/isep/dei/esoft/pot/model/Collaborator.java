package pt.ipp.isep.dei.esoft.pot.model;

import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;

import java.io.Serializable;

/**
 * The type Collaborator.
 *
 * @author Berkelios
 */
public class Collaborator implements Serializable {

    /**
     * The Collaborator's Name.
     */
    private String m_strName;
    /**
     * The Collaborator's Email.
     */
    private String m_strEmail;

    /**
     * Instantiates a new Collaborator.
     *
     * @param strName  the Name
     * @param strEmail the Email
     */
    public Collaborator(String strName, String strEmail) {
        setName(strName);
        setEmail(strEmail);
    }

    /**
     * Gets Email.
     *
     * @return the Email
     */
    public String getEmail() {
        return this.m_strEmail;
    }

    /**
     * Set Collaborator's Email.
     *
     * @param strEmail Collaborator's Email
     */
    private void setEmail(String strEmail) {
        if (strEmail == null || strEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Collaborator's Email!");
        } else {
            if (ApplicationPOT.getInstance().getPlatform().getFacadeAuthorization().exitsUser(strEmail)) {
                throw new IllegalArgumentException("Collaborator's Email already exists!");
            }
        }
        this.m_strEmail = strEmail;
    }

    /**
     * Gets Collaborator's Name.
     *
     * @return the Collaborator's Name
     */
    public String getName() {
        return this.m_strName;
    }

    /**
     * Sets Collaborator's Name.
     *
     * @param strName Collaborator's Name
     */
    private void setName(String strName) {
        if (strName == null || strName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Collaborator's Name!");
        }
        this.m_strName = strName;
    }

    /**
     * Textual Collaborator's Description.
     *
     * @return Textual Collaborator's Description
     */
    @Override
    public String toString() {
        return String.format("Name: %s Email: %s", this.m_strName, this.m_strEmail);
    }
}
