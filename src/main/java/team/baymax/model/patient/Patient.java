package team.baymax.model.patient;

import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import team.baymax.model.tag.Tag;
import team.baymax.model.util.uniquelist.UniqueListElement;

/**
 * Represents a Patient in the appointment book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient implements UniqueListElement {

    // Identity fields
    private final Nric nric;

    // Data fields
    private final Name name;
    private final Phone phone;
    private final Gender gender;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Patient(Nric nric, Name name, Phone phone, Gender gender, Set<Tag> tags, Remark remark) {
        requireAllNonNull(nric, name, phone, gender, tags, remark);
        this.nric = nric;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.tags.addAll(tags);
        this.remark = remark;
    }

    public Nric getNric() {
        return nric;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Gender getGender() {
        return gender;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both patients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two patients.
     */
    @Override
    public boolean isSame(UniqueListElement other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;

        return other != null
                && otherPatient.getNric().equals(getNric());
    }

    /**
     * Returns true if both patients have the same identity fields (nric, name, gender).
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;

        return otherPatient.getNric().equals(nric)
                && otherPatient.getName().equals(name)
                && otherPatient.getGender().equals(gender)
                && otherPatient.getPhone().equals(phone)
                && otherPatient.getRemark().equals(remark)
                && otherPatient.getTags().equals(tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(nric, name, phone, gender, tags, remark);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName())
                .append(" | NRIC: ")
                .append(getNric())
                .append(" | Gender: ")
                .append(getGender())
                .append(" | Phone: ")
                .append(getPhone())
                .append("\n\t   Remark: ")
                .append(getRemark());
        if (getTags().size() > 0) {
            builder.append(" | Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
