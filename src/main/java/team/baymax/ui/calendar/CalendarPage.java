package team.baymax.ui.calendar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.util.datetime.DateTimeUtil;
import team.baymax.model.util.datetime.Day;
import team.baymax.ui.UiPart;

// Make CalendarPage a property change listener

/**
 * A ui for the calendar in the application.
 */
public class CalendarPage extends UiPart<Region> implements PropertyChangeListener {

    private static final String FXML = "calendar/CalendarPage.fxml";

    @FXML
    private TilePane calendarGridView;

    @FXML
    private Label yearLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label dayLabel;

    private final AppointmentCalendar appointmentCalendar;

    /**
     * Constructs a {@Code CalendarPage} with the given {@code appointmentCalendar}.
     *
     */
    public CalendarPage(AppointmentCalendar appointmentCalendar) {
        super(FXML);
        this.appointmentCalendar = appointmentCalendar;
        appointmentCalendar.addPropertyChangeListener(this);

        initialize();
    }

    private void initialize() {
        calendarGridView.setMaxWidth(550);

        yearLabel.textProperty().bind(appointmentCalendar.getYearProperty());
        monthLabel.textProperty().bind(appointmentCalendar.getMonthProperty());

        renderCalendar();
    }

    private void renderCalendar() {
        calendarGridView.getChildren().setAll();

        int day = 1;
        int numOfDaysInMonth = DateTimeUtil.getNumOfDays(appointmentCalendar.getMonth(),
                appointmentCalendar.getYear());
        CalendarViewCell cell;
        while (day <= numOfDaysInMonth) {
            if (day == appointmentCalendar.getDay().getValue()) {
                cell = new CalendarViewCell(new Day(day), true);
            } else {
                cell = new CalendarViewCell(new Day(day), false);
            }
            calendarGridView.getChildren().add(cell);
            day++;
        }
    }

    static class CalendarViewCell extends Button {

        private static String style = "-fx-background-color: #ffffff; "
                    + "-fx-font-family: 'Adobe Gothic Std';"
                    + "-fx-text-fill: #0e0e0e;"
                    + "-fx-border-style: hidden hidden solid hidden;"
                    + "-fx-border-width: 5;"
                    + "-fx-border-color: %s; "
                    + "-fx-padding: 5;"
                    + "-fx-border-insets: 5;"
                    + "-fx-background-insets: 5;";

        private final String color;

        public CalendarViewCell(Day day, boolean isCurrent) {
            super();

            if (isCurrent) {
                this.color = "#97a2ff";
            } else {
                this.color = "#a5a5a5";
            }

            setText(day.toString());

            setStyle(String.format(style, this.color));
            setMinHeight(75.0);
            setMinWidth(75.0);
            setMouseTransparent(true);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        renderCalendar();
    }

}
