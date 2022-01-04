package pt.ipp.isep.dei.esoft.pot.model.enun;

import java.io.Serializable;

/**
 * The enum Level of Expertise.
 *
 * @author Berkelios
 */
public enum LevelOfExpertise implements Serializable {
    /**
     * The Junior.
     */
    JUNIOR(1) {
        @Override
        public String toString() {
            return "Junior";
        }
    },
    /**
     * The Senior.
     */
    SENIOR(2) {
        @Override
        public String toString() {
            return "Senior";
        }
    };

    /**
     * The Bonus.
     */
    public int bonus;

    /**
     * Instantiates a new Level Of Expertise.
     *
     * @param bonus the Bonus.
     */
    LevelOfExpertise(int bonus) {
        this.bonus = bonus;
    }

    /**
     * Sets level.
     *
     * @param level the level
     * @return the level
     */
    public static LevelOfExpertise setLevel(String level) {
        for (LevelOfExpertise l : LevelOfExpertise.values()) {
            if (l.toString().equals(level)) {
                return l;
            }
        }
        return null;
    }
}
