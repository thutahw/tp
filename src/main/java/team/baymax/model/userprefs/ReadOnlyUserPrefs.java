package team.baymax.model.userprefs;

import java.nio.file.Path;

import team.baymax.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getPatientStorageFilePath();

    Path getAppointmentStorageFilePath();

}
