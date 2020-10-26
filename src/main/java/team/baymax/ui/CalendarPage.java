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

        calendarGridView.setMaxWidth(500);

        int day = 1;

        yearLabel.textProperty().bind(appointmentCalendar.getYearProperty());
        monthLabel.textProperty().bind(appointmentCalendar.getMonthProperty());

        while (day <= appointmentCalendar.getMonth().getNumOfDays()) {
            CalendarViewCell cell = new CalendarViewCell(new Day(day));
            cell.setGreen();
            calendarGridView.getChildren().add(cell);
            day++;
        }
    }

    static class CalendarViewCell extends Button {

        public CalendarViewCell(Day day) {
            super();

            setText(day.getText());
            setMinHeight(75.0);
            setMinWidth(75.0);
            setMouseTransparent(true);
        }

        private void setGreen() {
            setStyle("-fx-background-color: #008000;");
        }

        private void setRed() {
            setStyle("-fx-background-color: #FF0000;");
        }

        private void setOrange() {
            setStyle("-fx-background-color: #FFA500;");
        }

    }

}
