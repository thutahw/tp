package seedu.address.model.appointment;

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
        value = description;
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

    public static boolean isValidDescription(String test) {
        return test.trim().length() != 0;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
