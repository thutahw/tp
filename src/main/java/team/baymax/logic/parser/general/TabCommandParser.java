package team.baymax.logic.parser.general;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.general.TabCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;

public class TabCommandParser implements Parser<TabCommand> {

    @Override
    public TabCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TabCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TabCommand.MESSAGE_INVALID_TAB), pe);
        }
    }
}
