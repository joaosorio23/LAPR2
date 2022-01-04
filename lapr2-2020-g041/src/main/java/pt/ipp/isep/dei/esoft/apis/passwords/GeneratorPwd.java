package pt.ipp.isep.dei.esoft.apis.passwords;

import java.io.Serializable;

/**
 * The type Generator Password.
 *
 * @author Berkelios
 */
public class GeneratorPwd implements ExternalPasswordGeneratorAlgorithm, Serializable {

    /**
     * Generate Password.
     *
     * @param strName the Name
     * @param strEmail the Email
     * @return the Password
     */
    @Override
    public String generatePassword(String strName, String strEmail) {
        String[] carat = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        StringBuilder pwd = new StringBuilder();
        for (int x = 1; x <= Math.random() * (10 - 7) + 7; x++) {
            int j = (int) (Math.random() * carat.length);
            pwd.append(carat[j]);
        }
        return pwd.toString();
    }
}
