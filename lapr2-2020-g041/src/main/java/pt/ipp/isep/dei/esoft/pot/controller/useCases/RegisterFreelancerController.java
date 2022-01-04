package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import javafx.scene.control.Alert;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.Alerts;
import pt.ipp.isep.dei.esoft.pot.model.Freelancer;
import pt.ipp.isep.dei.esoft.pot.model.Location;
import pt.ipp.isep.dei.esoft.pot.model.Platform;
import pt.ipp.isep.dei.esoft.pot.model.enun.Country;
import pt.ipp.isep.dei.esoft.pot.model.enun.LevelOfExpertise;
import pt.ipp.isep.dei.esoft.pot.model.register.FreelancersRegister;

/**
 * The type Register Freelancer Controller.
 *
 * @author Berkelios
 */
public class RegisterFreelancerController {

    /**
     * The Platform.
     */
    private Platform m_Platform;
    /**
     * The Freelancers Register.
     */
    private FreelancersRegister rfr;
    /**
     * The Freelancer.
     */
    private Freelancer free;

    /**
     * Instantiates a new Register Freelancer Controller.
     */
    public RegisterFreelancerController() {
        this.m_Platform = ApplicationPOT.getInstance().getPlatform();
        this.rfr = this.m_Platform.getFreelancersRegister();
    }

    /**
     * New Freelancer.
     *
     * @param id               the ID
     * @param name             the Name
     * @param email            the Email
     * @param nif              the NIF
     * @param iban             the IBAN
     * @param address          the Address
     * @param country          the Country
     * @param levelOfExpertise the Level Of Expertise
     * @return boolean boolean
     */
    public boolean newFreelancer(String id, String name, String email, String nif, String iban, String address, Country country, LevelOfExpertise levelOfExpertise) {
        try {
            Location local = Freelancer.newLocation(address, country);
            this.free = this.rfr.newFreelancer(id, name, email, nif, iban, local, levelOfExpertise);
            return this.rfr.validateFreelancer(this.free);
        } catch (IllegalArgumentException iae) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", iae.getMessage()).show();
            this.free = null;
            return false;
        }
    }

    /**
     * Register Freelancer.
     *
     * @return boolean boolean
     */
    public boolean registerFreelancer() {
        return this.rfr.registerFreelancer(this.free);
    }

    /**
     * Gets Freelancer String.
     *
     * @return the Freelancer String
     */
    public String getFreelancerString() {
        return this.free.toString();
    }
}
