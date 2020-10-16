package team.baymax.model.appointment;

import static java.util.Objects.requireNonNull;

public class Description {
    public static final String MESSAGE_CONSTRAINTS = "Appointment descriptions can take any values, "
            + "and it should not be blank";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        if (isValidDescription(description)) {
            value = description;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.trim().length() != 0;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
