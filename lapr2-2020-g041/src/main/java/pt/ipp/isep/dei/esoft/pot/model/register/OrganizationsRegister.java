package pt.ipp.isep.dei.esoft.pot.model.register;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import pt.ipp.isep.dei.esoft.autorizacao.FacadeAuthorization;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The type Organizations Register.
 *
 * @author Berkelios
 */
public class OrganizationsRegister implements Serializable {

    /**
     * The Facade Authorization.
     */
    private final FacadeAuthorization m_oAuthorization;
    /**
     * The Organizations List.
     */
    private final List<Organization> m_lstOrganizations;

    /**
     * Instantiates a new Organizations Register.
     *
     * @param m_oAuthorization the Facade Authorization
     */
    public OrganizationsRegister(FacadeAuthorization m_oAuthorization) {
        this.m_oAuthorization = m_oAuthorization;
        this.m_lstOrganizations = new ArrayList<>();
    }

    /**
     * Gets Organizations List.
     *
     * @return the Organizations List
     */
    public List<Organization> getOrganizationsList() {
        return this.m_lstOrganizations;
    }

    /**
     * New Organization.
     *
     * @param strDesig the Designation
     * @param oMan     the Manager
     * @param oCollab  the Collaborator
     * @return the Organization
     */
    public Organization newOrganization(String strDesig, Manager oMan, Collaborator oCollab) {
        return new Organization(strDesig, oMan, oCollab);
    }

    /**
     * Register Organization.
     *
     * @param oOrganization the Organization
     * @return boolean boolean
     * @throws IOException the IO Exception
     */
    public boolean registerOrganization(Organization oOrganization) throws IOException {
        if (this.validateOrganization(oOrganization)) {
            Manager oMan = oOrganization.getManager();
            Collaborator oColLab = oOrganization.getCollaborator();
            String strPwdManager = ApplicationPOT.getInstance().getPlatform().getPasswordGenerator().generatePassword(oMan.getName(), oMan.getEmail());
            String strPwdCollaborator = ApplicationPOT.getInstance().getPlatform().getPasswordGenerator().generatePassword(oColLab.getName(), oColLab.getEmail());
            if (this.m_oAuthorization.recordUserWithPapers(oMan.getName(), oMan.getEmail(), strPwdManager, new String[]{Constants.PAPER_MANAGER_ORGANIZATION}) && this.m_oAuthorization.recordUserWithPapers(oColLab.getName(), oColLab.getEmail(), strPwdCollaborator, new String[]{Constants.PAPER_COLLABORATOR_ORGANIZATION})) {
                ApplicationPOT.getInstance().getPlatform().getEmailSender().sendEmail(Constants.T4J_SUPPORT_EMAIL, oMan.getEmail(), "Password to Access T4J's Platform", String.format("Hello %s,%nYour password to access T4J's Platform with the email %s is: %s.%nBest Regards,%nT4J", oMan.getName(), oMan.getEmail(), strPwdManager));
                ApplicationPOT.getInstance().getPlatform().getEmailSender().sendEmail(Constants.T4J_SUPPORT_EMAIL, oColLab.getEmail(), "Password to Access T4J's Platform", String.format("Hello %s,%nYour password to access T4J's Platform with the email %s is: %s.%nBest Regards,%nT4J", oColLab.getName(), oColLab.getEmail(), strPwdCollaborator));

                return addOrganization(oOrganization);
            }
        }
        return false;
    }

    /**
     * Add Organization.
     *
     * @param oOrganization the Organization
     * @return boolean boolean
     */
    public boolean addOrganization(Organization oOrganization) {
        return this.m_lstOrganizations.add(oOrganization);
    }

    /**
     * Validate Organization.
     *
     * @param oOrganization the Organization
     * @return boolean boolean
     */
    public boolean validateOrganization(Organization oOrganization) {
        if (oOrganization.getManager().getEmail().equals(oOrganization.getCollaborator().getEmail())) {
            throw new IllegalArgumentException("The Manager's and Collaborator's email cannot be the same!");
        }

        return true;
    }

    /**
     * Gets Organization by User's Email.
     *
     * @param email the Email
     * @return the Organization
     */
    public Organization getOrganizationByUserEmail(String email) {

        for (Organization org : this.m_lstOrganizations) {
            if (org.getCollaborator().getEmail().equals(email) || org.getManager().getEmail().equals(email)) {
                return org;
            }
        }
        return null;
    }

