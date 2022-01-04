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
 * The type Analyse tasks statistics controller.
 *
 * @author Admin
 */
public class AnalyseTasksStatisticsController {

    private OrganizationsRegister rorgs;
    private Platform m_oPlataforma;
    private List<String> tasks;
    private List<Freelancer> lstFree;
    private Organization org;

    /**
     * Instantiates a new Analyse tasks statistics controller.
     */
    public AnalyseTasksStatisticsController() {
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
    public ArrayList<Double> getListStats() {

        this.lstFree = sortByName(this.org.getListOfFreeTrans());
        this.tasks = this.org.getTasksStatistics(this.lstFree);

        return this.org.getDelaysForEachInterval(this.tasks);
    }

    /**
     * Get delays free list.
     *
     * @param id the id
     * @return the list
     */
    public List<Double> getDelaysFree(String id) {

        return this.org.getNumberOfDelaysFree(id);
    }

    /**
     * Gets free list.
     *
     * @return the free list
     */
    public List<String> getFreeList() {
        return this.tasks;
    }

    /**
     * Get lst free list.
     *
     * @return the list
     */
    public List<Freelancer> getLstFree() {
        return this.lstFree;
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
        return this.org.getTasksStatistics(this.org.getListSortedByPayments(this.org.getListOfFreeTrans()));
    }

}
