package team.baymax.ui.patient;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import team.baymax.commons.core.LogsCenter;
import team.baymax.model.patient.Patient;
import team.baymax.ui.UiPart;

/**
 * Panel containing the list of patients.
 */
public class PatientListPanel extends UiPart<Region> {

    private static final String EMPTY_LIST_PLACEHOLDER_TEXT = "No patients here.";

    private static final String FXML = "patient/PatientListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientListPanel.class);

    @FXML
    private ListView<Patient> patientListView;

    /**s
     * Creates a {@code PatientListPanel} with the given {@code ObservableList}.
     */
    public PatientListPanel(ObservableList<Patient> patientList) {
        super(FXML);
        patientListView.setPlaceholder(new Label(EMPTY_LIST_PLACEHOLDER_TEXT));
        patientListView.setItems(patientList);
        patientListView.setCellFactory(listView -> new PatientListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patient} using a {@code PatientCard}.
     */
    class PatientListViewCell extends ListCell<Patient> {
        @Override
        protected void updateItem(Patient patient, boolean empty) {
            super.updateItem(patient, empty);

            if (empty || patient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PatientCard(patient, getIndex() + 1).getRoot());
            }
        }
    }

}
