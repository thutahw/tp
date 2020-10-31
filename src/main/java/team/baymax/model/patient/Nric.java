package team.baymax.model.patient;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.AppUtil.checkArgument;

public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
            "Nric should be of the format @XXXXXXX#, where @ is a letter that can be 'S', 'T', 'F' or 'G', X is a"
                    + " numeric digit from 0 to 9 and # is a capitalised alphabetical character";

    public static final String VALIDATION_REGEX = "[STFG][0-9]{7}[A-Z]";

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
