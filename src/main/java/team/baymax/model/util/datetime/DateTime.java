package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.AppUtil.checkArgument;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a DateTime object used in Baymax that wraps around the java.time.LocalDateTime
 * and provides basic functionality for storing and comparing.
 */
public class DateTime implements Comparable<DateTime> {
    public static final String MESSAGE_CONSTRAINTS = "DateTime entered must be in the form of <dd-MM-yyyy HH:mm>";

    private static final DateTimeFormatter FORMAT_INPUT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final DateTimeFormatter FORMAT_STORAGE = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final DateTimeFormatter FORMAT_OUTPUT = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mma");

    private final LocalDateTime dateTime;

    /**
     * Constructs a {@DateTime} given a {@code LocalDateTime}.
     *
     */
    public DateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Default constructor for {@code DateTime}, with input 2020-12-12 23:59
     */
    public DateTime() {
        this.dateTime = LocalDateTime.parse("12-12-2020 23:59", FORMAT_INPUT);
    }

    /**
     * Creates a new {@code DateTime} from a given formatted {@code dateTimeString}
     */
    public static DateTime fromString(String dateTimeString) {
        requireNonNull(dateTimeString);

        checkArgument(isValidDateTime((dateTimeString)), MESSAGE_CONSTRAINTS);
        return new DateTime(LocalDateTime.parse(dateTimeString, FORMAT_INPUT));
    }

    /**
     * Returns true if a given string is a valid DateTime format
     */
    public static boolean isValidDateTime(String test) {
        requireNonNull(test);
        try {
            LocalDateTime.parse(test, FORMAT_INPUT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Constructs a {@code DateTime} from a {@code Date} and {@code Time}.
     *
     */
    public static DateTime from(Date d, Time t) {
        requireAllNonNull(d, t);
        LocalDateTime dt = LocalDateTime.of(d.getDate(), t.getTime());
        return new DateTime(dt);
    }

    /**
     * Returns the dateTime in a storage format
     */
    public String getStorageFormat() {
        return dateTime.format(FORMAT_STORAGE);
    }

    public Day getDay() {
        return new Day(dateTime.getDayOfMonth());
    }

    public Month getMonth() {
        return new Month(dateTime.getMonthValue());
    }

    public Year getYear() {
        return new Year(dateTime.getYear());
    }

    public Date getDate() {
        return new Date(getDay(), getMonth(), getYear());
    }

    public Time getTime() {
        return new Time(dateTime.toLocalTime());
    }

    /**
     * Returns a new {@code DateTime} with the specified duration (in minutes) added.
     *
     * @param duration
     * @return
     */
    public DateTime plusMinutes(Duration duration) {
        return new DateTime(dateTime.plusMinutes(duration.value));
    }

    /**
     * Checks if this {@code DateTime} is after the specified date-time.
     *
     * @param other
     * @return
     */
    public boolean isAfter(DateTime other) {
        requireAllNonNull(other);
        return dateTime.isAfter(other.dateTime);
    }

    /**
     * Checks if this {@code DateTime} is before the specified date-time.
     *
     * @param other
     * @return
     */
    public boolean isBefore(DateTime other) {
        requireAllNonNull(other);
        return dateTime.isBefore(other.dateTime);
    }

    /**
     * Checks if this {@code DateTime} is equal to the specified date-time.
     *
     * @param other
     * @return
     */
    public boolean isEqual(DateTime other) {
        requireAllNonNull(other);
        return dateTime.isEqual(other.dateTime);
    }

    /**
     * Checks if this {@code Duration} extends the appointment to the next day.
     *
     */
    public boolean extendsUntilNextDay(Duration duration) {
        return !(getDate().equals(plusMinutes(duration).getDate()));
    }

    @Override
    public int compareTo(DateTime other) {
        requireAllNonNull(other);
        return dateTime.compareTo(other.dateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateTime
                && dateTime.equals(((DateTime) other).dateTime));
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    @Override
    public String toString() {
        return dateTime.format(FORMAT_OUTPUT);
    }
}
