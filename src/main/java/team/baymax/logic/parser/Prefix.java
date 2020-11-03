package team.baymax.logic.parser;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    private final String prefix;
    private final String type;

    /**
     * Constructor for Prefix
     * @param prefix String to be matched with for prefix (e.g. "tag/")
     */
    public Prefix(String prefix) {
        this.prefix = prefix;
        this.type = "";
    }

    /**
     * Constructor for Prefix
     * @param prefix String to be matched with for prefix (e.g. "tag/")
     * @param type String describing type of prefix (e.g. "Tag" for tag/ prefix)
     */
    public Prefix(String prefix, String type) {
        this.prefix = prefix;
        this.type = type;
    }

    public String getPrefix() {
        return prefix;
    }

    public String toString() {
        return getPrefix();
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Prefix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Prefix otherPrefix = (Prefix) obj;
        return otherPrefix.getPrefix().equals(getPrefix());
    }
}
