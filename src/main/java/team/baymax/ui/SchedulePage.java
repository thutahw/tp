package team.baymax.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import team.baymax.model.appointment.Appointment;

/**
 * A ui for the appointment information page.
 */
public class SchedulePage extends UiPart<Region> {

    private static final String FXML = "SchedulePage.fxml";

    @FXML
    private StackPane scheduleListPanelPlaceholder;

    private ScheduleListPanel scheduleListPanel;
    private ObservableList<Appointment> appointments;

    /**
     * Creates an {@code SchedulePage} with the given {@code ObservableList}.
     */
    public SchedulePage(ObservableList<Appointment> appointments) {
        super(FXML);
        this.appointments = appointments;

        initialize();
    }

    private void initialize() {
        scheduleListPanel = new ScheduleListPanel(appointments);
        scheduleListPanelPlaceholder.getChildren().add(scheduleListPanel.getRoot());
    }

    public ScheduleListPanel getScheduleListPanel() {
        return scheduleListPanel;
    }
}
