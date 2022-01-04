/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.esoft.pot.model;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.pot.model.enun.Country;
import pt.ipp.isep.dei.esoft.pot.model.enun.LevelOfExpertise;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Payment transaction test.
 *
 * @author Berkelios
 */
public class PaymentTransactionTest {

    /**
     * Test of getValue method, of class PaymentTransaction.
     */
    @Test
    public void testGetValueJunior() {
        System.out.println("getValueJunior");
        Location location = new Location("Street", Country.PT);
        Freelancer freel = new Freelancer("ID", "Name", "Email", "123456789", "1234", location, LevelOfExpertise.JUNIOR);
        Task task = new Task("ID", "Desc", "10", "10", "Category");
        PaymentTransaction instance = new PaymentTransaction("ID", freel, task);
        double expResult = 100;
        double result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class PaymentTransaction.
     */
    @Test
    public void testGetValueSenior() {
        System.out.println("getValueSenior");
        Location location = new Location("Street", Country.PT);
        Freelancer freel = new Freelancer("ID", "Name", "Email", "123456789", "1234", location, LevelOfExpertise.SENIOR);
        Task task = new Task("ID", "Desc", "10", "10", "Category");
        PaymentTransaction instance = new PaymentTransaction("ID", freel, task);
        double expResult = 200;
        double result = instance.getValue();
        assertEquals(expResult, result);
    }
}
