package team.baymax.model.calendar;

public class Hour {

    private final int value;

    public Hour(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
