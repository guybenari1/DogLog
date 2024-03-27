package superapp.miniApps;

import java.util.Date;

public class Medication {
    private Dog dog;
    private String name;
    private Boolean repeatedly;
    private Boolean prescription;
    private Date validity;

    public Medication() {
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRepeatedly() {
        return repeatedly;
    }

    public void setRepeatedly(Boolean repeatedly) {
        this.repeatedly = repeatedly;
    }

    public Boolean getPrescription() {
        return prescription;
    }

    public void setPrescription(Boolean prescription) {
        this.prescription = prescription;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "Medication [dog = " + dog + ", name=" + name + ", repeatedly=" + repeatedly + ", prescription=" + prescription + "]";
    }
}
