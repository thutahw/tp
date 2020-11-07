package team.baymax.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NRIC = new Prefix("nric/", "NRIC");
    public static final Prefix PREFIX_NAME = new Prefix("name/", "name");
    public static final Prefix PREFIX_PHONE = new Prefix("phone/", "phone number");
    public static final Prefix PREFIX_GENDER = new Prefix("gender/", "gender");
    public static final Prefix PREFIX_TAG = new Prefix("tag/", "tag");
    public static final Prefix PREFIX_REMARK = new Prefix("r/", "remark");
    public static final Prefix PREFIX_INDEX = new Prefix("id/", "index");
    public static final Prefix PREFIX_DATETIME = new Prefix("on/", "date and time");
    public static final Prefix PREFIX_TIME = new Prefix("at/", "time");
    public static final Prefix PREFIX_DURATION = new Prefix("dur/", "duration");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/", "description");

}
