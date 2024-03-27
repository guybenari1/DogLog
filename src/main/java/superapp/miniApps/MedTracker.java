package superapp.miniApps;

public class MedTracker {
    private Dog dog;
    private Medication medication;
    private String dosage;

    public MedTracker() {
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        return "MedTracker [dog = " + dog + ", medication=" + medication + ", dosage=" + dosage + "]";
    }
}
