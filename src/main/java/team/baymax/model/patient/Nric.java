package team.baymax.model.patient;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.AppUtil.checkArgument;

public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
            "Nric should be of the format @XXXXXXX#, where:\n"
                    + "(1) @ is a letter that can be 'S', 'T', 'F' or 'G',\n"
                    + "(2) X is a numeric digit from 0 to 9, and\n"
                    + "(3) # is a alphabetical character\n"
                    + "Note: there should be exactly 7 X's. The first and last letters are case-insensitive";

    public static final String VALIDATION_REGEX = "[stfgSTFG][0-9]{7}[a-zA-Z]";

    private final String value;

    /**
     * Constructs an {@code Nric}.
     *
     * @param value A valid nric.
     */
    public Nric(String value) {
        requireNonNull(value);
        checkArgument(isValidNric(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Nric
                && value.equals(((Nric) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
