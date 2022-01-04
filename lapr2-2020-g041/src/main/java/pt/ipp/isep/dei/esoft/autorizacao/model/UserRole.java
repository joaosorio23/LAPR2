package pt.ipp.isep.dei.esoft.autorizacao.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type User Role.
 *
 * @author paulomaio
 */
public class UserRole implements Serializable {

    /**
     * The User Role Paper.
     */
    private String m_strPaper;
    /**
     * The User Role Description.
     */
    private String m_strDescription;

    /**
     * Instantiates a new User Role.
     *
     * @param strPaper the Paper
     */
    public UserRole(String strPaper) {
        if ((strPaper == null) || (strPaper.isEmpty())) {
            throw new IllegalArgumentException("The argument cannot be null or empty.");
        }

        this.m_strPaper = strPaper;
        this.m_strDescription = strPaper;
    }

    /**
     * Instantiates a new User Role.
     *
     * @param strPaper       the Paper
     * @param strDescription the Description
     */
    public UserRole(String strPaper, String strDescription) {
        if ((strPaper == null) || (strDescription == null) || (strPaper.isEmpty()) || (strDescription.isEmpty())) {
            throw new IllegalArgumentException("None of the arguments cannot be null or empty.");
        }

        this.m_strPaper = strPaper;
        this.m_strDescription = strDescription;
    }

    /**
     * Has ID.
     *
     * @param strId the ID
     * @return boolean boolean
     */
    public boolean hasId(String strId) {
        return this.m_strPaper.equals(strId);
    }

    /**
     * Hash Code.
     *
     * @return the Hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.m_strPaper);
        return hash;
    }

    /**
     * Checks if two User Roles objects are equal.
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
        UserRole obj = (UserRole) o;
        return Objects.equals(m_strPaper, obj.m_strPaper);
    }

    /**
     * Textual User's Role Description.
     *
     * @return Textual User's Role Description
     */
    @Override
    public String toString() {
        return String.format("%s - %s", this.m_strPaper, this.m_strDescription);
    }
}
