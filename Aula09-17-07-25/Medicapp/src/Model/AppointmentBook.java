package Model;

public class AppointmentBook {
    private int id_appointment;
    private String start_date;
    private String end_date;
    private int fk_medication;

    public AppointmentBook(int id_appointment, String start_date, String end_date, int id_medication) {
        this.id_appointment = id_appointment;
        this.start_date = start_date;
        this.end_date = end_date;
        this.fk_medication = id_medication;
    }

    public AppointmentBook(String start_date, String end_date, int id_medication) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.fk_medication = id_medication;
    }

    public int getId_appointment() {
        return id_appointment;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public int getFk_medication() {
        return fk_medication;
    }

    public void setId_appointment(int id_appointment) {
        this.id_appointment = id_appointment;
    }

    public static class AppointmentNaturalPerson extends AppointmentBook{
        int fk_naturalPerson;

        public AppointmentNaturalPerson(String start_date, String end_date, int id_medication, int id_naturalPerson) {
            super(start_date, end_date, id_medication);
            this.fk_naturalPerson = id_naturalPerson;
        }

        public int getFk_naturalPerson() {
            return fk_naturalPerson;
        }
    }

    public static class AppointmentInstitute extends AppointmentBook{
        int fk_institute;

        public AppointmentInstitute(String start_date, String end_date, int id_medication, int id_institute) {
            super(start_date, end_date, id_medication);
            this.fk_institute = id_institute;
        }

        public int getFk_institute() {
            return fk_institute;
        }
    }
}
