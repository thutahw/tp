package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;

public class Month {

    public static final String MESSAGE_CONSTRAINTS = "A month must be specified as a number "
            + "between 1 (January) and 12 (December).";

    private static int[] numOfDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private final int value;

    /**
     * Constructs a {@code Month} given a postive integer that must be between 1 and 12.
     *
     */
    public Month(int value) {
        requireNonNull(value);
        if (isValidMonth(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if a given number is a valid month.
     */
    public static boolean isValidMonth(int n) {
        return n > 0 && n <= 12;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Month // instanceof handles nulls
                && value == ((Month) other).value); // state check
    }

    @Override
    public String toString() {
        return DateTimeUtil.getMonthForInt(value - 1);
    }
}
