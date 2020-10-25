package team.baymax.model.calendar;

import static java.util.Objects.requireNonNull;

public class Day {

    public static final String MESSAGE_CONSTRAINTS = "A day must be specificied as an integer between 0 and 31.";

    private final int value;

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

    public String getText() {
        return "" + value;
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
}
