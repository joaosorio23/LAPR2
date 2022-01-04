package pt.ipp.isep.dei.esoft.autorizacao.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The type User Role register.
 *
 * @author paulomaio
 */
public class UserRoleRegister implements Serializable {

    /**
     * The User Roles Papers List.
     */
    private Set<UserRole> m_lstPapers = new HashSet<UserRole>();

    /**
     * New User Paper Role.
     *
     * @param strPaper the Paper
     * @return the User Role
     */
    public UserRole newUserPaper(String strPaper) {
        return new UserRole(strPaper);
    }

    /**
     * Add Paper.
     *
     * @param paper the Paper
     * @return boolean boolean
     */
    public boolean addPaper(UserRole paper) {
        if (paper != null) {
            return this.m_lstPapers.add(paper);
        }
        return false;
    }

    /**
     * Find Paper User Role.
     *
     * @param strPaper the Paper
     * @return the User Role
     */
    public UserRole findPaper(String strPaper) {
        for (UserRole p : this.m_lstPapers) {
            if (p.hasId(strPaper)) {
                return p;
            }
        }
        return null;
    }
}
