package Model;

public class Medication {
    private int id_medication;
    private String name;
    private String category;
    private String dosage;
    private String time_of_usage;
    private String medication_pharmaceutical_form;

    public Medication(int id_medication, String name, String category, String dosage, String time_of_usage, String medication_pharmaceutical_form) {
        this.id_medication = id_medication;
        this.name = name;
        this.category = category;
        this.dosage = dosage;
        this.time_of_usage = time_of_usage;
        this.medication_pharmaceutical_form = medication_pharmaceutical_form;
    }

    public Medication(String name, String category, String dosage, String time_of_usage, String medication_pharmaceutical_form) {
        this.name = name;
        this.category = category;
        this.dosage = dosage;
        this.time_of_usage = time_of_usage;
        this.medication_pharmaceutical_form = medication_pharmaceutical_form;
    }

    public int getId_medication() {
        return id_medication;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDosage() {
        return dosage;
    }

    public String getTime_of_usage() {
        return time_of_usage;
    }

    public String getMedication_pharmaceutical_form() {
        return medication_pharmaceutical_form;
    }

    public void setId_medication(int id_medication) {
        this.id_medication = id_medication;
    }
}
