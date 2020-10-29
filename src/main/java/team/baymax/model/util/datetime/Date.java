package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import team.baymax.model.calendar.AppointmentCalendar;

public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Date entered must be in the form of <dd-MM-yyyy";
    private static final DateTimeFormatter FORMAT_INPUT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter FORMAT_OUTPUT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final LocalDate date;

    /**
     * Constructs a {@Date} given a {@code LocalDate}.
     *
     */
    public Date(LocalDate date) {
        this.date = date;
    }

    /**
     * Constructs a {@Date} given the {@code day}, {@code month} and {@code year}
     *
     */
    public Date(Day day, Month month, Year year) {
        date = LocalDate.of(year.getValue(), month.getValue(), day.getValue());
    }

    /**
     * Creates a new {@code Date} from a given formatted {@code dateString}
     */
    public static Date fromString(String dateString) {
        requireNonNull(dateString);

        checkArgument(isValidDate((dateString)), MESSAGE_CONSTRAINTS);
        return new Date(LocalDate.parse(dateString, FORMAT_INPUT));
    }

    /**
     * Returns true if a given string is a valid time format
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, FORMAT_INPUT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static Date fromCalendar(AppointmentCalendar calendar) {
        return new Date(calendar.getDay(), calendar.getMonth(), calendar.getYear());
    }

    public LocalDate getDate() {
        return date;
    }

    public Year getYear() {
        return new Year(date.getYear());
    }

    public Month getMonth() {
        return new Month(date.getMonthValue());
    }

    public Day getDay() {
        return new Day(date.getDayOfMonth());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;

        return otherDate != null
                && otherDate.date.equals(date);
    }

    @Override
    public String toString() {
        return date.format(FORMAT_OUTPUT);
    }
}
