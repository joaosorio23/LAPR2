package pt.ipp.isep.dei.esoft.apis.emails;

import java.io.IOException;

/**
 * The Interface External Email Sender Algorithm.
 *
 * @author Berkelios
 */
public interface ExternalEmailSenderAlgorithm {

    /**
     * Send Email.
     *
     * @param EmailSender   the T4J's Email
     * @param EmailReceiver the User's Email
     * @param Subject       the Subject
     * @param message       the Message
     * @throws IOException the IO Exception
     */
    void sendEmail(String EmailSender, String EmailReceiver, String Subject, String message) throws IOException;
}
