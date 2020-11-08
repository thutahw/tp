package team.baymax.ui.appointment;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.ui.UiPart;

/**
 * An UI component that displays information of an {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "appointment/AppointmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Appointment appointment;
    private final int displayedIndex;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label dateTime;
    @FXML
    private Label description;
    @FXML
    private Label patientName;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label statusTag;
    @FXML
    private Label patientNric;
    @FXML
    private Label duration;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        this.displayedIndex = displayedIndex;

        initialize();
    }

    private void initialize() {
        id.setText(displayedIndex + ". ");
        patientName.setText(appointment.getPatient().getName().getFullName());
        patientNric.setText(appointment.getPatient().getNric().toString());
        phoneNumber.setText(appointment.getPatient().getPhone().toString());
        dateTime.setText(appointment.getDateTime().toString());
        duration.setText(appointment.getDuration().toString());
        description.setText(appointment.getDescription().toString());
        statusTag.setText(appointment.getStatus().text());
        statusTag.setStyle(statusTagStyles(appointment.getStatus()));
        appointment.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private String statusTagStyles(AppointmentStatus status) {
        return String.format("-fx-background-color: %s;", status.getColorCode());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        // state check
        AppointmentCard card = (AppointmentCard) other;
        return id.getText().equals(card.id.getText())
                && appointment.equals(card.appointment);
    }
}
