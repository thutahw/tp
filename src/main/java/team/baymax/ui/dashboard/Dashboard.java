package team.baymax.ui.dashboard;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;
import team.baymax.ui.UiPart;
import team.baymax.ui.appointment.AppointmentListPanel;

/**
 * A ui for the dashboard in the application.
 */
public class Dashboard extends UiPart<Region> {

    private static final String FXML = "dashboard/Dashboard.fxml";

    @FXML
    private StackPane timePanel;
    @FXML
    private StackPane appointmentListPanelPlaceholder;
    @FXML
    private Label dateLabel;

    private AppointmentListPanel appointmentListPanel;
    private DigitalClock clock;

    /**
     * Constructs a dashboard with the given {@code ObservableList}.
     * @param appointmentsToday
     */
    public Dashboard(ObservableList<Appointment> appointmentsToday) {
        super(FXML);

        clock = new DigitalClock();
        timePanel.getChildren().add(clock);

        Date currentDate = new Date(
                new Day(AppointmentCalendar.getCurrentDay()),
                new Month(AppointmentCalendar.getCurrentMonth()),
                new Year(AppointmentCalendar.getCurrentYear()));

        dateLabel.setText(currentDate.toString());

        appointmentListPanel = new AppointmentListPanel(appointmentsToday);
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());
    }

}
