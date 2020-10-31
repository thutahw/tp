package team.baymax.model.patient;

/**
 * Represents a Patient's remark in the appointment book.
 * Guarantees: immutable;}
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remarks can take any values, and it should not be blank";

    private final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param value A valid remark.
     */
    public Remark(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
