package pt.ipp.isep.dei.esoft.pot.controller;

import pt.ipp.isep.dei.esoft.autorizacao.model.UserRole;
import pt.ipp.isep.dei.esoft.pot.model.Platform;

import java.util.List;

/**
 * The type Login Controller.
 *
 * @author paulomaio
 */
public class LoginController {

    /**
     * The ApplicationPOT.
     */
    private ApplicationPOT m_oApp;
    /**
     * The Platform.
     */
    private Platform m_oPlatform;

    /**
     * Instantiates a new Login Controller.
     */
    public LoginController() {
        this.m_oApp = ApplicationPOT.getInstance();
        this.m_oPlatform = m_oApp.getPlatform();
    }

    /**
     * Do Login.
     *
     * @param strId  the ID
     * @param strPwd the Password
     * @return boolean boolean
     */
    public boolean doLogin(String strId, String strPwd) {
        return this.m_oApp.doLogin(strId, strPwd);
    }

    /**
     * Gets User's Roles.
     *
     * @return the User's Roles
     */
    public List<UserRole> getUserRoles() {
        if (this.m_oApp.getCurrentSession().isLoggedIn()) {
            return this.m_oApp.getCurrentSession().getUserPapers();
        }
        return null;
    }

    /**
     * Do Logout.
     */
    public void doLogout() {
        this.m_oApp.doLogout();
    }

    /**
     * Schedule.
     */
    public void schedule() {
        this.m_oPlatform.scheduleTimer();
    }
}
