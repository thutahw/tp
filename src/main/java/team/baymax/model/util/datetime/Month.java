package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;

import java.text.DateFormatSymbols;

public class Month {

    public static final String MESSAGE_CONSTRAINTS = "A month must be specified as a number "
            + "between 1 (January) and 12 (December), or the name of the month itself or its short form "
            + "(jan, feb, mar, ...)";

    private final int value;

    /**
     * Constructs a {@code Month} given a valid month string.
     *
     */
    public Month(String monthString) {
        requireNonNull(monthString);
        if (isValidMonth(monthString)) {
            this.value = DateTimeUtil.getMonthIntFromString(monthString);
        } else {
            throw new IllegalArgumentException();
        }
    }

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

    /**
     * Returns true if a given string is a valid month.
     */
    public static boolean isValidMonth(String monthString) {
        requireNonNull(monthString);

        if (monthString.trim().length() == 0) {
            return false;
        }

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        String[] shortMonths = dfs.getShortMonths();

        for (String month : months) {
            if (monthString.equalsIgnoreCase(month)) {
                return true;
            }
        }

        for (String month : shortMonths) {
            if (monthString.equalsIgnoreCase(month)) {
                return true;
            }
        }

        return false;
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
        return DateTimeUtil.getMonthStringFromInt(value);
    }
}
