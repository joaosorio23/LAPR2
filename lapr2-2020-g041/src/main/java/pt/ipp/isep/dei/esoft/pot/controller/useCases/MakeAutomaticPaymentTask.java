package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import pt.ipp.isep.dei.esoft.autorizacao.model.UserSession;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.*;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Make automatic payment task.
 *
 * @author Berkelios
 */
public class MakeAutomaticPaymentTask extends TimerTask {

    private Platform m_Platform;
    private Calendar cal;
    private Timer timer;
    private Organization org;
    private OrganizationsRegister rorgs;
    private String strUserEmail;
    private String subject = "Automatic Payment";
    private int m_intDay;

    /**
     * Instantiates a new Make automatic payment task.
     *
     * @param timer the timer
     * @param cal   the calendar
     */
    public MakeAutomaticPaymentTask(Timer timer, Calendar cal) {
        this.m_Platform = ApplicationPOT.getInstance().getPlatform();
        this.rorgs = this.m_Platform.getOrganizationsRegister();
        this.cal = cal;
        this.timer = timer;
        ApplicationPOT app = ApplicationPOT.getInstance();
        UserSession session = app.getCurrentSession();
        this.strUserEmail = session.getUserEmail();
        this.org = this.rorgs.getOrganizationByUserEmail(this.strUserEmail);
    }

    @Override
    public void run() {
        for (int i = org.getPaymentSize(); i < org.getPaymentTransactionList().getPaymentTransactions().size(); i++) {
            PaymentTransaction pay = org.getPaymentTransactionList().getPaymentTransactions().get(i);
            try {
                WriteToFile.writeToFile(pay, org);
                String message = String.format("The Payment for the realization of your task: %s,\nwith "
                        + "the value %.2f€ (%.2f%s) was made to your Bank Account (IBAN:%s).\nYour overall payment value is: %.2f€.\nBest Regards,\nT4J", pay.getTask().toString(),
                        pay.getValue(), this.m_Platform.getCoinConverter().convertCoin(pay.getValue(), pay.getFreelancer().getLocal().getCountry().multiplier),
                        pay.getFreelancer().getLocal().getCountry().symbol, pay.getFreelancer().getIban(), pay.getFreelancer().getOverallPayments());
                this.m_Platform.getEmailSender().sendEmail(Constants.T4J_NOTIFY_EMAIL, pay.getFreelancer().getEmail(), subject, message);
                org.setPaymentSize(org.getPaymentTransactionList().getPaymentTransactions().size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reschedule();
    }

    /**
     * Reschedules the new payment date.
     */
    public void reschedule() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int dayAux = this.m_intDay;

        if (dayAux == 30) {
            if (thisMonth == Calendar.FEBRUARY) {
                dayAux = org.February(thisYear, this.m_intDay);
            }
        } else if (dayAux == 31) {
            if (thisMonth == Calendar.APRIL || thisMonth == Calendar.JUNE || thisMonth == Calendar.SEPTEMBER || thisMonth == Calendar.NOVEMBER) {
                dayAux = 30;
            } else if (thisMonth == Calendar.FEBRUARY) {
                dayAux = org.February(thisYear, this.m_intDay);
            }
        }

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), dayAux, calendar.get(Calendar.HOUR_OF_DAY), 0);
        Date date = calendar.getTime();
        timer = new Timer();
        MakeAutomaticPaymentTask newTask = new MakeAutomaticPaymentTask(timer, calendar);

        System.out.println(date);
        timer.schedule(newTask, date);
    }

    /**
     * Sets day.
     *
     * @param day the day
     */
    public void setDay(int day) {
        this.m_intDay = day;
    }

}
