package pt.ipp.isep.dei.esoft.pot.model;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.enun.Country;
import pt.ipp.isep.dei.esoft.pot.model.enun.LevelOfExpertise;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Organization test.
 *
 * @author Berkelios
 */
public class OrganizationTest {

    /**
     * Test of get Mean Total method, of class Organization.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @Test
    public void testGetMeanT() throws IOException, ParseException {
        System.out.println("getMeanT");
        Organization org = info("a", "b");
        Freelancer free = ApplicationPOT.getInstance().getPlatform().getFreelancersRegister().getFreelancers().get(0);
        double expResult = 3.0;
        double result = org.getMeanT(free);
        assertEquals(expResult, result);
    }

    /**
     * Test of get Deviation Total method, of class Organization.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @Test
    public void testGetDeviationT() throws IOException, ParseException {
        System.out.println("getDeviationT");
        Double median = 5.0;
        Organization org = info("c", "d");
        Freelancer free = ApplicationPOT.getInstance().getPlatform().getFreelancersRegister().getFreelancers().get(0);
        double expResult = 2.0;
        double result = org.getDeviationT(free, median);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotalMeanT method, of class Organization.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @Test
    public void testGetTotalMeanT() throws IOException, ParseException {
        System.out.println("getTotalMeanT");
        Organization org = info("e", "f");
        double expResult = 3.0;
        double result = org.getTotalMeanT();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotalDeviationT method, of class Organization.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @Test
    public void testGetTotalDeviationT() throws IOException, ParseException {
        System.out.println("getTotalDeviationT");
        double totalMean = 2.0;
        Organization org = info("g", "h");
        double expResult = 1.0;
        double result = org.getTotalDeviationT(totalMean);
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateMeanP method, of class Organization.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @Test
    public void testCalculateMeanP() throws IOException, ParseException {
        System.out.println("calculateMeanP");
        Organization org = info("i", "j");
        Freelancer free = ApplicationPOT.getInstance().getPlatform().getFreelancersRegister().getFreelancers().get(0);
        double expResult = 12.0;
        double result = org.calculateMeanP(free);
        assertEquals(expResult, result);

    }

    /**
     * Test of calculateDeviationP method, of class Organization.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @Test
    public void testCalculateDeviationP() throws IOException, ParseException {
        System.out.println("calculateDeviationP");
        Organization org = info("k", "l");
        Freelancer free = ApplicationPOT.getInstance().getPlatform().getFreelancersRegister().getFreelancers().get(0);
        double median = org.calculateMeanP(free);
        double expResult = 0.0;
        double result = org.calculateDeviationP(free, median);
        assertEquals(expResult, result);

    }

    /**
     * Info organization.
     *
     * @param emailManager      the email manager
     * @param emailCollaborator the email collaborator
     * @return the organization
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    public Organization info(String emailManager, String emailCollaborator) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse("01/08/2020");
        OrganizationsRegister ro = new OrganizationsRegister(ApplicationPOT.getInstance().getPlatform().getFacadeAuthorization());
        Organization org = new Organization("a", new Manager("a", emailManager), new Collaborator("d", emailCollaborator));
        ro.addOrganization(org);
        Task t1 = new Task("a", "a", "3", "4", "a");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate executionEnd = LocalDate.parse("13-05-20", dtf);
        t1.newTaskExecution(executionEnd, "3", "a");
        Freelancer f1 = new Freelancer("12", "a", "i", "9", "9", new Location("b", Country.PT), LevelOfExpertise.JUNIOR);
        ApplicationPOT.getInstance().getPlatform().getFreelancersRegister().addFreelancer(f1);
        PaymentTransaction pt = org.getPaymentTransactionList().newPaymentTransaction("a", f1, t1);
        org.getPaymentTransactionList().addPaymentTransaction(pt);

        return org;
    }

}
