package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
            "Nric should be of the format @XXXXXXX#, where @ is a letter that can be 'S', 'T', 'F' or 'G', X is a"
                    + " numeric digit from 0 to 9 and # is a capitalised alphabetical character";

    public static final String VALIDATION_REGEX = "[STFG][0-9]{7}[A-Z]";

    public final String nric;

    /**
     * Constructs an {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        this.nric = nric;
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Nric
                && nric.equals(((Nric) other).nric));
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }
}
