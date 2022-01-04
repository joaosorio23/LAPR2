package pt.ipp.isep.dei.esoft.autorizacao;

import pt.ipp.isep.dei.esoft.autorizacao.model.*;

import java.io.Serializable;

/**
 * The type Facade Authorization.
 *
 * @author paulomaio
 */
public class FacadeAuthorization implements Serializable {

    /**
     * The User Role Register.
     */
    private final UserRoleRegister m_oPapers = new UserRoleRegister();
    /**
     * The Users Register.
     */
    private final UsersRegister m_oUsers = new UsersRegister();
    /**
     * The User Session.
     */
    private UserSession m_oSession = null;

    /**
     * Record User Paper.
     *
     * @param strPaper the Paper
     * @return boolean boolean
     */
    public boolean recordUserPaper(String strPaper) {
        UserRole papel = this.m_oPapers.newUserPaper(strPaper);
        return this.m_oPapers.addPaper(papel);
    }

    /**
     * Record User with Paper.
     *
     * @param strName     the Name
     * @param strEmail    the Email
     * @param strPassword the Password
     * @param strPaper    the Paper
     * @return boolean boolean
     */
    public boolean recordUserWithPaper(String strName, String strEmail, String strPassword, String strPaper) {
        UserRole papel = this.m_oPapers.findPaper(strPaper);
        User user = this.m_oUsers.newUser(strName, strEmail, strPassword);
        user.addPaper(papel);
        return this.m_oUsers.addUser(user);
    }

    /**
     * Record User with Papers.
     *
     * @param strName     the Name
     * @param strEmail    the Email
     * @param strPassword the Password
     * @param papers      the Papers
     * @return boolean boolean
     */
    public boolean recordUserWithPapers(String strName, String strEmail, String strPassword, String[] papers) {
        User user = this.m_oUsers.newUser(strName, strEmail, strPassword);
        for (String strPaper : papers) {
            UserRole paper = this.m_oPapers.findPaper(strPaper);
            user.addPaper(paper);
        }

        return this.m_oUsers.addUser(user);
    }

    /**
     * Exits User.
     *
     * @param strId the ID
     * @return boolean boolean
     */
    public boolean exitsUser(String strId) {
        return this.m_oUsers.hasUser(strId);
    }

    /**
     * Do Login User Session.
     *
     * @param strId  the ID
     * @param strPwd the Password
     * @return the User Session
     */
    public UserSession doLogin(String strId, String strPwd) {
        User user = this.m_oUsers.findUser(strId);
        if (user != null) {
            if (user.hasPassword(strPwd)) {
                this.m_oSession = new UserSession(user);
            }
        }
        return getCurrentSession();
    }

    /**
     * Gets Current Session.
     *
     * @return the Current Session
     */
    public UserSession getCurrentSession() {
        return this.m_oSession;
    }

    /**
     * Do Logout.
     */
    public void doLogout() {
        if (this.m_oSession != null) {
            this.m_oSession.doLogout();
        }
        this.m_oSession = null;
    }
}
