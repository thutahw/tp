package team.baymax.ui.calendar;

import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import team.baymax.commons.core.LogsCenter;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.patient.Patient;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Time;
import team.baymax.ui.UiPart;

/**
 * Panel containing the list of appointments along a timeline.
 */
public class SchedulePanel extends UiPart<Region> implements PropertyChangeListener {
    private static final String FXML = "calendar/SchedulePanel.fxml";

    private static final String HALF_HOUR_LINE_STYLE_CLASS = "half-hour-line";
    private static final String FULL_HOUR_LINE_STYLE_CLASS = "full-hour-line";
    private static final String TIME_LABEL_STYLE_CLASS = "time-label";
    private static final String[] SCHEDULE_VIEW_CELL_COLORS = {"#ffb7b2", "#ffdac1", "#e2f0cb", "#b5ead7", "#c7ceea"};
    private static final String[] SCHEDULE_VIEW_CELL_BORDER_COLOR = {"#FF3A2C", "#FF7F29", "#7FAD32", "#33B385",
        "#5268BE"};
    private static final List<Patient> patientColorMapping = new ArrayList<>();

    private static final double leftCellAnchor = 80;
    private static final double rightCellPadding = 50;
    private static final double cellSpacing = 5;

    @FXML
    private ScrollPane scheduleTimelineScroll;

    @FXML
    private StackPane scheduleTimelineView;

    private final List<Line> lines = new ArrayList<>();
    private final List<Label> timeLabels = new ArrayList<>();
    private final List<ScheduleViewCell> viewCells = new ArrayList<>();

    private AppointmentCalendar appointmentCalendar;
    private ObservableList<Appointment> appointments;

    private Date currentDate;
    private final Logger logger = LogsCenter.getLogger(SchedulePanel.class);


    /**
     * Creates a {@code AppointmentListPanel} with the given {@code ObservableList}.
     */
    public SchedulePanel(AppointmentCalendar appointmentCalendar,
                         ObservableList<Appointment> appointments) {
        super(FXML);

        this.appointmentCalendar = appointmentCalendar;
        this.appointments = appointments;
        appointments.addListener((ListChangeListener<? super Appointment>) (it -> renderSchedule()));

        appointmentCalendar.addPropertyChangeListener(this);

        initialize();
    }

    protected void initialize() {
        scheduleTimelineView.setPrefWidth(1000);
        scheduleTimelineView.setPrefHeight(2000);

        renderSchedule();
    }

    private void renderSchedule() {
        scheduleTimelineScroll.setVvalue(0.0);
        scheduleTimelineView.getChildren().setAll();
        lines.clear();
        timeLabels.clear();
        viewCells.clear();

        currentDate = new Date(appointmentCalendar.getDay(),
                appointmentCalendar.getMonth(),
                appointmentCalendar.getYear());

        renderTimelineBackground();
        renderAppointmentEntries();

        double ymin = -1;
        for (ScheduleViewCell cell : viewCells) {
            double y1 = getTimeLocation(cell.getStartTime());

            if (ymin == -1 || y1 < ymin) {
                ymin = y1;
            }
        }

        if (ymin != -1) {
            scheduleTimelineScroll.setVvalue((ymin + 250) / scheduleTimelineView.getPrefHeight());
        }
    }

    private void renderTimelineBackground() {
        for (int i = 1; i < 24; i++) {
            createLine(HALF_HOUR_LINE_STYLE_CLASS);
            createLine(FULL_HOUR_LINE_STYLE_CLASS);
        }

        createLine(HALF_HOUR_LINE_STYLE_CLASS);

        int lineCount = lines.size();

        for (int i = 0; i < lineCount; i++) {
            Line line = lines.get(i);

            int hour = (i + 1) / 2;
            String hourString = hour < 10 ? "0" + hour : String.valueOf(hour);
            int minute = 0;

            boolean halfHourLine = (i % 2 == 0);
            if (halfHourLine) {
                minute = 30;
            }

            String minuteString = minute < 10 ? "0" + minute : String.valueOf(minute);

            Time time = Time.fromString(hourString + ":" + minuteString);
            double yy = scheduleTimelineView.snapPositionY(getTimeLocation(time));

            line.setStartX(scheduleTimelineView.snapPositionX(leftCellAnchor));
            line.setStartY(yy);
            line.setEndX(scheduleTimelineView.snapPositionX(scheduleTimelineView.getPrefWidth())
                    - rightCellPadding);
            line.setEndY(yy);

            if (!halfHourLine) {
                Label label = new Label(hourString + ":" + minuteString);
                label.getStyleClass().add(TIME_LABEL_STYLE_CLASS);
                scheduleTimelineView.setAlignment(label, Pos.TOP_LEFT);
                label.setTranslateY(yy - 10);
                label.setTranslateX(10);
                timeLabels.add(label);
                scheduleTimelineView.getChildren().add(label);
            }
        }
    }

