package team.baymax.model.appointment;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import team.baymax.commons.util.StringUtil;
import team.baymax.model.tag.Tag;


/**
 * Tests that an {@code Appointment}'s {@code Description} or {@code Tags} contain any of the keywords given.
 */
public class AppointmentContainsKeywordsPredicate implements Predicate<Appointment> {
    private final List<String> keywords;

    /**
     * Constructs the Predicate for testing.
     * @param keywords List of keywords to be tested against for matching.
     */
    public AppointmentContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Appointment appointment) {
        // iterate through set of tags
        String tagSet = "";

        for (Tag temp : appointment.getTags()) {
            tagSet += temp.getTagname() + " ";
        }

        String toMatch = tagSet + appointment.getDescription().toString();

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        toMatch, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same objectF
                || (other instanceof AppointmentContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AppointmentContainsKeywordsPredicate) other).keywords)); // state check
    }

}
