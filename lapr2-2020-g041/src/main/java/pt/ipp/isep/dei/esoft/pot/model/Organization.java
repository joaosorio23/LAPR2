package pt.ipp.isep.dei.esoft.pot.model;

import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.list.PaymentTransactionList;
import pt.ipp.isep.dei.esoft.pot.model.list.TasksList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.MakeAutomaticPaymentTask;

/**
 * The type Organization.
 *
 * @author Berkelios
 */
public class Organization implements Serializable {

    /**
     * The Organizations's Tasks List.
     */
    private final TasksList m_TasksList;
    /**
     * The Organizations's Payments Transactions List.
     */
    private final PaymentTransactionList m_PaymentTransactionList;
    /**
     * The Organizations's Designation.
     */
    private String m_strDesignation;
    /**
     * The Organizations's Manager.
     */
    private Manager m_oManager;
    /**
     * The Organizations's Collaborator.
     */
    private Collaborator m_oCollaborator;

    private int m_paymentSize = 0;

    /**
     * Instantiates a new Organization.
     *
     * @param strDesignation the designation
     * @param oMan           the manager
     * @param oCollab        the collaborator
     */
    public Organization(String strDesignation, Manager oMan, Collaborator oCollab) {

        setDesignation(strDesignation);
        this.m_oManager = oMan;
        this.m_oCollaborator = oCollab;

        this.m_TasksList = new TasksList();
        this.m_PaymentTransactionList = new PaymentTransactionList();
    }

    /**
     * New Manager.
     *
     * @param strName  the Name
     * @param strEmail the Email
     * @return the Manager
     */
    public static Manager newManager(String strName, String strEmail) {
        return new Manager(strName, strEmail);
    }

    /**
     * New Collaborator.
     *
     * @param strName  the Name
     * @param strEmail the Email
     * @return the Collaborator
     */
    public static Collaborator newCollaborator(String strName, String strEmail) {
        return new Collaborator(strName, strEmail);
    }

    /**
     * Gets manager.
     *
     * @return the manager
     */
    public Manager getManager() {
        return this.m_oManager;
    }

    /**
     * Gets Collaborator.
     *
     * @return the Collaborator
     */
    public Collaborator getCollaborator() {
        return this.m_oCollaborator;
    }

    /**
     * Gets Designation.
     *
     * @return the Designation
     */
    public String getDesignation() {
        return this.m_strDesignation;
    }

    /**
     * Sets Designation.
     *
     * @param strDesignation the Designation
     */
    private void setDesignation(String strDesignation) {
        if (strDesignation == null || strDesignation.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Organization's Designation!");
        }
        this.m_strDesignation = strDesignation;
    }

    /**
     * Gets List of Freelancers Transactions.
     *
     * @return the List of Freelancers Transactions
     */
    public ArrayList<Freelancer> getListOfFreeTrans() {
        ArrayList<Freelancer> m_lstFreelancers = new ArrayList<>();
        for (PaymentTransaction pt : this.m_PaymentTransactionList.getPaymentTransactions()) {
            if (!m_lstFreelancers.contains(pt.getFreelancer())) {
                m_lstFreelancers.add(pt.getFreelancer());
            }
        }
        return m_lstFreelancers;
    }

    /**
     * Gets List Sorted by Payments.
     *
     * @param m_lstFreelancers the List with Freelancers
     * @return the List Sorted by Payments
     */
    public ArrayList<Freelancer> getListSortedByPayments(ArrayList<Freelancer> m_lstFreelancers) {
        ArrayList<Double> m_lstP = new ArrayList<>();
        for (Freelancer m_lstFreelancer : m_lstFreelancers) {
            double paymentSum = 0;
            for (PaymentTransaction pt : this.m_PaymentTransactionList.getPaymentTransactions()) {
                if (m_lstFreelancer.getId().equals(pt.getFreelancer().getId())) {
                    paymentSum = paymentSum + pt.getValue();
                }
            }
            m_lstP.add(paymentSum);
        }
        for (int i = 0; i < m_lstP.size() - 1; i++) {
            for (int z = i + 1; z < m_lstP.size(); z++) {
                if (m_lstP.get(z) > m_lstP.get(i)) {
                    Collections.swap(m_lstFreelancers, z, i);
                    Collections.swap(m_lstP, z, i);
                }
            }
        }
        return m_lstFreelancers;
    }

    /**
     * Gets Freelancers Payments Statistics done by Organization.
     *
     * @param m_lstFreelancers the List with Freelancers
     * @return the Freelancers Payments Statistics done by Organization
     */
    public List<String> getFreelancersPaymentsStatisticsDoneByOrg(ArrayList<Freelancer> m_lstFreelancers) {
        List<String> payments = new ArrayList<>();
        for (Freelancer m_lstFreelancer : m_lstFreelancers) {
            double mean = calculateMeanP(m_lstFreelancer);
            double deviation = calculateDeviationP(m_lstFreelancer, mean);

            payments.add(String.format("Freelancer: %s - ID: %s - Mean of the Payments: %.2f€ - Standard Deviation of the Payments: %.2f€", m_lstFreelancer.getName(), m_lstFreelancer.getId(), mean, deviation));

        }
        return payments;
    }

