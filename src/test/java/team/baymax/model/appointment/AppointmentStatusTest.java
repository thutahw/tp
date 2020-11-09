package team.baymax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppointmentStatusTest {
    @Test
    void getColorCode() {
        assertEquals("#3e9149", AppointmentStatus.DONE.getColorCode());
        assertEquals("#4d66bc", AppointmentStatus.UPCOMING.getColorCode());
        assertEquals("#ac4848", AppointmentStatus.MISSED.getColorCode());
    }

    @Test
    void text() {
        assertEquals("done", AppointmentStatus.DONE.text());
        assertEquals("upcoming", AppointmentStatus.UPCOMING.text());
        assertEquals("missed", AppointmentStatus.MISSED.text());
    }
}
