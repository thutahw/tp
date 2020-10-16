package team.baymax.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import team.baymax.model.patient.Patient;

/**
 * A ui for the patient information page.
 */
public class PatientInfoPage extends UiPart<Region> {

    private static final String FXML = "PatientInfoPage.fxml";

    @FXML
    private StackPane patientListPanelPlaceholder;

    private PatientListPanel patientListPanel;

    /**
     * Creates a {@code PatientInfoPage} with the given {@code ObservableList}.
     */
    public PatientInfoPage(ObservableList<Patient> patientList) {
        super(FXML);
        patientListPanel = new PatientListPanel(patientList);
        patientListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());
    }

    public PatientListPanel getPatientListPanel() {
        return patientListPanel;
    }
}
