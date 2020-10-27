package team.baymax.model.modelmanagers;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a ListManager
 */
public interface ReadOnlyListManager<T> {
    /**
     * Returns an unmodifiable view of the list.
     * This list will not contain any duplicate elements.
     */

    ObservableList<T> getReadOnlyList();
}
