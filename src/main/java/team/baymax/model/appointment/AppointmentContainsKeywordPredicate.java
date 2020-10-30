package team.baymax.model.appointment;

import java.util.List;
import java.util.function.Predicate;

import team.baymax.commons.util.StringUtil;
import team.baymax.model.tag.Tag;

/**
 * Tests that an {@code Appointment}'s {@code Description} or {@code Tags} contain any of the keywords given.
 */
public class AppointmentContainsKeywordPredicate implements Predicate<Appointment> {
    private final List<String> keywords;

    public AppointmentContainsKeywordPredicate(List<String> keywords) {
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
                || (other instanceof AppointmentContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((AppointmentContainsKeywordPredicate) other).keywords)); // state check
    }

}
