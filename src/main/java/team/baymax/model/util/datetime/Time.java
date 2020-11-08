package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time entered must be in the form of <HH:mm>. Example: 14:00";
    private static final DateTimeFormatter FORMAT_INPUT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FORMAT_OUTPUT = DateTimeFormatter.ofPattern("h:mm a");

    private final LocalTime time;

    /**
     * Constructs a {@code Time} from a {@code LocalDateTime}.
     */
    public Time(LocalTime time) {
        requireNonNull(time);
        this.time = time;
    }

    /**
     * Creates a new {@code Time} from a given formatted {@code timeString}
     */
    public static Time fromString(String timeString) {
        requireNonNull(timeString);
        checkArgument(isValidTime((timeString)), MESSAGE_CONSTRAINTS);
        return new Time(LocalTime.parse(timeString, FORMAT_INPUT));
    }

    /**
     * Returns true if a given string is a valid DateTime format
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, FORMAT_INPUT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time.format(FORMAT_OUTPUT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;

        return otherTime != null
                && time.equals(otherTime.time);
    }
}
