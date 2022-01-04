/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import pt.ipp.isep.dei.esoft.autorizacao.model.UserSession;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.Freelancer;
import pt.ipp.isep.dei.esoft.pot.model.Organization;
import pt.ipp.isep.dei.esoft.pot.model.Platform;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

/**
 * The type Analyse freelancer payments controller.
 *
 * @author Admin
 */
public class AnalyseFreelancerPaymentsController {

    private OrganizationsRegister rorgs;
    private Platform m_oPlataforma;
    private Organization org;

    /**
     * Instantiates a new Analyse freelancer payments controller.
     */
    public AnalyseFreelancerPaymentsController() {
        this.m_oPlataforma = ApplicationPOT.getInstance().getPlatform();
        this.rorgs = this.m_oPlataforma.getOrganizationsRegister();
        ApplicationPOT app = ApplicationPOT.getInstance();
        UserSession session = app.getCurrentSession();
        String strUserEmail = session.getUserEmail();
        this.org = this.rorgs.getOrganizationByUserEmail(strUserEmail);
    }

    /**
     * Gets list stats.
     *
     * @return the list stats
     */
    public List<String> getListStats() {

        ArrayList<Freelancer> m_lstFreelancers = sortByName(this.org.getListOfFreeTrans());

        return this.org.getFreelancersPaymentsStatisticsDoneByOrg(m_lstFreelancers);
    }

    private ArrayList<Freelancer> sortByName(ArrayList<Freelancer> listOfFreeTrans) {
        listOfFreeTrans.sort(Comparator.comparing(Freelancer::getName));
        return listOfFreeTrans;
    }

    /**
     * Gets free sorted by payment.
     *
     * @return the free sorted by payment
     */
    public List<String> getFreeSortedByPayment() {

        return this.org.getFreelancersPaymentsStatisticsDoneByOrg(this.org.getListSortedByPayments(this.org.getListOfFreeTrans()));
    }
}
