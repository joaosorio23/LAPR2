package pt.ipp.isep.dei.esoft.autorizacao.model;

import java.io.Serializable;
import java.util.List;

/**
 * The type User Session.
 *
 * @author paulomaio
 */
public class UserSession implements Serializable {

    /**
     * The User.
     */
    private User m_oUser;

    /**
     * Instantiates a new User Session.
     *
     * @param oUser the User
     */
    public UserSession(User oUser) {
        if (oUser == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        this.m_oUser = oUser;
    }

    /**
     * Do logout.
     */
    public void doLogout() {
        this.m_oUser = null;
    }

    /**
     * Is Logged In.
     *
     * @return boolean boolean
     */
    public boolean isLoggedIn() {
        return this.m_oUser != null;
    }

    /**
     * Gets User's Name.
     *
     * @return the User's Name
     */
    public String getUserName() {
        if (isLoggedIn()) {
            return this.m_oUser.getName();
        }
        return null;
    }

    /**
     * Gets User's Email.
     *
     * @return the User's Email
     */
    public String getUserEmail() {
        if (isLoggedIn()) {
            return this.m_oUser.getEmail();
        }
        return null;
    }

    /**
     * Gets User's Papers.
     *
     * @return the User's Papers
     */
    public List<UserRole> getUserPapers() {
        return this.m_oUser.getPapers();
    }
}
