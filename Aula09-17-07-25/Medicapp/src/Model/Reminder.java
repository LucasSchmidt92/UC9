package Model;

public class Reminder {
//    Reminder_ID BIGSERIAL PRIMARY KEY NOT NULL,
//    Reminder_Time DATE NOT NULL,
//    Reminder_Observations VARCHAR(255) NOT NULL,
//    Reminder_Status BOOLEAN NOT NULL,
    private int id_reminder;
    private String time;
    private String observations;
    private Boolean status;

    public Reminder(int id_reminder, String time, String observations, Boolean status) {
        this.id_reminder = id_reminder;
        this.time = time;
        this.observations = observations;
        this.status = status;
    }

    public Reminder(String time, String observations, Boolean status) {
        this.time = time;
        this.observations = observations;
        this.status = status;
    }

    public int getId_reminder() {
        return id_reminder;
    }

    public String getTime() {
        return time;
    }

    public String getObservations() {
        return observations;
    }

    public String getStatus() {
        if(this.status){
            return "Realizado";
        } else {
            return "Pendente";
        }
    }

    public void setId_reminder(int id_reminder) {
        this.id_reminder = id_reminder;
    }

    public static class ReminderInstitute extends Reminder{
        int fk_appointmentInstitute;
        int fk_institute;

        public ReminderInstitute(String time, String observations, Boolean status, int fk_appointmentInstitute, int fk_institute) {
            super(time, observations, status);
            this.fk_appointmentInstitute = fk_appointmentInstitute;
            this.fk_institute = fk_institute;
        }

        public int getFk_appointmentInstitute() {
            return fk_appointmentInstitute;
        }

        public int getFk_institute() {
            return fk_institute;
        }
    }

    public static class ReminderNaturalPerson extends Reminder{
        int fk_appointmentNaturalPerson;
        int fk_naturalPerson;

        public ReminderNaturalPerson(String time, String observations, Boolean status, int fk_appointmentNaturalPerson, int fk_naturalPerson) {
            super(time, observations, status);
            this.fk_appointmentNaturalPerson = fk_appointmentNaturalPerson;
            this.fk_naturalPerson = fk_naturalPerson;
        }

        public int getFk_appointmentNaturalPerson() {
            return fk_appointmentNaturalPerson;
        }

        public int getFk_naturalPerson() {
            return fk_naturalPerson;
        }
    }
}
