package pt.ipp.isep.dei.esoft.apis.converter;

/**
 * The interface Conversion.
 *
 * @author Berkelios
 */
public interface Conversion {

    /**
     * Convert coin.
     *
     * @param value      the value
     * @param multiplier the multiplier
     * @return the value
     */
    double convertCoin(double value, double multiplier);
}
