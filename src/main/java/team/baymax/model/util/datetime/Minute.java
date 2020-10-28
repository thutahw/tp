package team.baymax.model.util.datetime;

public class Minute {

    private final int value;

    public Minute(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
