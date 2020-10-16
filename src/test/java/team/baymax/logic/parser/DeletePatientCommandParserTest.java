package team.baymax.logic.parser;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.patient.DeletePatientCommand;
import team.baymax.logic.parser.patient.DeletePatientCommandParser;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeletePatientCommandParserTest {

    private DeletePatientCommandParser parser = new DeletePatientCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeletePatientCommand(INDEX_FIRST_PATIENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePatientCommand.MESSAGE_USAGE));
    }
}
