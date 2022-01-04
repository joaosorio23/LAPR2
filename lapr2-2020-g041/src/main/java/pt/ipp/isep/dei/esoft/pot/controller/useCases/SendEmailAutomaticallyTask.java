package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.*;
import pt.ipp.isep.dei.esoft.pot.model.register.FreelancersRegister;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

import java.io.IOException;
import java.util.*;

/**
 * The type Send email automatically task.
 */
public class SendEmailAutomaticallyTask extends TimerTask {

    private ApplicationPOT m_oApp;
    private Platform m_oPlatform;
    private Calendar cal;
    private Timer timer;

    /**
     * Instantiates a new Send email automatically task.
     *
     * @param timer the timer
     * @param cal   the cal
     */
    public SendEmailAutomaticallyTask(Timer timer, Calendar cal) {
        this.m_oApp = ApplicationPOT.getInstance();
        this.m_oPlatform = this.m_oApp.getPlatform();
        this.cal = cal;
        this.timer = timer;
    }

    /**
     * Instantiates a new Send email automatically task.
     */
    public SendEmailAutomaticallyTask() {
        this.m_oApp = ApplicationPOT.getInstance();
        this.m_oPlatform = this.m_oApp.getPlatform();
    }

    /**
     * Gets freelancer to send email to.
     *
     * @return the freelancer to send email to
     * @throws IOException the io exception
     */
    public int getFreelancerToSendEmailTo() throws IOException {
        FreelancersRegister rfr = this.m_oPlatform.getFreelancersRegister();
        OrganizationsRegister ro = this.m_oPlatform.getOrganizationsRegister();
        List<Freelancer> freeList = rfr.getFreelancers();
        double overallPercentage = ro.calculateOverallPercentageOfDelays();
        int numberOfFreelancer = 0;
        for (Freelancer free : freeList) {
            String id = free.getId();
            double median = ro.getMean(id);
            double percentage = ro.getPercentage(id);
            if (median > 3 && percentage > overallPercentage) {
                numberOfFreelancer++;
                this.m_oPlatform.getEmailSender().sendEmail(Constants.T4J_NOTIFY_EMAIL, free.getEmail(), "Notify freelancers because of delays", String.format("Hello %s,%nYour percentage of delays this year is: %.2f%% and you have a mean task delay time of%n%.2f Hours.%nBest Regards,%nT4J", free.getName(), percentage, median));
            }
        }
        return numberOfFreelancer;
    }

    /**
     * Reschedule.
     */
    public void reschedule() {
        cal.set(cal.get(Calendar.YEAR) + 1, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        Date date = cal.getTime();

        timer.cancel();
        timer = new Timer();
        SendEmailAutomaticallyTask newTask = new SendEmailAutomaticallyTask(timer, cal);
        timer.schedule(newTask, date);
    }

    @Override
    public void run() {

        try {
            getFreelancerToSendEmailTo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        reschedule();
    }
}
