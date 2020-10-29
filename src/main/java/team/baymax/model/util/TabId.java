package team.baymax.model.util;

import java.util.HashMap;
import java.util.Map;

public enum TabId {
    DASHBOARD(1, "dashboard"),
    CALENDAR(2, "calendar"),
    SCHEDULE(3, "schedule"),
    PATIENT(4, "patient"),
    APPOINTMENT(5, "appointment"),
    INFO(6, "info"),
    NONE(-1, "none");

    private static Map map = new HashMap<Integer, TabId>();
    private final int tabNumber;
    private final String tabName;

    TabId(int tabNumber, String tabName) {
        this.tabNumber = tabNumber;
        this.tabName = tabName;
    }

    static {
        for (TabId tabId : TabId.values()) {
            map.put(tabId.tabNumber, tabId);
        }
    }

    @SuppressWarnings("unchecked")
    public static TabId valueOf(int tabNumber) {
        return (TabId) map.get(tabNumber);
    }

    public String getTabName() {
        return tabName;
    }

    public int getTabNumber() {
        return tabNumber;
    }

    @Override
    public String toString() {
        return getTabName();
    }
}
