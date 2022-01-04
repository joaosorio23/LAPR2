package pt.ipp.isep.dei.esoft.pot.model.register;

import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.Freelancer;
import pt.ipp.isep.dei.esoft.pot.model.Location;
import pt.ipp.isep.dei.esoft.pot.model.enun.LevelOfExpertise;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Freelancers Register.
 *
 * @author Berkelios
 */
public class FreelancersRegister implements Serializable {

    /**
     * The Freelancers List.
     */
    private List<Freelancer> m_lstFreelancers;

    /**
     * Instantiates a new Freelancers Register.
     */
    public FreelancersRegister() {
        this.m_lstFreelancers = new ArrayList<>();
    }

    /**
     * Gets Freelancers.
     *
     * @return the Freelancers
     */
    public List<Freelancer> getFreelancers() {
        return this.m_lstFreelancers;
    }

    /**
     * New Freelancer.
     *
     * @param id               the ID
     * @param name             the Name
     * @param email            the Email
     * @param nif              the NIF
     * @param iban             the IBAN
     * @param local            the Location
     * @param levelOfExpertise the Level Of Expertise
     * @return the Freelancer
     */
    public Freelancer newFreelancer(String id, String name, String email, String nif, String iban, Location local, LevelOfExpertise levelOfExpertise) {
        return new Freelancer(id, name, email, nif, iban, local, levelOfExpertise);
    }

    /**
     * Add Freelancer.
     *
     * @param fr the Freelancer
     * @return boolean boolean
     */
    public boolean addFreelancer(Freelancer fr) {
        return this.m_lstFreelancers.add(fr);
    }

    /**
     * Register Freelancer.
     *
     * @param free the Freelancer
     * @return boolean boolean
     */
    public boolean registerFreelancer(Freelancer free) {

        if (validateFreelancer(free)) {
            return addFreelancer(free);
        }
        return false;
    }

    /**
     * Validate Freelancer.
     *
     * @param free the Freelancer
     * @return boolean boolean
     */
    public boolean validateFreelancer(Freelancer free) {

        if (ApplicationPOT.getInstance().getPlatform().getFacadeAuthorization().exitsUser(free.getEmail())) {
            throw new IllegalArgumentException("Freelancer's Email already exists!");
        } else {
            for (Freelancer Freelancer : this.m_lstFreelancers) {
                if (free.getId().equals(Freelancer.getId())) {
                    throw new IllegalArgumentException("Freelancer's ID already exists!");
                }
                if (free.getEmail().equals(Freelancer.getEmail())) {
                    throw new IllegalArgumentException("Freelancer's Email already exists!");
                }
                if (free.getNif().equals(Freelancer.getNif())) {
                    throw new IllegalArgumentException("Freelancer's NIF already exists!");
                }
                if (free.getIban().equals(Freelancer.getIban())) {
                    throw new IllegalArgumentException("Freelancer's IBAN already exists!");
                }
            }
        }
        return true;
    }

    /**
     * Gets Freelancer by ID.
     *
     * @param freeID the Freelancer ID
     * @return the Freelancer by ID
     */
    public Freelancer getFreelancerByID(String freeID) {
        for (Freelancer free : m_lstFreelancers) {
            if (free.getId().equals(freeID)) {
                return free;
            }
        }
        throw new IllegalArgumentException("Freelancer not found!");
    }
}
