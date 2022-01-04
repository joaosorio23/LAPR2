package pt.ipp.isep.dei.esoft.pot.model;

import pt.ipp.isep.dei.esoft.pot.model.enun.Country;
import pt.ipp.isep.dei.esoft.pot.model.enun.LevelOfExpertise;

import java.io.Serializable;

/**
 * The type Freelancer.
 *
 * @author Berkelios
 */
public class Freelancer implements Serializable {

    /**
     * The Freelancer's ID.
     */
    private String m_strId;
    /**
     * The Freelancer's Name.
     */
    private String m_strName;
    /**
     * The Freelancer's Email.
     */
    private String m_strEmail;
    /**
     * The Freelancer's NIF.
     */
    private double m_intNif;
    /**
     * The Freelancer's IBAN.
     */
    private String m_douIban;
    /**
     * The Freelancer's Location.
     */
    private Location m_oLocal;
    /**
     * The Freelancer's Level of Expertise.
     */
    private LevelOfExpertise m_oLevel;
    private double m_douOverallPayments;

    /**
     * Instantiates a new Freelancer.
     *
     * @param m_strId    the ID
     * @param m_strName  the Name
     * @param m_strEmail the Email
     * @param m_intNif   the NIF
     * @param m_intIban  the IBAN
     * @param m_oLocal   the Location
     * @param m_oLevel   the Level Of Expertise
     */
    public Freelancer(String m_strId, String m_strName, String m_strEmail, String m_intNif, String m_intIban, Location m_oLocal, LevelOfExpertise m_oLevel) {
        this.m_oLocal = m_oLocal;
        this.m_oLevel = m_oLevel;
        setId(m_strId);
        setName(m_strName);
        setEmail(m_strEmail);
        setNif(m_intNif);
        setIban(m_intIban);
    }

    /**
     * New location.
     *
     * @param address the Address
     * @param country the Country
     * @return the Location
     */
    public static Location newLocation(String address, Country country) {
        return new Location(address, country);
    }

    /**
     * Gets Level Of Expertise.
     *
     * @return the Level Of Expertise
     */
    public LevelOfExpertise getLevel() {
        return this.m_oLevel;
    }

    /**
     * Gets ID.
     *
     * @return the ID
     */
    public String getId() {
        return this.m_strId;
    }

    /**
     * Sets ID.
     *
     * @param m_strId the ID
     */
    public void setId(String m_strId) {
        if (m_strId == null || m_strId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid ID!");
        }
        this.m_strId = m_strId;
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
     * @param m_strName the Name
     */
    public void setName(String m_strName) {
        if (m_strName == null || m_strName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Name!");
        }
        this.m_strName = m_strName;
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
     * Sets Email.
     *
     * @param m_strEmail the Email
     */
    public void setEmail(String m_strEmail) {
        if (m_strEmail == null || m_strEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Email!");
        }
        this.m_strEmail = m_strEmail;
    }

    /**
     * Gets NIF.
     *
     * @return the NIF
     */
    public Double getNif() {
        return this.m_intNif;
    }

    /**
     * Sets NIF.
     *
     * @param m_intNif the NIF
     */
    public void setNif(String m_intNif) {
        if (!m_intNif.trim().isEmpty() && !m_intNif.contains(".")) {
            try {
                this.m_intNif = Double.parseDouble(m_intNif);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid NIF!");
            }
        } else {
            throw new IllegalArgumentException("Invalid NIF!");
        }
    }

    /**
     * Gets IBAN.
     *
     * @return the IBAN
     */
    public String getIban() {
        return this.m_oLocal.getCountry().initialIBAN + this.m_douIban;
    }

    /**
     * Sets IBAN.
     *
     * @param m_douIban the IBAN
     */
    public void setIban(String m_douIban) {
        if (!(m_douIban.trim().isEmpty()) && !(m_douIban.contains("."))) {
            try {
                double verification = Double.parseDouble(m_douIban);
                this.m_douIban = m_douIban;
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid IBAN!");
            }
        } else {
            throw new IllegalArgumentException("Invalid IBAN!");
        }
    }

    /**
     * Gets IBAN.
     *
     * @return the IBAN
     */
    public Location getLocal() {
        return m_oLocal;
    }

    /**
     * Gets Overall Payments.
     *
     * @return the Overall Payments
     */
    public Double getOverallPayments() {
        return this.m_douOverallPayments;
    }

    /**
     * Sets Payment.
     *
     * @param m_douPayment the Payment
     */
    public void setPayment(double m_douPayment) {
        try {
            this.m_douOverallPayments = this.m_douOverallPayments + m_douPayment;
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid Overall Payment!");
        }
    }

    /**
     * Checks if two Freelancers objects are equal.
     *
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Freelancer)) {
            return false;
        }
        Freelancer that = (Freelancer) o;
        return m_intNif == that.m_intNif
                && m_douIban.equals(that.m_douIban)
                && m_strId.equals(that.m_strId) && m_strEmail.equals(that.m_strEmail);
    }

    /**
     * Textual Freelancer's Description.
     *
     * @return Textual Freelancer's Description
     */
    @Override
    public String toString() {
        return String.format("ID: %s , Name: %s , Email: %s , NIF: %.0f , IBAN: %s - Local: %s - Level of Expertise: %s", this.m_strId, this.m_strName, this.m_strEmail, this.m_intNif, this.getIban(), this.m_oLocal.toString(), this.m_oLevel);
    }
}
