package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;

public class Day {

    public static final String MESSAGE_CONSTRAINTS = "A day must be specified as a number "
            + "between the first and last day of the month, inclusive.";

    private final int value;

    /**
     * Constructs a {@code Day} given a postive integer that must be between 1 and 31.
     *
     */
    public Day(int value) {
        requireNonNull(value);

        if (isValidDay(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if a given number is a valid day.
     */
    public static boolean isValidDay(int n) {
        return n > 0 && n <= 31;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day // instanceof handles nulls
                && value == ((Day) other).value); // state check
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
