package pt.ipp.isep.dei.esoft.pot.model.enun;

import java.io.Serializable;

/**
 * The enum Country.
 *
 * @author Berkelios
 */
public enum Country implements Serializable {

    /**
     * Portugal.
     */
    PT(1, "€", "PT") {
        @Override
        public String toString() {
            return "Portugal";
        }
    },
    /**
     * United States.
     */
    US(1.14, "$", "US") {
        @Override
        public String toString() {
            return "USA";
        }
    },
    /**
     * Luxembourg.
     */
    LU(1, "€", "LU") {
        @Override
        public String toString() {
            return "Luxembourg";
        }
    },
    /**
     * France.
     */
    FR(1, "€", "FR") {
        @Override
        public String toString() {
            return "France";
        }
    },
    /**
     * Spain.
     */
    ES(1, "€", "ES") {
        @Override
        public String toString() {
            return "Spain";
        }
    },
    /**
     * United Kingdom.
     */
    GB(0.89, "£", "GB") {
        @Override
        public String toString() {
            return "UK";
        }
    },
    /**
     * Italy.
     */
    IT(1, "€", "IT") {
        @Override
        public String toString() {
            return "Italy";
        }
    },
    /**
     * Brazil.
     */
    BR(5.50, "$", "BR") {
        @Override
        public String toString() {
            return "Brasil";
        }
    },
    /**
     * Germany.
     */
    DE(1, "€", "DE") {
        @Override
        public String toString() {
            return "Germany";
        }
    },
    /**
     * Switzerland.
     */
    CH(1.08, "Fr", "CH") {
        @Override
        public String toString() {
            return "Switzerland";
        }
    },
    /**
     * Japan.
     */
    JP(122.47, "¥", "JP") {
        @Override
        public String toString() {
            return "Japan";
        }
    },
    /**
     * China.
     */
    CI(7.99, "¥", "CI") {
        @Override
        public String toString() {
            return "China";
        }
    },
    /**
     * The Other.
     */
    OT(1, "€", "-") {
        @Override
        public String toString() {
            return "Other";
        }
    },;

    /**
     * The Coin Symbol.
     */
    public String symbol;
    /**
     * The Coin Multiplier.
     */
    public double multiplier;
    /**
     * The Initial iban.
     */
    public String initialIBAN;

    /**
     * Instantiates a new Country.
     *
     * @param multiplier Coin Multiplier.
     * @param symbol Coin Symbol.
     * @param initialIBAN IBAN
     */
    Country(double multiplier, String symbol, String initialIBAN) {
        this.multiplier = multiplier;
        this.symbol = symbol;
        this.initialIBAN = initialIBAN;
    }

    /**
     * Return Country.
     *
     * @param country country
     * @return country country
     */
    public static Country setCountry(String country) {
        for (Country c : Country.values()) {
            if (c.toString().equals(country)) {
                return c;
            }
        }
        return Country.OT;
    }
}
