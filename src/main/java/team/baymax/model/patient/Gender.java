package team.baymax.model.patient;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's gender in the appointment book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_CONSTRAINTS =
            "Gender should be specified as either M (male) or F (female). This field is case-insensitive.";

    public static final String VALIDATION_REGEX = "[mMfF]";

    private final String value;

    /**
     * Constructs an {@code Gender}.
     *
     * @param value A valid gender.
     */
    public Gender(String value) {
        requireNonNull(value);
        checkArgument(isValidGender(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if the gender is male.
     * @return
     */
    public boolean isMale() {
        return value.equals("m") || value.equals("M");
    }

    /**
     * Returns true if the gender is female.
     * @return
     */
    public boolean isFemale() {
        return value.equals("f") || value.equals("F");
    }

    @Override
    public String toString() {
        if (isMale()) {
            return "Male";
        }
        return "Female";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
