package team.baymax.model.appointment;

public enum AppointmentStatus {
    DONE("done", "#3e9149"),
    MISSED("missed", "#ac4848"),
    UPCOMING("upcoming", "#4d66bc");

    private String status;
    private String colorCode;

    AppointmentStatus(String status, String color) {
        this.status = status;
        this.colorCode = color;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String text() {
        return status;
    }
}
