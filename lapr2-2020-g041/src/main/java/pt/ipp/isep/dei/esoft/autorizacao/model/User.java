package pt.ipp.isep.dei.esoft.autorizacao.model;

import java.io.Serializable;
import java.util.*;

/**
 * The type User.
 *
 * @author paulomaio
 */
public class User implements Serializable {

    /**
     * The User's Name.
     */
    private String m_strName;
    /**
     * The User's Email.
     */
    private String m_strEmail;
    /**
     * The User's Password.
     */
    private String m_strPassword;
    /**
     * The User's Papers.
     */
    private Set<UserRole> m_lstPapers = new HashSet<>();

    /**
     * Instantiates a new User.
     *
     * @param strName     the Name
     * @param strEmail    the Email
     * @param strPassword the Password
     */
    public User(String strName, String strEmail, String strPassword) {

        if ((strName == null) || (strEmail == null) || (strPassword == null) || (strName.isEmpty()) || (strEmail.isEmpty()) || (strPassword.isEmpty())) {
            throw new IllegalArgumentException("None of the arguments cannot be null or empty.");
        }

        this.m_strName = strName;
        this.m_strEmail = strEmail;
        this.m_strPassword = strPassword;

    }

    /**
     * Gets ID.
     *
     * @return the ID
     */
    public String getId() {
        return this.m_strEmail;
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
     * Gets Email.
     *
     * @return the Email
     */
    public String getEmail() {
        return this.m_strEmail;
    }

    /**
     * Has ID.
     *
     * @param strId the ID
     * @return boolean boolean
     */
    public boolean hasId(String strId) {
        return this.m_strEmail.equals(strId);
    }

    /**
     * Has Password.
     *
     * @param strPwd the Password
     * @return boolean boolean
     */
    public boolean hasPassword(String strPwd) {
        return this.m_strPassword.equals(strPwd);
    }

    /**
     * Add Paper.
     *
     * @param paper the Paper
     */
    public void addPaper(UserRole paper) {
        if (paper != null) {
            this.m_lstPapers.add(paper);
        }
    }

    /**
     * Gets Papers.
     *
     * @return the Papers
     */
    public List<UserRole> getPapers() {
        return new ArrayList<>(this.m_lstPapers);
    }

    /**
     * Hash Code.
     *
     * @return the Hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.m_strEmail);
        return hash;
    }

    /**
     * Checks if two User objects are equal.
     *
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        // Inspirado em https://www.sitepoint.com/implement-javas-equals-method-correctly/

        // self check
        if (this == o) {
            return true;
        }
        // null check
        if (o == null) {
            return false;
        }
        // type check and cast
        if (getClass() != o.getClass()) {
            return false;
        }
        // field comparison
        User obj = (User) o;
        return Objects.equals(m_strEmail, obj.m_strEmail);
    }

    /**
     * Textual User's Description.
     *
     * @return Textual User's Description
     */
    @Override
    public String toString() {
        return String.format("%s - %s", this.m_strName, this.m_strEmail);
    }
}
