package pt.ipp.isep.dei.esoft.autorizacao.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Users Register.
 *
 * @author paulomaio
 */
public class UsersRegister implements Serializable {

    /**
     * The Users List.
     */
    private Set<User> m_lstUsers = new HashSet<User>();

    /**
     * New User.
     *
     * @param strName     the Name
     * @param strEmail    the Email
     * @param strPassword the Password
     * @return the User
     */
    public User newUser(String strName, String strEmail, String strPassword) {
        return new User(strName, strEmail, strPassword);
    }

    /**
     * Add User.
     *
     * @param user the User
     * @return boolean boolean
     */
    public boolean addUser(User user) {
        if (user != null) {
            return this.m_lstUsers.add(user);
        }
        return false;
    }

    /**
     * Find User.
     *
     * @param strId the ID
     * @return the User
     */
    public User findUser(String strId) {
        for (User user : this.m_lstUsers) {
            if (user.hasId(strId)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Has User.
     *
     * @param strId the ID
     * @return boolean boolean
     */
    public boolean hasUser(String strId) {
        User user = findUser(strId);
        if (user != null) {
            return this.m_lstUsers.contains(user);
        }
        return false;
    }
}
