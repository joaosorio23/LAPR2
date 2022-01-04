package pt.ipp.isep.dei.esoft.pot.model;

import pt.ipp.isep.dei.esoft.apis.converter.CoinConverter;
import pt.ipp.isep.dei.esoft.apis.emails.WriteEmail;
import pt.ipp.isep.dei.esoft.apis.passwords.GeneratorPwd;
import pt.ipp.isep.dei.esoft.autorizacao.FacadeAuthorization;
import pt.ipp.isep.dei.esoft.pot.controller.useCases.SendEmailAutomaticallyTask;
import pt.ipp.isep.dei.esoft.pot.model.register.FreelancersRegister;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * The type Platform.
 *
 * @author Berkelios
 */
public class Platform implements Serializable {

    /**
     * The Facade Authorization.
     */
    private final FacadeAuthorization m_oAuthorization;
    /**
     * The Platform's Designation.
     */
    private String m_strDesignation;
    /**
     * The Freelancers Register.
     */
    private FreelancersRegister m_FreelancersRegister;
    /**
     * The Organizations Register.
     */
    private OrganizationsRegister m_OrganizationsRegister;

    private CoinConverter oConversion;
    private WriteEmail oEmailSender;
    private GeneratorPwd oPwdGenerator;

    /**
     * Instantiates a new Platform.
     *
     * @param strDesignation the Designation
     * @param Conversion     the Conversion
     * @param EmailSender    the Email Sender
     * @param PwdGenerator   the Password Generator
     */
    public Platform(String strDesignation, String Conversion, String EmailSender, String PwdGenerator) {

        try {
            Class<?> oConversion = Class.forName(Conversion);
            Class<?> oEmailSender = Class.forName(EmailSender);
            Class<?> oPwdGenerator = Class.forName(PwdGenerator);
            this.oConversion = ((CoinConverter) oConversion.newInstance());
            this.oEmailSender = ((WriteEmail) oEmailSender.newInstance());
            this.oPwdGenerator = ((GeneratorPwd) oPwdGenerator.newInstance());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        this.m_strDesignation = strDesignation;

        this.m_oAuthorization = new FacadeAuthorization();

        this.m_FreelancersRegister = new FreelancersRegister();
        this.m_OrganizationsRegister = new OrganizationsRegister(this.getFacadeAuthorization());
    }

    /**
     * Gets Facade Authorization.
     *
     * @return the Facade Authorization
     */
    public FacadeAuthorization getFacadeAuthorization() {
        return this.m_oAuthorization;
    }

    /**
     * Gets Freelancers Register.
     *
     * @return the Freelancers Register
     */
    public FreelancersRegister getFreelancersRegister() {
        return this.m_FreelancersRegister;
    }

    /**
     * Gets Organizations Register.
     *
     * @return the Organizations Register
     */
    public OrganizationsRegister getOrganizationsRegister() {
        return this.m_OrganizationsRegister;
    }

    /**
     * Gets Email Sender.
     *
     * @return the Email Sender
     */
    public WriteEmail getEmailSender() {
        return this.oEmailSender;
    }

    /**
     * Gets Password Generator.
     *
     * @return the Password Generator
     */
    public GeneratorPwd getPasswordGenerator() {
        return this.oPwdGenerator;
    }

    /**
     * Gets Coin Converter.
     *
     * @return the Coin Converter
     */
    public CoinConverter getCoinConverter() {
        return this.oConversion;
    }

    /**
     * Schedule timer.
     */
    public void scheduleTimer() {
        Timer timer = new TimerT4J();
        Calendar cal = Calendar.getInstance();
        int thisYear = cal.get(Calendar.YEAR);
        cal.set(thisYear, Constants.MONTH_OF_ANUAL_EMAIL_SENDER, Constants.DAY_OF_ANUAL_EMAIL_SENDER);
        Date d = cal.getTime();
        SendEmailAutomaticallyTask task = new SendEmailAutomaticallyTask(timer, cal);
        timer.schedule(task, d);
    }
}
