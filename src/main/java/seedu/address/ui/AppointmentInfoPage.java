package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.appointment.Appointment;


/**
 * A ui for the appointment information page.
 */
public class AppointmentInfoPage extends UiPart<Region> {

    private static final String FXML = "AppointmentInfoPage.fxml";

    @FXML
    private StackPane appointmentListPanelPlaceholder;

    private AppointmentListPanel appointmentListPanel;

    public AppointmentInfoPage(ObservableList<Appointment> appointmentList) {
        super(FXML);
        appointmentListPanel = new AppointmentListPanel(appointmentList);
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());
    }

    public AppointmentListPanel getAppointmentListPanel() {
        return appointmentListPanel;
    }
}