    private void renderAppointmentEntries() {
        appointments.stream()
                .filter(new AppointmentMatchesDatePredicate(currentDate))
                .map(appointment -> createScheduleViewCell(appointment))
                .forEach(cell -> viewCells.add(cell));

        resolveOverlap();
    }

    private void resolveOverlap() {
        Collections.sort(viewCells, (c1, c2) -> {
            double c1y1 = getTimeLocation(c1.getStartTime());
            double c2y1 = getTimeLocation(c2.getStartTime());
            double c2y2 = getTimeLocation(c2.getEndTime());

            if (c1y1 < c2y1) {
                return -1;
            } else if (c1y1 > c2y2) {
                return +1;
            } else {
                return 0;
            }
        });

        List<ScheduleViewCluster> clusters = new ArrayList<>();
        ScheduleViewCluster cluster = null;

        for (ScheduleViewCell cell : viewCells) {
            if (cluster == null || !cluster.intersects(cell)) {
                cluster = new ScheduleViewCluster();
                clusters.add(cluster);
            }

            cluster.add(cell);
        }

        for (ScheduleViewCluster c : clusters) {
            c.resolve();
        }
    }

    private void createLine(String styleClass) {
        Line line = new Line();
        line.setManaged(false);
        line.setMouseTransparent(true);
        if (styleClass != null) {
            line.getStyleClass().add(styleClass);
        }
        lines.add(line);
        scheduleTimelineView.getChildren().add(line);
    }

    private ScheduleViewCell createScheduleViewCell(Appointment appointment) {
        String cellText = appointment.getPatient().getName() + " / " + appointment.getDescription();
        String cellColor = getViewCellColor(appointment);
        String cellBorderColor = getViewCellBorderColor(appointment);
        ScheduleViewCell cell = new ScheduleViewCell(
                appointment.getTime(), appointment.getEndDateTime().getTime(),
                cellText, cellColor, cellBorderColor);
        double yy = getTimeLocation(appointment.getTime());
        double height = getTimeLocation(appointment.getEndDateTime().getTime()) - yy;

        cell.setPrefHeight(height);
        cell.setPrefWidth(scheduleTimelineView.getPrefWidth() - rightCellPadding);
        cell.setTranslateX(leftCellAnchor);
        cell.setTranslateY(yy);

        scheduleTimelineView.getChildren().add(cell);
        scheduleTimelineView.setAlignment(cell, Pos.TOP_LEFT);

        return cell;
    }

    private double getTimeLocation(Time time) {
        double viewHeight = scheduleTimelineView.getPrefHeight();
        long startNano = LocalTime.MIN.toNanoOfDay();
        long endNano = LocalTime.MAX.toNanoOfDay();

        double npp = (endNano - startNano) / viewHeight;

        return ((int) ((time.getTime().toNanoOfDay() - startNano) / npp)) + 0.5;
    }

    private String getViewCellColor(Appointment appointment) {
        Patient patient = appointment.getPatient();
        if (!patientColorMapping.contains(patient)) {
            patientColorMapping.add(patient);
        }

        return SCHEDULE_VIEW_CELL_COLORS[
                patientColorMapping.indexOf(patient) % SCHEDULE_VIEW_CELL_COLORS.length];
    }

    private String getViewCellBorderColor(Appointment appointment) {
        Patient patient = appointment.getPatient();
        if (!patientColorMapping.contains(patient)) {
            patientColorMapping.add(patient);
        }

        return SCHEDULE_VIEW_CELL_BORDER_COLOR[
                patientColorMapping.indexOf(patient) % SCHEDULE_VIEW_CELL_BORDER_COLOR.length];
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        renderSchedule();
    }


