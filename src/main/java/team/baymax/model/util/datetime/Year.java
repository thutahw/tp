package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;

public class Year {

    public static final String MESSAGE_CONSTRAINTS = "A year must be specified as a number from 1900 to 2100.";

    private final int value;

    /**
     * Constructs a {@code Year} given a postive integer that must be between 1900 and 2100 (inclusive).
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
        return (n >= 1900 && n <= 2100);
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
