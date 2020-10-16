package team.baymax.model.util.uniquelist.exceptions;

/**
 * Signals that the operation will result in duplicate Patients (Patients are considered duplicates if they have
 * the same identity).
 */
public class DuplicateElementException extends RuntimeException {
    public DuplicateElementException() {
        super("Operation would result in duplicate elements");
    }
}
