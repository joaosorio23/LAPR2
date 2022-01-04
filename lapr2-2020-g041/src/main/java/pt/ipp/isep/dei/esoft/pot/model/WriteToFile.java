package pt.ipp.isep.dei.esoft.pot.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The type Write to file.
 */
public class WriteToFile {

    /**
     * Write to file.
     *
     * @param pay the pay
     * @param org the org
     * @throws IOException the io exception
     */
    public static void writeToFile(PaymentTransaction pay, Organization org) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.REGISTER_PATH_FILE, true)));
        out.println("Payment: " + pay + "\nMade by Organization:" + org);
        out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        out.close();
    }
}
