package pt.ipp.isep.dei.esoft.apis.converter;

import java.io.Serializable;

/**
 * The type Coin converter.
 *
 * @author Berkelios
 */
public class CoinConverter implements Conversion, Serializable {

    /**
     * Convert Coin
     *
     * @param value the value
     * @param multiplier the multiplier
     * @return the value
     */
    @Override
    public double convertCoin(double value, double multiplier) {
        return value * multiplier;
    }
}
