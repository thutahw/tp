package team.baymax.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.calendar.Day;

/**
 * A ui for the calendar in the application.
 */
public class CalendarPage extends UiPart<Region> {

    private static final String FXML = "CalendarPage.fxml";

    @FXML
    private TilePane calendarGridView;

    @FXML
    private Label yearLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label dayLabel;

    private final AppointmentCalendar appointmentCalendar;

    public CalendarPage(AppointmentCalendar appointmentCalendar) {
        super(FXML);
        this.appointmentCalendar = appointmentCalendar;

        initialize();
    }

    private void initialize() {
        calendarGridView.setMaxWidth(550);

        yearLabel.textProperty().bind(appointmentCalendar.getYearProperty());
        monthLabel.textProperty().bind(appointmentCalendar.getMonthProperty());

        renderCalendar();
    }

    private void renderCalendar() {
        int day = 1;
        int numOfDaysInMonth = appointmentCalendar.getMonth().getNumOfDays();
        while (day <= numOfDaysInMonth) {
            CalendarViewCell cell = new CalendarViewCell(new Day(day));
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
                    + "-fx-border-color: #a5a5a5; "
                    + "-fx-padding: 5;"
                    + "-fx-border-insets: 5;"
                    + "-fx-background-insets: 5;";

        public CalendarViewCell(Day day) {
            super();

            setText(day.toString());

            setStyle(style);
            setMinHeight(75.0);
            setMinWidth(75.0);
            setMouseTransparent(true);
        }
    }

}
