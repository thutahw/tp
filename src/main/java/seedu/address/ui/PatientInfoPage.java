package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;

/**
 * A ui for the patient information page.
 */
public class PatientInfoPage extends UiPart<Region> {

    private static final String FXML = "PatientInfoPage.fxml";

    @FXML
    private StackPane personListPanelPlaceholder;

    private PersonListPanel personListPanel;

    /**
     * Creates a {@code PatientInfoPage} with the given {@code ObservableList}.
     */
    public PatientInfoPage(ObservableList<Person> personList) {
        super(FXML);
        personListPanel = new PersonListPanel(personList);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }
}
