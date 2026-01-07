package healthcare.model;

public class Appointment {
    private String appointmentId;
    private String date;
    private String time;
    private String status;

    public Appointment() {}
    public Appointment(String appointmentId, String date, String time, String status) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public void createAppointment() {}
    public void cancelAppointment() {}
    public void modifyAppointment() {}
}
