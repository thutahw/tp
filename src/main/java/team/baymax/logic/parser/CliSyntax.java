package team.baymax.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NRIC = new Prefix("nric/", "NRIC");
    public static final Prefix PREFIX_NAME = new Prefix("name/", "Name");
    public static final Prefix PREFIX_PHONE = new Prefix("phone/", "Phone number");
    public static final Prefix PREFIX_GENDER = new Prefix("gender/", "Gender");
    public static final Prefix PREFIX_TAG = new Prefix("tag/", "Tag");
    public static final Prefix PREFIX_REMARK = new Prefix("r/", "Remark");
    public static final Prefix PREFIX_INDEX = new Prefix("id/", "Index");
    public static final Prefix PREFIX_DATETIME = new Prefix("on/", "DateTime");
    public static final Prefix PREFIX_TIME = new Prefix("at/", "Time");
    public static final Prefix PREFIX_DURATION = new Prefix("dur/", "Duration");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/", "Description");

}