    private class ScheduleViewCell extends Button {
        private static final String SCHEDULE_VIEW_CELL_STYLE_CLASS = "schedule-view-cell";

        private Time startTime;
        private Time endTime;

        public ScheduleViewCell(Time startTime, Time endTime, String text,
                                String color, String borderColor) {
            super();

            this.startTime = startTime;
            this.endTime = endTime;

            setTextAlignment(TextAlignment.LEFT);
            setAlignment(Pos.TOP_LEFT);
            setText(startTime.toString() + " - " + text);
            getStyleClass().add(SCHEDULE_VIEW_CELL_STYLE_CLASS);
            setStyle("-fx-background-color: " + color + ";"
                    + "-fx-border-color: " + borderColor + ";");
            setMouseTransparent(true);
        }

        public Time getStartTime() {
            return startTime;
        }

        public Time getEndTime() {
            return endTime;
        }
    }

    private class ScheduleViewColumn {
        private List<ScheduleViewCell> viewCells;

        public void add(ScheduleViewCell cell) {
            requireAllNonNull(cell);
            if (this.viewCells == null) {
                this.viewCells = new ArrayList<>();
            }
            this.viewCells.add(cell);
        }

        public boolean hasRoomFor(ScheduleViewCell cell) {
            if (this.viewCells == null) {
                return true;
            }

            double y1 = getTimeLocation(cell.getStartTime());
            double y2 = getTimeLocation(cell.getEndTime());

            for (ScheduleViewCell otherCell : this.viewCells) {
                double othery1 = getTimeLocation(otherCell.getStartTime());
                double othery2 = getTimeLocation(otherCell.getEndTime());

                if (y1 < othery2 && y2 > othery1) {
                    // two cells intersect
                    return false;
                }
            }
            return true;
        }

        public List<ScheduleViewCell> getViewCells() {
            return this.viewCells;
        }

    }

    private class ScheduleViewCluster {
        private List<ScheduleViewCell> viewCells;
        private List<ScheduleViewColumn> columns;

        private boolean areBoundsSet = false;
        private double ytop;
        private double ybot;

        public int getColumnsCount() {
            if (columns == null || columns.isEmpty()) {
                return 0;
            }

            return columns.size();
        }

        public void add(ScheduleViewCell cell) {
            if (this.viewCells == null) {
                this.viewCells = new ArrayList<>();
            }

            this.viewCells.add(cell);
            double y1 = getTimeLocation(cell.getStartTime());
            double y2 = getTimeLocation(cell.getEndTime());

            if (!areBoundsSet) {
                ytop = y1;
                ybot = y2;
                areBoundsSet = true;
            } else {
                ytop = Math.min(ytop, y1);
                ybot = Math.max(ybot, y2);
            }
        }

        public boolean intersects(ScheduleViewCell cell) {
            if (!areBoundsSet) {
                //initialise
                return true;
            }

            double y1 = getTimeLocation(cell.getStartTime());
            double y2 = getTimeLocation(cell.getEndTime());
            return (y1 < ybot) && (y2 > ytop);
        }

        public void resolve() {
            if (this.viewCells == null || this.viewCells.isEmpty()) {
                return;
            }

            columns = new ArrayList<>();
            columns.add(new ScheduleViewColumn());

            for (ScheduleViewCell cell : this.viewCells) {
                boolean added = false;

                for (ScheduleViewColumn column : columns) {
                    if (column.hasRoomFor(cell)) {
                        column.add(cell);
                        added = true;
                        break;
                    }
                }

                if (!added) {
                    ScheduleViewColumn column = new ScheduleViewColumn();
                    column.add(cell);
                    columns.add(column);
                }
            }

            double fullWidth = scheduleTimelineView.getPrefWidth() - leftCellAnchor - rightCellPadding;
            double cellWidth = fullWidth / getColumnsCount();

            for (int col = 0; col < getColumnsCount(); col++) {
                double shiftRight = cellWidth * col;
                ScheduleViewColumn column = columns.get(col);
                for (ScheduleViewCell cell : column.getViewCells()) {
                    cell.setPrefWidth(cellWidth - cellSpacing);
                    cell.setTranslateX(cell.getTranslateX() + shiftRight);
                }
            }
        }
    }

}
