package team.baymax.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import team.baymax.commons.core.index.Index;
import team.baymax.commons.util.StringUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.appointment.Description;
import team.baymax.model.patient.Gender;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Phone;
import team.baymax.model.patient.Remark;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Duration;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Time;
import team.baymax.model.util.datetime.Year;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "The index you have entered is invalid!";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code day} into a {@code Day} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static Day parseDayOfMonth(String day) throws ParseException {
        String trimmedDayOfMonth = day.trim();
        boolean isValidNumber = StringUtil.isNonZeroUnsignedInteger(trimmedDayOfMonth);

        if (isValidNumber && Day.isValidDay(Integer.parseInt(trimmedDayOfMonth))) {
            return new Day(Integer.parseInt(trimmedDayOfMonth));
        }

        throw new ParseException(Day.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses {@code month} into a {@code Month} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the given {@code month} is invalid.
     */
    public static Month parseMonth(String month) throws ParseException {
        String trimmedMonth = month.trim();
        boolean validNumber = StringUtil.isNonZeroUnsignedInteger(trimmedMonth);

        if (validNumber && Month.isValidMonth(Integer.parseInt(trimmedMonth))) {
            return new Month(Integer.parseInt(trimmedMonth));
        }

        if (Month.isValidMonth(trimmedMonth)) {
            return new Month(trimmedMonth);
        }

        throw new ParseException(Month.MESSAGE_CONSTRAINTS);
    }

    /**
     * Parses {@code year} into a {@code Year} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        String trimmedYear = year.trim();
        boolean invalidNumber = !StringUtil.isNonZeroUnsignedInteger(trimmedYear);
        if (invalidNumber || !Year.isValidYear(Integer.parseInt(trimmedYear))) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
        return new Year(Integer.parseInt(trimmedYear));
    }

    /**
     * Parses a {@code String nric} into an {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric.toUpperCase());
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);

        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);

        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String gender} into an {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);

        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);

        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String remark} into a {@code Remark}.
     */
    public static Remark parseRemark(String remark) {
        requireNonNull(remark);

        String trimmedRemark = remark.trim();
        return new Remark(trimmedRemark);
    }

    /**
     * Parses {@code String description} into a {@code Description}.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);

        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses {@code String dateTime} (e.g. "2020-10-12 23:39PM") into a {@code DateTime}.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);

        String trimmedDateTime = dateTime.trim();
        DateTime dateTimeObj;
        try {
            dateTimeObj = DateTime.fromString(trimmedDateTime);
        } catch (DateTimeParseException ex) {
            throw new ParseException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ParseException(ex.getMessage());
        }
        return dateTimeObj;
    }

    /**
     * Parses {@code String time} (e.g. "23:39PM") into a {@code Time}.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);

        String trimmedTime = time.trim();
        Time timeObj;
        try {
            timeObj = Time.fromString(trimmedTime);
        } catch (DateTimeParseException ex) {
            throw new ParseException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ParseException(ex.getMessage());
        }
        return timeObj;
    }

    /**
     * Parses {@code String duration} (e.g. "60") into a {@code Duration}.
     */
    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);

        String trimmedDuration = duration.trim();
        boolean invalidNumber = !StringUtil.isNonZeroUnsignedInteger(trimmedDuration);
        if (invalidNumber || !Duration.isValidDuration(Integer.parseInt(trimmedDuration))) {
            throw new ParseException(Duration.MESSAGE_CONSTRAINTS);
        }
        return new Duration(Integer.parseInt(trimmedDuration));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    public static Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        requireNonNull(tags);

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(parseTags(tagSet));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
