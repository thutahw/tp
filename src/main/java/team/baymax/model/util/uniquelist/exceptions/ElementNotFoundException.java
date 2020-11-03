package team.baymax.model.util.uniquelist.exceptions;

/**
 * Signals that the operation is unable to find the specified element.
 */
public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException() {
        super("The element you are looking for is not found");
    }

}
