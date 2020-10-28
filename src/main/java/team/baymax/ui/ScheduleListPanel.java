package team.baymax.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import team.baymax.commons.core.LogsCenter;
import team.baymax.model.appointment.Appointment;

/**
 * Panel containing the list of appointments along a timeline.
 */
public class ScheduleListPanel extends UiPart<Region> {
    private static final String FXML = "ScheduleListPanel.fxml";

    @FXML
    protected ListView<Appointment> scheduleListView;

    protected final ObservableList<Appointment> appointments;
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);


    /**
     * Creates a {@code AppointmentListPanel} with the given {@code ObservableList}.
     */
    public ScheduleListPanel(ObservableList<Appointment> appointments) {
        super(FXML);
        this.appointments = appointments;

        initialize();
    }

    protected void initialize() {
        scheduleListView.setItems(appointments);
        scheduleListView.setCellFactory(listView -> new ScheduleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Appointment} using a {@code ScheduleCard}.
     */
    class ScheduleListViewCell extends ListCell<Appointment> {

        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);
            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ScheduleCard(appointment).getRoot());
            }
        }
    }

}
