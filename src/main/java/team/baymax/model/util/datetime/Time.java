package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time entered must be in the form of <HH:mm>";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final Hour hour;
    private final Minute minute;

    /**
     * Constructs a {@code Time} object given the {@code hour} and {@code minute}.
     *
     */
    public Time(Hour hour, Minute minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Constructs a {@code Time} from a {@code LocalDateTime}.
     */
    private Time(LocalDateTime dateTime) {
        this.time = dateTime;
    }

    /**
     * Creates a new {@code Time} from a given formatted {@code timeString}
     */
    public static Time fromString(String timeString) {
        requireNonNull(timeString);

        checkArgument(isValidTime((timeString)), MESSAGE_CONSTRAINTS);
        return new Time(LocalDateTime.parse(timeString, TIME_FORMAT));
    }

    /**
     * Returns true if a given string is a valid DateTime format
     */
    public static boolean isValidTime(String test) {
        try {
            LocalDateTime.parse(test, TIME_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return hour + ":" + minute;
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

        return other != null
                && otherTime.hour.equals(hour)
                && otherTime.minute.equals(minute);
    }
}
