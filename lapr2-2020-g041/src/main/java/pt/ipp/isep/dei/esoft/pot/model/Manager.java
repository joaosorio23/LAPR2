package pt.ipp.isep.dei.esoft.pot.model;

import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;

import java.io.Serializable;

/**
 * The type Manager.
 *
 * @author Berkelios
 */
public class Manager implements Serializable {

    /**
     * The Manager's Name.
     */
    private String m_strName;
    /**
     * The Manager's Email.
     */
    private String m_strEmail;

    /**
     * Instantiates a new Manager.
     *
     * @param strName  the Name
     * @param strEmail the Email
     */
    public Manager(String strName, String strEmail) {
        setName(strName);
        setEmail(strEmail);
    }

    /**
     * Gets email.
     *
     * @return the Email
     */
    public String getEmail() {
        return this.m_strEmail;
    }

    /**
     * Sets Email.
     *
     * @param strEmail the Email
     */
    private void setEmail(String strEmail) {
        if (strEmail == null || strEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Manager's Email!");
        } else {
            if (ApplicationPOT.getInstance().getPlatform().getFacadeAuthorization().exitsUser(strEmail)) {
                throw new IllegalArgumentException("Manager's Email already exists!");
            }
        }
        this.m_strEmail = strEmail;
    }

    /**
     * Gets Name.
     *
     * @return the Name
     */
    public String getName() {
        return this.m_strName;
    }

    /**
     * Sets Name.
     *
     * @param strName the Name
     */
    private void setName(String strName) {
        if (strName == null || strName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Manager's Name!");
        }
        this.m_strName = strName;
    }

    /**
     * Textual Manager's Description.
     *
     * @return Textual Manager's Description
     */
    @Override
    public String toString() {
        return String.format("Name: %s , Email: %s", this.m_strName, this.m_strEmail);
    }
}
