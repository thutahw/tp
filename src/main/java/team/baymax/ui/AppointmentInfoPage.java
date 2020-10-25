package team.baymax.ui;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import team.baymax.model.appointment.Appointment;

/**
 * A ui for the appointment information page.
 */
public class AppointmentInfoPage extends UiPart<Region> {

    private static final String FXML = "AppointmentInfoPage.fxml";

    @FXML
    private StackPane appointmentListPanelPlaceholder;

    private AppointmentListPanel appointmentListPanel;
    private ObservableList<Appointment> appointmentList;

    /**
     * Creates an {@code AppointmentInfoPage} with the given {@code ObservableList}.
     */
    public AppointmentInfoPage(ObservableList<Appointment> appointmentList) {
        super(FXML);
        this.appointmentList = appointmentList;

        initialize();
    }

    private void initialize() {
        appointmentListPanel = new AppointmentListPanel(appointmentList);
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());
    }

    public AppointmentListPanel getAppointmentListPanel() {
        return appointmentListPanel;
    }
}
