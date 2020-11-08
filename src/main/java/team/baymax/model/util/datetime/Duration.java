package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.AppUtil.checkArgument;

import java.util.function.Predicate;


public class Duration {
    public static final String MESSAGE_CONSTRAINTS = "Duration (in minutes) must be a valid integer spanning"
            + " not more than one day";

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 24 * 60;
    public static final Predicate<Integer> VALIDATION_PREDICATE = i -> i >= MIN_VALUE && i <= MAX_VALUE;

    public final Integer value;

    /**
     * Constructs a {@Duration} from an integer.
     *
     */
    public Duration(Integer duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        this.value = duration;
    }

    public int numberOfHours() {
        return value / 60;
    }

    public int numberOfMinutes() {
        return value % 60;
    }

    /**
     * Returns true if a given Integer is a valid duration.
     */
    public static boolean isValidDuration(Integer dur) {
        return VALIDATION_PREDICATE.test(dur);
    }

    @Override
    public String toString() {
        if (numberOfHours() == 0) {
            return String.format("%d Minute(s)", numberOfMinutes());
        } else if (numberOfMinutes() == 0) {
            return String.format("%d Hour(s)", numberOfHours());
        }
        return String.format("%1$d Hour(s) %2$d Minute(s)", numberOfHours(), numberOfMinutes());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && value.equals(((Duration) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
