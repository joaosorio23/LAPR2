/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import org.apache.commons.math.MathException;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.Freelancer;
import pt.ipp.isep.dei.esoft.pot.model.Platform;
import java.util.List;

/**
 * The type Analyse freelancers performance delays controller.
 *
 * @author Berkelios
 */
public class AnalyseFreelancersPerformanceDelaysController {

    private Platform m_oPlataforma;

    /**
     * Instantiates a new Analyse freelancers performance delays controller.
     */
    public AnalyseFreelancersPerformanceDelaysController() {
        this.m_oPlataforma = ApplicationPOT.getInstance().getPlatform();

    }

    /**
     * Gets list freelancers with payments.
     *
     * @return the list freelancers with payments
     */
    public List<Freelancer> getListFreelancersWithPayments() {
        return this.m_oPlataforma.getOrganizationsRegister().getListFreelancersWithPayments();
    }

    /**
     * Gets list freelancers delays.
     *
     * @return the list freelancers delays
     */
    public List<String> getListFreelancersDelays() {
        return this.m_oPlataforma.getOrganizationsRegister().getListFreelancersDelays(this.getListFreelancersWithPayments());
    }

    /**
     * Get delays interval list.
     *
     * @return the list
     */
    public List<Double> getDelaysInterval() {
        return this.m_oPlataforma.getOrganizationsRegister().getDelaysForEachInterval(this.getListFreelancersDelays());
    }

    /**
     * Get delays free list.
     *
     * @param id the id
     * @return the list
     */
    public List<Double> getDelaysFree(String id) {

        return this.m_oPlataforma.getOrganizationsRegister().getNumberOfDelaysFree(id);
    }

    /**
     * Gets probability.
     *
     * @return the probability
     * @throws MathException the math exception
     */
    public String getProbability() throws MathException {
        return this.m_oPlataforma.getOrganizationsRegister().calculateProbability();
    }
}
