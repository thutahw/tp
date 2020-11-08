package team.baymax.ui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.patient.Patient;
import team.baymax.model.util.TabId;
import team.baymax.ui.appointment.AppointmentInfoPage;
import team.baymax.ui.calendar.CalendarPage;
import team.baymax.ui.calendar.SchedulePage;
import team.baymax.ui.dashboard.Dashboard;
import team.baymax.ui.patient.PatientInfoPage;

public class SideTabPane extends UiPart<Region> {

    private static final String FXML = "SideTabPane.fxml";

    @FXML
    private TabPane sideTabPane;
    @FXML
    private Tab dashboardTab;
    @FXML
    private Tab calendarTab;
    @FXML
    private Tab scheduleTab;
    @FXML
    private Tab patientInfoTab;
    @FXML
    private Tab appointmentInfoTab;
    @FXML
    private Tab infoTab;
    @FXML
    private StackPane dashboardTabContentPlaceholder;
    @FXML
    private StackPane patientTabContentPlaceholder;
    @FXML
    private StackPane appointmentTabContentPlaceholder;
    @FXML
    private StackPane calendarTabContentPlaceholder;
    @FXML
    private StackPane infoTabContentPlaceholder;
    @FXML
    private StackPane scheduleTabContentPlaceholder;

    private ObservableList<Patient> patients;
    private ObservableList<Appointment> appointments;
    private AppointmentCalendar calendar;

    /**
     * Constructs a {@SideTabPane}.
     *
     */
    public SideTabPane(ObservableList<Patient> patients, ObservableList<Appointment> appointments,
                       AppointmentCalendar calendar) {
        super(FXML);
        this.patients = patients;
        this.appointments = appointments;
        this.calendar = calendar;

        initialize();
    }

    private void initialize() {

        PatientInfoPage patientInfoPage = new PatientInfoPage(patients);
        patientTabContentPlaceholder.getChildren().add(patientInfoPage.getRoot());

        AppointmentInfoPage appointmentInfoPage = new AppointmentInfoPage(appointments);
        appointmentTabContentPlaceholder.getChildren().add(appointmentInfoPage.getRoot());

        CalendarPage calendarPage = new CalendarPage(calendar);
        calendarTabContentPlaceholder.getChildren().add(calendarPage.getRoot());

        SchedulePage schedulePage = new SchedulePage(calendar, appointments);
        scheduleTabContentPlaceholder.getChildren().add(schedulePage.getRoot());

        Dashboard dashboard = new Dashboard(appointments);
        dashboardTabContentPlaceholder.getChildren().add(dashboard.getRoot());

        InfoPage infoPage = new InfoPage();
        infoTabContentPlaceholder.getChildren().add(infoPage.getRoot());

        sideTabPane.addEventFilter(
            MouseEvent.ANY,
            new EventHandler<MouseEvent>() {
                public void handle(final MouseEvent mouseEvent) {
                    // prevent any children from acting on the mouse event
                    mouseEvent.consume();
                }
            });
    }

    /**
     * Switches between the tabs in the left-side tab bar.
     * @param tabId tab identifier corresponding to the tab
     */
    public void switchTab(TabId tabId) {

        SingleSelectionModel<Tab> selectionModel = sideTabPane.getSelectionModel();

        switch (tabId) {
        case DASHBOARD:
            selectionModel.select(dashboardTab);
            break;
        case CALENDAR:
            selectionModel.select(calendarTab);
            break;
        case SCHEDULE:
            selectionModel.select(scheduleTab);
            break;
        case PATIENT:
            selectionModel.select(patientInfoTab);
            break;
        case APPOINTMENT:
            selectionModel.select(appointmentInfoTab);
            break;
        case INFO:
            selectionModel.select(infoTab);
            break;
        default:
            break;
        }
    }
}
