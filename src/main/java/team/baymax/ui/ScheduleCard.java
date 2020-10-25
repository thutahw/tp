package team.baymax.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import team.baymax.model.appointment.Appointment;

/**
 * An UI component that displays information of a scheduled {@code Appointment}.
 */
public class ScheduleCard extends UiPart<Region> {

    private static final String FXML = "ScheduleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label dateTime;
    @FXML
    private Label description;
    @FXML
    private Label patientName;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public ScheduleCard(Appointment appointment) {
        super(FXML);
        this.appointment = appointment;

        initialize();
    }

    private void initialize() {
        patientName.setText(appointment.getPatient().getName().fullName);
        dateTime.setText(appointment.getTime().toString());
        description.setText(appointment.getDescription().toString());
        appointment.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return appointment.equals(card.appointment);
    }
}
