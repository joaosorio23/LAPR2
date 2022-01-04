package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import javafx.scene.control.Alert;
import pt.ipp.isep.dei.esoft.autorizacao.model.UserSession;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.*;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

/**
 * The type Set payment day controller.
 *
 * @author Berkelios
 */
public class SetPaymentDayController {

    private static final int MIN_DAY = 0;
    private static final int MAX_DAY = 31;
    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 24;

    private Platform m_Platform;
    private Organization org;
    private OrganizationsRegister rorgs;
    private String strUserEmail;
    private int day;
    private int hour;

    /**
     * Instantiates a new Set payment day controller.
     */
    public SetPaymentDayController() {
        this.m_Platform = ApplicationPOT.getInstance().getPlatform();
        this.rorgs = this.m_Platform.getOrganizationsRegister();
    }

    /**
     * Sets payment schedule.
     *
     * @return the payment schedule
     */
    public boolean setPaymentSchedule() {
        try {
            this.org.setPaymentSchedule(this.day, this.hour);
            return true;
        } catch (Exception e) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", e.getMessage()).show();
            return false;
        }
    }

    /**
     * Convert to int boolean.
     *
     * @param m_intDay  the m int day
     * @param m_intHour the m int hour
     * @return the boolean
     */
    public boolean convertToInt(String m_intDay, String m_intHour) {
        try {
            if (!m_intDay.trim().isEmpty()) {
                try {
                    if ((MIN_DAY < Integer.parseInt(m_intDay)) && (Integer.parseInt(m_intDay) <= MAX_DAY)) {
                        this.day = Integer.parseInt(m_intDay);
                    } else {
                        throw new IllegalArgumentException("Invalid Day!");
                    }
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Invalid Day!");
                }
            } else {
                throw new IllegalArgumentException("Invalid Day!");
            }

            if (!m_intHour.trim().isEmpty()) {
                try {
                    if ((MIN_HOUR <= Integer.parseInt(m_intHour)) && (Integer.parseInt(m_intHour) <= MAX_HOUR)) {
                        this.hour = Integer.parseInt(m_intHour);
                        return paymentTransactions();
                    } else {
                        throw new IllegalArgumentException("Invalid Hour!");
                    }
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Invalid Hour!");
                }
            } else {
                throw new IllegalArgumentException("Invalid Hour!");
            }
        } catch (IllegalArgumentException iae) {
            Alerts.createAlert(Alert.AlertType.ERROR, "Data Error...", iae.getMessage()).show();
            return false;
        }
    }

    /**
     * Payment transactions boolean.
     *
     * @return the boolean
     */
    public boolean paymentTransactions() {
        ApplicationPOT app = ApplicationPOT.getInstance();
        UserSession session = app.getCurrentSession();
        this.strUserEmail = session.getUserEmail();
        this.org = this.rorgs.getOrganizationByUserEmail(this.strUserEmail);
        if (org.getPaymentTransactionList().getPaymentTransactions().isEmpty()) {
            throw new IllegalArgumentException("No PaymentTransactions Found!");
        } else {
            return true;
        }
    }
}
