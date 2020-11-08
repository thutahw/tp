package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;

public class Year {

    public static final String MESSAGE_CONSTRAINTS = "A year must be specified as a number 2000 or above.";

    private final int value;

    /**
     * Constructs a {@code Year} given a postive integer that must be 2000 or above.
     *
     */
    public Year(int value) {
        requireNonNull(value);

        if (isValidYear(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if a given number is a valid year.
     */
    public static boolean isValidYear(int n) {
        return n >= 2000;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && value == ((Year) other).value); // state check
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
