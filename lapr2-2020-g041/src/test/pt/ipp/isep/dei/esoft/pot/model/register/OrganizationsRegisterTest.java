package pt.ipp.isep.dei.esoft.pot.model.register;

import org.apache.commons.math.MathException;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.*;
import pt.ipp.isep.dei.esoft.pot.model.enun.Country;
import pt.ipp.isep.dei.esoft.pot.model.enun.LevelOfExpertise;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Organizations register test.
 *
 * @author Berkelios
 */
public class OrganizationsRegisterTest {

    /**
     * Test of calculate Overall Percentage Of Delays method, of class
     * OrganizationsRegister.
     *
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    @Test
    public void testCalculateOverallPercentageOfDelays() throws ParseException, IOException {
        System.out.println("calculateOverallPercentageOfDelays");
        OrganizationsRegister ro = info("a", "b");
        double expResult = 100.0;
        double result = ro.calculateOverallPercentageOfDelays();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMedian method, of class OrganizationsRegister.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @Test
    public void testGetMedian() throws IOException, ParseException {
        System.out.println("getMedian");
        OrganizationsRegister ro = info("c", "d");
        String id = "12";
        double expResult = 3.0;
        double result = ro.getMean(id);
        assertEquals(expResult, result);

    }

    /**
     * Test of getPercentage method, of class OrganizationsRegister.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    @Test
    public void testGetPercentage() throws IOException, ParseException {
        System.out.println("getPercentage");
        OrganizationsRegister ro = info("e", "f");
        String id = "12";
        double expResult = 100.0;
        double result = ro.getPercentage(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateProbability method, of class OrganizationRegister
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     * @throws MathException  the math exception
     */
    @Test
    public void testCalculateProbability() throws IOException, ParseException, MathException {
        System.out.println("calculateProbability");
        OrganizationsRegister ro = info("e", "f");
        String id = "12";
        String expResult = "25,25 %";
        String result = ro.calculateProbability();
        assertEquals(expResult, result);
    }

    /**
     * Info organizations register.
     *
     * @param emailManager      the email manager
     * @param emailCollaborator the email collaborator
     * @return the organizations register
     * @throws ParseException the parse exception
     * @throws IOException    the io exception
     */
    public OrganizationsRegister info(String emailManager, String emailCollaborator) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse("01/08/2020");
        OrganizationsRegister ro = new OrganizationsRegister(ApplicationPOT.getInstance().getPlatform().getFacadeAuthorization());
        Organization org = new Organization("a", new Manager("a", emailManager), new Collaborator("d", emailCollaborator));
        ro.addOrganization(org);
        Task t1 = new Task("a", "a", "3", "4", "a");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate executionEnd = LocalDate.parse("13-05-20", dtf);
        t1.newTaskExecution(executionEnd, "3", "a");
        PaymentTransaction pt = org.getPaymentTransactionList().newPaymentTransaction("a", new Freelancer("12", "a", "i", "9", "9", new Location("b", Country.PT), LevelOfExpertise.JUNIOR), t1);
        org.getPaymentTransactionList().addPaymentTransaction(pt);

        return ro;
    }
}
