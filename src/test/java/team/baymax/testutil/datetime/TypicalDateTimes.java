package team.baymax.testutil.datetime;

import team.baymax.model.util.datetime.DateTime;

public class TypicalDateTimes {
    public static final DateTime DATETIME1 = DateTime.fromString("12-12-2020 23:59");
    public static final DateTime DATETIME1_DUPLICATE = DateTime.fromString("12-12-2020 23:59");
    public static final DateTime DATETIME2 = DateTime.fromString("01-01-2020 20:00");
    public static final DateTime DATETIME3 = DateTime.fromString("01-01-2020 20:01");
    public static final DateTime DATETIME4 = DateTime.fromString("12-01-2020 10:00");
    public static final DateTime DATETIME5 = DateTime.fromString("11-10-2022 12:00");
    public static final DateTime DATETIME6 = DateTime.fromString("11-01-2020 12:45");
}
