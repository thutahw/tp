package team.baymax.ui.dashboard;

import java.util.Calendar;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import team.baymax.model.appointment.Appointment;
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
    private Label yearLabel;
    @FXML
    private Label monthLabel;
    @FXML
    private Label dayLabel;

    private AppointmentListPanel appointmentListPanel;

    /**
     * Constructs a dashboard with the given {@code ObservableList}.
     * @param appointmentsToday
     */
    public Dashboard(ObservableList<Appointment> appointmentsToday) {
        super(FXML);

        timePanel.getChildren().add(new DigitalClock());


        dayLabel.setText(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/");
        monthLabel.setText(Calendar.getInstance().get(Calendar.MONTH) + 1 + "/");
        yearLabel.setText(Calendar.getInstance().get(Calendar.YEAR) + "");

        appointmentListPanel = new AppointmentListPanel(appointmentsToday);
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());
    }

}
