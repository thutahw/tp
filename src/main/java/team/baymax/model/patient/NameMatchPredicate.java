package team.baymax.model.patient;

import java.util.function.Predicate;

/**
 * Tests that a {@code Patient}'s {@code Name} matches the string given exactly.
 */
public class NameMatchPredicate implements Predicate<Patient> {
    private final Name name;

    public NameMatchPredicate(Name name) {
        this.name = name;
    }

    @Override
    public boolean test(Patient patient) {
        return name.equals(patient.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameMatchPredicate // instanceof handles nulls
                && name.equals(((NameMatchPredicate) other).name)); // state check
    }
}
