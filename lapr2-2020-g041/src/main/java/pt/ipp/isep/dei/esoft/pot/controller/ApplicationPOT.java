package pt.ipp.isep.dei.esoft.pot.controller;

import pt.ipp.isep.dei.esoft.autorizacao.FacadeAuthorization;
import pt.ipp.isep.dei.esoft.autorizacao.model.UserSession;
import pt.ipp.isep.dei.esoft.pot.model.Constants;
import pt.ipp.isep.dei.esoft.pot.model.Platform;

import java.io.*;
import java.util.Properties;

/**
 * The type ApplicationPOT.
 *
 * @author paulomaio and Berkelios
 */
public class ApplicationPOT implements Serializable {

    // Inspired by https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    /**
     * The ApplicationPOT.
     */
    private static ApplicationPOT singleton = null;
    /**
     * The Platform.
     */
    private final Platform m_oPlatform;
    /**
     * The Facade Authorization.
     */
    private final FacadeAuthorization m_oAuthorization;

    /**
     * Instantiates a new ApplicationPOT.
     */
    private ApplicationPOT() {
        Properties props = getProperties();
        this.m_oPlatform = this.readPlatformSevFile(props);
        this.m_oAuthorization = this.m_oPlatform.getFacadeAuthorization();
        bootstrap();
    }

    /**
     * Gets Instance.
     *
     * @return the Instance
     */
    public static ApplicationPOT getInstance() {
        if (singleton == null) {
            synchronized (ApplicationPOT.class) {
                singleton = new ApplicationPOT();
            }
        }
        return singleton;
    }

    /**
     * Gets Platform.
     *
     * @return the Platform
     */
    public Platform getPlatform() {
        return this.m_oPlatform;
    }

    /**
     * Gets Current Session.
     *
     * @return the current session
     */
    public UserSession getCurrentSession() {
        return this.m_oAuthorization.getCurrentSession();
    }

    /**
     * Do Login.
     *
     * @param strId  the ID
     * @param strPwd the Password
     * @return boolean boolean
     */
    public boolean doLogin(String strId, String strPwd) {
        return this.m_oAuthorization.doLogin(strId, strPwd) != null;
    }

    /**
     * Do Logout.
     */
    public void doLogout() {
        this.m_oAuthorization.doLogout();
    }

    /**
     * Get Properties.
     *
     * @return the Properties
     */
    private Properties getProperties() {
        Properties props = new Properties();

        props.setProperty(Constants.PARAMS_PLATFORM_DESIGNATION, "Task for Joe");

        try {
            InputStream in = new FileInputStream(Constants.PARAMS_FILE);
            props.load(in);
            in.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return props;
    }

    /**
     * Record Users.
     */
    private void bootstrap() {
        this.m_oAuthorization.recordUserPaper(Constants.PAPER_ADMINISTRATOR);
        this.m_oAuthorization.recordUserPaper(Constants.PAPER_MANAGER_ORGANIZATION);
        this.m_oAuthorization.recordUserPaper(Constants.PAPER_COLLABORATOR_ORGANIZATION);

        this.m_oAuthorization.recordUserWithPaper("Administrativo", "adm@lapr.pt", "123456", Constants.PAPER_ADMINISTRATOR);
    }

    /**
     * Read Platform .sev File (Binary File).
     *
     * @param props the Properties
     * @return the Platform
     */
    public Platform readPlatformSevFile(Properties props) {
        try {
            FileInputStream fileIn = new FileInputStream(Constants.BINARY_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Platform plat = (Platform) in.readObject();
            in.close();
            fileIn.close();
            return plat;
        } catch (IOException i) {
            return new Platform(props.getProperty("Platform.Designation"), props.getProperty("Conversion.Class"), props.getProperty("EmailSender.Class"), props.getProperty("PasswordGenerator.Class"));
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
    }

    /**
     * Save Information.
     */
    public void saveInformation() {
        try {
            FileOutputStream fileOut = new FileOutputStream(Constants.BINARY_FILE_PATH);
            ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(this.m_oPlatform);
            outStream.close();
            fileOut.close();
        } catch (IOException fne) {
            fne.printStackTrace();
        }
    }
}
