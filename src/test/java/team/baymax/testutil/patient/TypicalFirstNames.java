package team.baymax.testutil.patient;

import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;

import team.baymax.model.patient.Name;

public class TypicalFirstNames {
    public static final Name FIRST_NAME_ALICE = new Name("Alice");
    public static final Name FIRST_NAME_BENSON = new Name("Benson");
    public static final Name FIRST_NAME_CARL = new Name("Carl");
    public static final Name FIRST_NAME_DANIEL = new Name("Daniel");
    public static final Name FIRST_NAME_ELLE = new Name("Elle");
    public static final Name FIRST_NAME_FIONA = new Name("Fiona");
    public static final Name FIRST_NAME_GEORGE = new Name("George");
    public static final Name FIRST_NAME_HANSON = new Name("Hanson");

    public static final String VALID_FIRST_NAME_ALICE = " " + PREFIX_NAME + FIRST_NAME_ALICE;

    public static final String INVALID_FIRST_NAME_ALICE = " " + PREFIX_NAME + new Name("Alice Bob");


}
