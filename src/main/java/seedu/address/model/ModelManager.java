package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;

/**
 * Represents the in-memory model of the appointment book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AppointmentBook appointmentBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Patient> filteredPatients;

    /**
     * Initializes a ModelManager with the given appointmentBook and userPrefs.
     */
    public ModelManager(ReadOnlyAppointmentBook appointmentBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(appointmentBook, userPrefs);

        logger.fine("Initializing with appointment book: " + appointmentBook + " and user prefs " + userPrefs);

        this.appointmentBook = new AppointmentBook(appointmentBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(this.appointmentBook.getPatientList());
    }

    public ModelManager() {
        this(new AppointmentBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAppointmentBookFilePath() {
        return userPrefs.getAppointmentBookFilePath();
    }

    @Override
    public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
        requireNonNull(appointmentBookFilePath);
        userPrefs.setAppointmentBookFilePath(appointmentBookFilePath);
    }

    //=========== AppointmentBook ================================================================================

    @Override
    public void setAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
        this.appointmentBook.resetData(appointmentBook);
    }

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        return appointmentBook;
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return appointmentBook.hasPatient(patient);
    }

    @Override
    public void deletePatient(Patient target) {
        appointmentBook.removePatient(target);
    }

    @Override
    public void addPatient(Patient patient) {
        appointmentBook.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        appointmentBook.setPatient(target, editedPatient);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedAppointmentBook}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        System.out.println("In model.getfilteredpatientlist()");
        System.out.println(filteredPatients);
        return filteredPatients;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return appointmentBook.equals(other.appointmentBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPatients.equals(other.filteredPatients);
    }

}
