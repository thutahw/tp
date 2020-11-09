package team.baymax.ui.appointment;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import team.baymax.commons.core.LogsCenter;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.util.datetime.DateTimeUtil;
import team.baymax.ui.UiPart;

/**
 * Panel containing the list of appointments.
 */
public class AppointmentListPanel extends UiPart<Region> {

    private static final String EMPTY_LIST_PLACEHOLDER_TEXT = "No appointments here.";

    private static final String FXML = "appointment/AppointmentListPanel.fxml";

    @FXML
    protected ListView<Appointment> appointmentListView;

    protected final ObservableList<Appointment> appointments;
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);


    /**
     * Creates a {@code AppointmentListPanel} with the given {@code ObservableList}.
     */
    public AppointmentListPanel(ObservableList<Appointment> appointments) {
        super(FXML);
        this.appointments = appointments;

        initialize();
    }

    protected void initialize() {
        appointmentListView.setPlaceholder(new Label(EMPTY_LIST_PLACEHOLDER_TEXT));
        appointmentListView.setItems(appointments);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());

        int scrollTarget = 0;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getDateTime().isBefore(DateTimeUtil.getCurrentDateTime())) {
                scrollTarget = i;
            }
        }
        appointmentListView.scrollTo(scrollTarget);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Appointment} using a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<Appointment> {

        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);
            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(appointment, getIndex() + 1).getRoot());
            }
        }
    }

}
