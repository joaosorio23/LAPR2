/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.Freelancer;
import pt.ipp.isep.dei.esoft.pot.model.Platform;
import java.util.List;

/**
 * The type Analyse freelancers performance delays controller.
 *
 * @author Berkelios
 */
public class AnalyseFreelancersPerformancePaymentsController {

    private Platform m_oPlataforma;

    /**
     * Instantiates a new Analyse freelancers performance delays controller.
     */
    public AnalyseFreelancersPerformancePaymentsController() {
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
     * Gets list freelancers payments.
     *
     * @return the list freelancers payments
     */
    public List<String> getListFreelancersPayments() {
        return this.m_oPlataforma.getOrganizationsRegister().getListFreelancersPayments(this.getListFreelancersWithPayments());
    }

    /**
     * Gets payments interval.
     *
     * @return the payments interval
     */
    public List<Double> getPaymentsInterval() {
        return this.m_oPlataforma.getOrganizationsRegister().getPaymentsForEachInterval(this.getListFreelancersPayments());
    }

    /**
     * Get payments free list.
     *
     * @param id the id
     * @return the list
     */
    public List<Double> getPaymentsFree(String id) {

        return this.m_oPlataforma.getOrganizationsRegister().getPaymentsIntervalFree(id);
    }
}