    /**
     * Calculate overall percentage of delays.
     *
     * @return the overall percentage of delays
     */
    public double calculateOverallPercentageOfDelays() {
        double amountOfOverallTasks = 0;
        double amountOfDelays = 0;
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction pt : org.getPaymentTransactionList().getPaymentTransactions()) {
                calendar.setTime(pt.getTask().getTaskExecution().getDate());
                if ((calendar.get(Calendar.YEAR)) == (now.get(Calendar.YEAR))) {
                    amountOfOverallTasks++;
                    if (pt.getTask().getTaskExecution().getDelay() > 0) {
                        amountOfDelays++;
                    }
                }
            }
        }
        return (amountOfDelays / amountOfOverallTasks) * 100;
    }

    /**
     * Gets median.
     *
     * @param id the ID
     * @return the median
     */
    public double getMean(String id) {
        double cont = 0;
        double delaysSum = 0;
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction pt : org.getPaymentTransactionList().getPaymentTransactions()) {
                String idFree = pt.getFreelancer().getId();
                if (id.equals(idFree)) {
                    calendar.setTime(pt.getTask().getTaskExecution().getDate());
                    if ((calendar.get(Calendar.YEAR)) == (now.get(Calendar.YEAR))) {
                        delaysSum = delaysSum + pt.getTask().getTaskExecution().getDelay();
                        cont++;
                    }
                }
            }
        }
        return (delaysSum / cont);
    }

    /**
     * Gets Percentage.
     *
     * @param id the ID
     * @return the Percentage
     */
    public double getPercentage(String id) {
        double totalDelays = 0;
        double totalTasks = 0;
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction pt : org.getPaymentTransactionList().getPaymentTransactions()) {
                String idFree = pt.getFreelancer().getId();
                if (id.equals(idFree)) {
                    calendar.setTime(pt.getTask().getTaskExecution().getDate());
                    if ((calendar.get(Calendar.YEAR)) == (now.get(Calendar.YEAR))) {
                        totalTasks++;
                        if (pt.getTask().getTaskExecution().getDelay() > 0) {
                            totalDelays++;
                        }
                    }
                }
            }
        }
        return (totalDelays / totalTasks) * 100;
    }

    /**
     * Gets list freelancers with payments.
     *
     * @return the list freelancers with payments
     */
    public List<Freelancer> getListFreelancersWithPayments() {
        List<Freelancer> allFree = new ArrayList<>();
        for (Organization org : this.m_lstOrganizations) {
            for (Freelancer free : org.getListOfFreeTrans()) {
                if (!(allFree.contains(free))) {
                    allFree.add(free);
                }
            }
        }
        return allFree;
    }

    /**
     * Gets list freelancers delays.
     *
     * @param listFreelancersWithPayments the list freelancers with payments
     * @return the list freelancers delays
     */
    public List<String> getListFreelancersDelays(List<Freelancer> listFreelancersWithPayments) {
        List<String> freeDelays = new ArrayList<>();
        double allDelay = 0, allCont = 0, allDeviation = 0;
        for (Freelancer free : listFreelancersWithPayments) {
            double totalDelayFree = 0;
            double contFree = 0;
            double deviationFree = 0;
            for (Organization org : this.m_lstOrganizations) {
                totalDelayFree = totalDelayFree + (org.getMeanT(free) * org.getAmountOfTransacFree(free));
                contFree = contFree + org.getAmountOfTransacFree(free);
            }
            for (Organization org : this.m_lstOrganizations) {
                for (PaymentTransaction trans : org.getPaymentTransactionList().getPaymentTransactions()) {
                    if (free.getId().equals(trans.getFreelancer().getId())) {
                        deviationFree = Math.pow(trans.getTask().getTaskExecution().getDelay() - totalDelayFree / contFree, 2) + deviationFree;
                    }
                }
            }
            allDelay = allDelay + totalDelayFree;
            allCont = allCont + contFree;
            freeDelays.add(String.format("The Freelancer %s - ID: %s has a mean of delays of %.2f and a standard deviation of %.2f", free.getName(), free.getId(), totalDelayFree / contFree, Math.sqrt(deviationFree / contFree)));
        }
        for (Freelancer freel : listFreelancersWithPayments) {
            for (Organization org : this.m_lstOrganizations) {
                for (PaymentTransaction trans : org.getPaymentTransactionList().getPaymentTransactions()) {
                    if (freel.getId().equals(trans.getFreelancer().getId())) {
                        allDeviation = Math.pow(trans.getTask().getTaskExecution().getDelay() - allDelay / allCont, 2) + allDeviation;
                    }
                }
            }
        }
        double median = allDelay / allCont;
        double deviation = allDeviation / allCont;
        if (listFreelancersWithPayments.isEmpty()) {
            median = 0;
            deviation = 0;
        }
        freeDelays.add(String.valueOf(median));
        freeDelays.add(String.valueOf(Math.sqrt(deviation)));
        return freeDelays;
    }

    /**
     * Gets list freelancers payments.
     *
     * @param listFreelancersWithPayments the list freelancers with payments
     * @return the list freelancers payments
     */
    public List<String> getListFreelancersPayments(List<Freelancer> listFreelancersWithPayments) {
        List<String> freePayments = new ArrayList<>();
        double allPayments = 0, allCont = 0, allDeviation = 0;
        for (Freelancer free : listFreelancersWithPayments) {
            double totalPaymentsFree = 0;
            double contFree = 0;
            double deviationFree = 0;
            for (Organization org : this.m_lstOrganizations) {
                totalPaymentsFree = totalPaymentsFree + (org.calculateMeanP(free) * org.getAmountOfTransacFree(free));
                contFree = contFree + org.getAmountOfTransacFree(free);
            }
            for (Organization org : this.m_lstOrganizations) {
                for (PaymentTransaction trans : org.getPaymentTransactionList().getPaymentTransactions()) {
                    if (free.getId().equals(trans.getFreelancer().getId())) {
                        deviationFree = Math.pow(trans.getValue() - totalPaymentsFree / contFree, 2) + deviationFree;
                    }
                }
            }
            allPayments = allPayments + totalPaymentsFree;
            allCont = allCont + contFree;
            freePayments.add(String.format("The Freelancer %s - ID: %s has a mean of payments of %.2f€ and a standard deviation of %.2f€", free.getName(), free.getId(), totalPaymentsFree / contFree, Math.sqrt(deviationFree / contFree)));
        }
        for (Freelancer freel : listFreelancersWithPayments) {
            for (Organization org : this.m_lstOrganizations) {
                for (PaymentTransaction trans : org.getPaymentTransactionList().getPaymentTransactions()) {
                    if (freel.getId().equals(trans.getFreelancer().getId())) {
                        allDeviation = Math.pow(trans.getValue() - allPayments / allCont, 2) + allDeviation;
                    }
                }
            }
        }
        double median = allPayments / allCont;
        double deviation = allDeviation / allCont;
        if (listFreelancersWithPayments.isEmpty()) {
            median = 0;
            deviation = 0;
        }
        freePayments.add(String.valueOf(median));
        freePayments.add(String.valueOf(Math.sqrt(deviation)));
        return freePayments;
    }

    /**
     * Gets delays for each interval.
     *
     * @param freeDelays the free delays
     * @return the delays for each interval
     */
    public ArrayList<Double> getDelaysForEachInterval(List<String> freeDelays) {
        ArrayList<Double> list = new ArrayList<>();
        double totalMedian = Double.parseDouble(freeDelays.get(freeDelays.size() - 2));
        double totalDeviation = Double.parseDouble(freeDelays.get(freeDelays.size() - 1));
        double cont1 = 0, cont2 = 0, cont3 = 0;
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction t : org.getPaymentTransactionList().getPaymentTransactions()) {
                if ((t.getTask().getTaskExecution().getDelay()) <= (totalMedian - totalDeviation)) {
                    cont1++;
                }
                if ((t.getTask().getTaskExecution().getDelay()) > (totalMedian - totalDeviation) && ((t.getTask().getTaskExecution().getDelay()) < (totalMedian + totalDeviation))) {
                    cont2++;
                }
                if ((t.getTask().getTaskExecution().getDelay()) >= (totalMedian + totalDeviation)) {
                    cont3++;
                }
            }
        }
        list.add(cont1);
        list.add(cont2);
        list.add(cont3);
        list.add(totalMedian - totalDeviation);
        list.add(totalMedian + totalDeviation);

        return list;
    }

    /**
     * Gets payments for each interval.
     *
     * @param freeDelays the free delays
     * @return the payments for each interval
     */
    public ArrayList<Double> getPaymentsForEachInterval(List<String> freeDelays) {
        ArrayList<Double> list = new ArrayList<>();
        double totalMedian = Double.parseDouble(freeDelays.get(freeDelays.size() - 2));
        double totalDeviation = Double.parseDouble(freeDelays.get(freeDelays.size() - 1));
        double cont1 = 0, cont2 = 0, cont3 = 0;
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction t : org.getPaymentTransactionList().getPaymentTransactions()) {
                if ((t.getValue() <= (totalMedian - totalDeviation))) {
                    cont1++;
                }
                if ((t.getValue()) > (totalMedian - totalDeviation) && (t.getValue() < (totalMedian + totalDeviation))) {
                    cont2++;
                }
                if (t.getValue() >= (totalMedian + totalDeviation)) {
                    cont3++;
                }
            }
        }
        list.add(cont1);
        list.add(cont2);
        list.add(cont3);
        list.add(totalMedian - totalDeviation);
        list.add(totalMedian + totalDeviation);

        return list;
    }

    /**
     * Gets payments interval free.
     *
     * @param id the id
     * @return the payments interval free
     */
    public List<Double> getPaymentsIntervalFree(String id) {
        ArrayList<Double> list = new ArrayList<>();
        Freelancer f = ApplicationPOT.getInstance().getPlatform().getFreelancersRegister().getFreelancerByID(id);
        double mean = getMeanP(f);
        double deviation = getDeviationP(f, mean);
        double cont1 = 0, cont2 = 0, cont3 = 0;
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction t : org.getPaymentTransactionList().getPaymentTransactions()) {
                if (f.equals(t.getFreelancer())) {
                    if ((t.getValue()) <= (mean - deviation)) {
                        cont1++;
                    }
                    if (((t.getValue()) > (mean - deviation)) && ((t.getValue()) < (mean + deviation))) {
                        cont2++;
                    }
                    if ((t.getValue()) >= (mean + deviation)) {
                        cont3++;
                    }
                }
            }
        }
        list.add(cont1);
        list.add(cont2);
        list.add(cont3);
        list.add(mean - deviation);
        list.add(mean + deviation);

        return list;
    }

    /**
     * Gets number of delays free.
     *
     * @param id the id
     * @return the number of delays free
     */
    public List<Double> getNumberOfDelaysFree(String id) {
        ArrayList<Double> list = new ArrayList<>();
        Freelancer f = ApplicationPOT.getInstance().getPlatform().getFreelancersRegister().getFreelancerByID(id);
        double mean = getMeanT(f);
        double deviation = getDeviationT(f, mean);
        double cont1 = 0, cont2 = 0, cont3 = 0;
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction t : org.getPaymentTransactionList().getPaymentTransactions()) {
                if (f.equals(t.getFreelancer())) {
                    if ((t.getTask().getTaskExecution().getDelay()) <= (mean - deviation)) {
                        cont1++;
                    }
                    if (((t.getTask().getTaskExecution().getDelay()) > (mean - deviation)) && ((t.getTask().getTaskExecution().getDelay()) < (mean + deviation))) {
                        cont2++;
                    }
                    if ((t.getTask().getTaskExecution().getDelay()) >= (mean + deviation)) {
                        cont3++;
                    }
                }
            }
        }
        list.add(cont1);
        list.add(cont2);
        list.add(cont3);
        list.add(mean - deviation);
        list.add(mean + deviation);

        return list;
    }

    private double getDeviationT(Freelancer free, double mean) {
        double deviationFree = 0, cont = 0;
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction trans : org.getPaymentTransactionList().getPaymentTransactions()) {
                if (free.getId().equals(trans.getFreelancer().getId())) {
                    deviationFree = Math.pow(trans.getTask().getTaskExecution().getDelay() - mean, 2) + deviationFree;
                    cont++;
                }
            }
        }
        return Math.sqrt(deviationFree / cont);
    }

    private double getMeanT(Freelancer free) {
        double totalDelayFree = 0;
        double contFree = 0;
        for (Organization org : this.m_lstOrganizations) {
            totalDelayFree = totalDelayFree + (org.getMeanT(free) * org.getAmountOfTransacFree(free));
            contFree = contFree + org.getAmountOfTransacFree(free);
        }
        return totalDelayFree / contFree;
    }

    private double getDeviationP(Freelancer free, double mean) {
        double deviationFree = 0, counter = 0;
        for (Organization org : this.m_lstOrganizations) {
            for (PaymentTransaction trans : org.getPaymentTransactionList().getPaymentTransactions()) {
                if (free.getId().equals(trans.getFreelancer().getId())) {
                    deviationFree = Math.pow(trans.getValue() - mean, 2) + deviationFree;
                    counter++;
                }
            }
        }
        return Math.sqrt(deviationFree / counter);
    }

    private double getMeanP(Freelancer free) {
        double totalPaymentsFree = 0;
        double contFree = 0;
        for (Organization org : this.m_lstOrganizations) {
            totalPaymentsFree = totalPaymentsFree + (org.calculateMeanP(free) * org.getAmountOfTransacFree(free));
            contFree = contFree + org.getAmountOfTransacFree(free);
        }
        return totalPaymentsFree / contFree;
    }

    /**
     * Calculate probability string.
     *
     * @return the string
     * @throws MathException the math exception
     */
    public String calculateProbability() throws MathException {
        double standardDeviation = 0;
        if (this.getListFreelancersWithPayments().size() > 0) {
            standardDeviation = Math.pow(1.5, 2) / this.getOverallPayments();
        }
        NormalDistributionImpl normalDistribution = new NormalDistributionImpl();
        normalDistribution.setMean(2);
        normalDistribution.setStandardDeviation(Math.sqrt(standardDeviation));
        return String.format("%.2f", (1 - normalDistribution.cumulativeProbability(3)) * 100) + " %";
    }

    /**
     * Get overall payments int.
     *
     * @return the int
     */
    public int getOverallPayments() {
        int soma = 0;
        for (Organization org : this.m_lstOrganizations) {
            soma = soma + org.getPaymentTransactionList().getPaymentTransactions().size();
        }
        return soma;
    }
}
