package pt.ipp.isep.dei.esoft.apis.emails;

import pt.ipp.isep.dei.esoft.pot.model.Constants;

import java.io.*;

/**
 * The type Write email.
 *
 * @author Berkelios
 */
public class WriteEmail implements ExternalEmailSenderAlgorithm, Serializable {

    /**
     * Send Email.
     *
     * @param EmailSender the T4J's Email
     * @param EmailReceiver the User's Email
     * @param Subject the Subject
     * @param Message the Message
     * @throws IOException the IO Exception
     */
    @Override
    public void sendEmail(String EmailSender, String EmailReceiver, String Subject, String Message) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.EMAILS_PATH_FILE, true)));
        out.println("From: " + EmailSender);
        out.println("To: " + EmailReceiver);
        out.println();
        out.println("Subject: " + Subject);
        out.println();
        out.println("Message: " + Message);
        out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        out.close();
    }
}
