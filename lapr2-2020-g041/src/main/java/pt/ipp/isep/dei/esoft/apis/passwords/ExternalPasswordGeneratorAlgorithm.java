package pt.ipp.isep.dei.esoft.apis.passwords;

/**
 * The Interface External Password Generator Algorithm.
 *
 * @author Berkelios
 */
public interface ExternalPasswordGeneratorAlgorithm {

    /**
     * Generate Password.
     *
     * @param strName  the Name
     * @param strEmail the Email
     * @return the Password
     */
    String generatePassword(String strName, String strEmail);
}
