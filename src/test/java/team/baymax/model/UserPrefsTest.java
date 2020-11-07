package team.baymax.model;

import static team.baymax.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import team.baymax.model.userprefs.UserPrefs;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setPatientStorageFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setPatientStorageFilePath(null));
    }

}
