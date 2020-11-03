package team.baymax.ui.calendar;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.ui.UiPart;

/**
 * A ui for the appointment information page.
 */
public class SchedulePage extends UiPart<Region> {
    private static final String FXML = "calendar/SchedulePage.fxml";

    @FXML
    private StackPane schedulePanelPlaceholder;

    @FXML
    private Label dateLabel;

    private SchedulePanel schedulePanel;

    /**
     * Creates an {@code SchedulePage} with the given {@code ObservableList}.
     */
    public SchedulePage(AppointmentCalendar appointmentCalendar,
                        ObservableList<Appointment> appointments) {
        super(FXML);

        schedulePanel = new SchedulePanel(appointmentCalendar, appointments);
        schedulePanelPlaceholder.getChildren().add(schedulePanel.getRoot());
        dateLabel.textProperty().bind(appointmentCalendar.getDateProperty());
    }

    public SchedulePanel getSchedulePanel() {
        return schedulePanel;
    }
}
