package team.baymax.model.listmanagers;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a PatientManager
 */
public interface ReadOnlyListManager<T> {
    /**
     * Returns an unmodifiable view of the list.
     * This list will not contain any duplicate elements.
     */

    ObservableList<T> getReadOnlyList();
}