    /**
     * Calculate Mean Payments.
     *
     * @param free the Freelancer.
     * @return Mean Payments
     */
    public double calculateMeanP(Freelancer free) {
        double paymentSum = 0, cont = 0;
        for (PaymentTransaction pt : this.m_PaymentTransactionList.getPaymentTransactions()) {
            if (free.getId().equals(pt.getFreelancer().getId())) {
                paymentSum = paymentSum + pt.getValue();
                cont++;
            }
        }
        return paymentSum / cont;
    }

    /**
     * Calculate deviation p double.
     *
     * @param free   the free
     * @param median the median
     * @return the double
     */
    public double calculateDeviationP(Freelancer free, double median) {
        double counter = 0, deviation = 0;
        for (PaymentTransaction trans : this.m_PaymentTransactionList.getPaymentTransactions()) {
            if (free.getId().equals(trans.getFreelancer().getId())) {
                deviation = Math.pow(trans.getValue() - median, 2) + deviation;
                counter++;
            }
        }
        return Math.sqrt(deviation / counter);
    }

    /**
     * Get amount of transac free int.
     *
     * @param free the free
     * @return the int
     */
    public int getAmountOfTransacFree(Freelancer free) {
        int cont = 0;
        for (PaymentTransaction pay : this.m_PaymentTransactionList.getPaymentTransactions()) {
            if (pay.getFreelancer().equals(free)) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Gets Tasks Statistics.
     *
     * @param m_list the List with Freelancers
     * @return the Tasks Statistics
     */
    public List<String> getTasksStatistics(List<Freelancer> m_list) {
        List<String> tasks = new ArrayList<>();

        for (Freelancer freelancer : m_list) {
            double mean = getMeanT(freelancer);
            double deviation = getDeviationT(freelancer, mean);

            tasks.add(String.format("Freelancer: %s - ID: %s - Mean of the Delays: %.2f - Standard Deviation of the Delays: %.2f", freelancer.getName(), freelancer.getId(), mean, deviation));
        }

        double totalMean = getTotalMeanT();
        double totalDeviation = getTotalDeviationT(totalMean);

        if (m_list.isEmpty()) {
            totalMean = 0;
            totalDeviation = 0;
        }

        String totalMeanSt = String.valueOf(totalMean);
        String totalDeviationSt = String.valueOf(totalDeviation);

        tasks.add(totalMeanSt);
        tasks.add(totalDeviationSt);

        return tasks;
    }

    /**
     * Gets mean t.
     *
     * @param free the free
     * @return the mean t
     */
    public double getMeanT(Freelancer free) {
        double tasksSum = 0;
        int sum = 0;
        for (PaymentTransaction trans : this.m_PaymentTransactionList.getPaymentTransactions()) {
            if (free.getId().equals(trans.getFreelancer().getId())) {
                tasksSum = tasksSum + trans.getTask().getTaskExecution().getDelay();
                sum++;
            }
        }
        return tasksSum / sum;
    }

    /**
     * Gets deviation t.
     *
     * @param free   the free
     * @param median the median
     * @return the deviation t
     */
    public double getDeviationT(Freelancer free, Double median) {
        double deviation = 0;
        int counter = 0;
        for (PaymentTransaction trans : this.m_PaymentTransactionList.getPaymentTransactions()) {
            if (free.getId().equals(trans.getFreelancer().getId())) {
                deviation = Math.pow(trans.getTask().getTaskExecution().getDelay() - median, 2) + deviation;
                counter++;
            }
        }
        return Math.sqrt(deviation / counter);
    }

    /**
     * Gets total mean t.
     *
     * @return the total mean t
     */
    public double getTotalMeanT() {
        double totalMean = 0;
        int counterMedian = 0;
        for (PaymentTransaction trans : this.m_PaymentTransactionList.getPaymentTransactions()) {
            totalMean = totalMean + trans.getTask().getTaskExecution().getDelay();
            counterMedian++;
        }
        return totalMean / counterMedian;
    }

    /**
     * Gets total deviation t.
     *
     * @param totalMean the total mean
     * @return the total deviation t
     */
    public double getTotalDeviationT(double totalMean) {
        double totalDeviation = 0;
        int counterDeviation = 0;
        for (PaymentTransaction trans : this.m_PaymentTransactionList.getPaymentTransactions()) {
            totalDeviation = Math.pow(trans.getTask().getTaskExecution().getDelay() - totalMean, 2) + totalDeviation;
            counterDeviation++;
        }
        return Math.sqrt(totalDeviation / counterDeviation);
    }

    /**
     * Gets Delays for each Interval.
     *
     * @param tasks the Tasks
     * @return the Delays for each Interval
     */
    public ArrayList<Double> getDelaysForEachInterval(List<String> tasks) {
        ArrayList<Double> list = new ArrayList<>();
        double totalMedian = Double.parseDouble(tasks.get(tasks.size() - 2));
        double totalDeviation = Double.parseDouble(tasks.get(tasks.size() - 1));
        tasks.remove(tasks.size() - 1);
        tasks.remove(tasks.size() - 1);
        tasks.add(String.format("Mean of the Delays: %.2f\nStandard Deviation of the Delays: %.2f", totalMedian, totalDeviation));
        double cont1 = 0, cont2 = 0, cont3 = 0;
        for (PaymentTransaction t : this.m_PaymentTransactionList.getPaymentTransactions()) {
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
        list.add(cont1);
        list.add(cont2);
        list.add(cont3);
        list.add(totalMedian - totalDeviation);
        list.add(totalMedian + totalDeviation);

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
        for (PaymentTransaction t : this.m_PaymentTransactionList.getPaymentTransactions()) {
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
        list.add(cont1);
        list.add(cont2);
        list.add(cont3);
        list.add(mean - deviation);
        list.add(mean + deviation);

        return list;
    }

    /**
     * Gets Tasks List.
     *
     * @return the Tasks List
     */
    public TasksList getTasksList() {
        return this.m_TasksList;
    }

    /**
     * Gets Payment Transaction List.
     *
     * @return the Payment Transaction List
     */
    public PaymentTransactionList getPaymentTransactionList() {
        return this.m_PaymentTransactionList;
    }

    /**
     * Get payment size int.
     *
     * @return the int
     */
    public int getPaymentSize() {
        return this.m_paymentSize;
    }

    /**
     * Set payment size.
     *
     * @param paymentSize the payment size
     */
    public void setPaymentSize(int paymentSize) {
        this.m_paymentSize = paymentSize;
    }

    /**
     * Sets Payment Schedule.
     *
     * @param thisDay  the this day
     * @param thisHour the this hour
     */
    public void setPaymentSchedule(int thisDay, int thisHour) {
        Timer timer = new TimerT4J();
        Calendar cal = Calendar.getInstance();

        int thisYear = cal.get(Calendar.YEAR);
        int thisMonth = cal.get(Calendar.MONTH);
        int thisDayAux = cal.get(Calendar.DAY_OF_MONTH);
        int thisHourAux = cal.get(Calendar.HOUR_OF_DAY);
        int thisMinuteAux = cal.get(Calendar.MINUTE);

        int dayAux = thisDay;

        if (dayAux == 30) {
            if (thisMonth == Calendar.FEBRUARY) {
                dayAux = February(thisYear, dayAux);
            }
        } else if (dayAux == 31) {
            if (thisMonth == Calendar.APRIL || thisMonth == Calendar.JUNE || thisMonth == Calendar.SEPTEMBER || thisMonth == Calendar.NOVEMBER) {
                dayAux = 30;
            } else if (thisMonth == Calendar.FEBRUARY) {
                dayAux = February(thisYear, dayAux);
            }
        }

        cal.set(thisYear, thisMonth, dayAux, thisHour, 0);
        System.out.println(cal.getTime().toString());

        if (thisDayAux > dayAux) {
            cal.add(Calendar.MONTH, 1);
        } else if (thisDayAux == dayAux) {
            if (thisHourAux > thisHour) {
                cal.add(Calendar.MONTH, 1);
            } else if (thisHourAux == thisHour) {
                if (thisMinuteAux != 0) {
                    cal.add(Calendar.MONTH, 1);
                }
            }
        }

        Date d = cal.getTime();

        MakeAutomaticPaymentTask mapt = new MakeAutomaticPaymentTask(timer, cal);
        mapt.setDay(thisDay);

        timer.schedule(mapt, d);
    }

    /**
     * February int.
     *
     * @param thisYear the this year
     * @param thisDay  the this day
     * @return the int
     */
    public int February(int thisYear, int thisDay) {
        double leapYear = (thisYear % 4);
        int aux;
        if (leapYear == 0) {
            aux = (thisYear / 4);
            if ((aux % 100) != 0) {
                return 29;
            } else {
                return 28;
            }
        }
        return thisDay;
    }

    /**
     * Textual Organization's Description.
     *
     * @return Textual Organization's Description
     */
    @Override
    public String toString() {
        return String.format("Designation: %s - Manager: %s - Collaborator: %s", this.m_strDesignation, this.m_oManager, this.m_oCollaborator);
    }
}
