package pt.ipp.isep.dei.esoft.pot.model;

import pt.ipp.isep.dei.esoft.pot.model.enun.Country;

import java.io.Serializable;

/**
 * The type Location.
 *
 * @author Berkelios
 */
public class Location implements Serializable {

    /**
     * The Address.
     */
    private String m_strAddress;
    /**
     * The Country.
     */
    private Country m_strCountry;

    /**
     * Instantiates a new Location.
     *
     * @param m_strAddress the Address
     * @param m_strCountry the Country
     */
    public Location(String m_strAddress, Country m_strCountry) {
        setAddress(m_strAddress);
        this.m_strCountry = m_strCountry;
    }

    /**
     * Sets Address.
     *
     * @param m_strAddress the Address
     */
    private void setAddress(String m_strAddress) {
        if (m_strAddress == null || m_strAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Address!");
        }
        this.m_strAddress = m_strAddress;
    }

    /**
     * Gets Country.
     *
     * @return the Country
     */
    public Country getCountry() {
        return this.m_strCountry;
    }

    /**
     * Textual Location's Description.
     *
     * @return Textual Location's Description
     */
    @Override
    public String toString() {
        return String.format("Address: %s , Country: %s ", this.m_strAddress, this.m_strCountry.toString());
    }

}
