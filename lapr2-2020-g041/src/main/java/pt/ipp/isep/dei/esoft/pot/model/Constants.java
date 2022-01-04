package pt.ipp.isep.dei.esoft.pot.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * The type Constants.
 *
 * @author Berkelios
 */
public class Constants implements Serializable {

    /**
     * The constant PAPER ADMINISTRATOR.
     */
    public static final String PAPER_ADMINISTRATOR = "ADMINISTRATOR";
    /**
     * The constant PAPER COLLABORATOR ORGANIZATION.
     */
    public static final String PAPER_COLLABORATOR_ORGANIZATION = "COLLABORATOR_ORGANIZATION";
    /**
     * The constant PAPER MANAGER ORGANIZATION.
     */
    public static final String PAPER_MANAGER_ORGANIZATION = "MANAGER_ORGANIZATION";

    /**
     * The constant PARAMS FILE.
     */
    public static final String PARAMS_FILE = "config.properties";
    /**
     * The constant PARAMS PLATFORM DESIGNATION.
     */
    public static final String PARAMS_PLATFORM_DESIGNATION = "Plataforma.Designation";

    /**
     * The constant ICON ISEP.
     */
    public static final String ICON_ISEP = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9HHeeBYer-D8uNhIAaDy21bPA5-EnzwSMaHdcIBGGMAD3xrOgkA&s%22";
    /**
     * The constant BINARY FILE PATH.
     */
    public static final String BINARY_FILE_PATH = "Files\\Platform.ser";
    /**
     * The constant T4J SUPPORT EMAIL.
     */
    public static final String T4J_SUPPORT_EMAIL = "support@t4j.com";

    /**
     * The constant T4J NOTIFY EMAIL.
     */
    public static final String T4J_NOTIFY_EMAIL = "notify@t4j.com";

    /**
     * The constant EMAILS PATH FILE.
     */
    public static final String EMAILS_PATH_FILE = "Files\\e-mails.txt";

    /**
     * The constant MONTH OF ANUAL EMAIL SENDER.
     */
    public static final int MONTH_OF_ANUAL_EMAIL_SENDER = Calendar.DECEMBER;

    /**
     * The constant DAY OF ANUAL EMAIL SENDER.
     */
    public static final int DAY_OF_ANUAL_EMAIL_SENDER = 31;

    /**
     * The constant REGISTER PATH FILE
     */
    public static final String REGISTER_PATH_FILE = "Files\\Payments.txt";
}
