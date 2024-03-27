package superapp.miniApps;

import java.util.Date;

public class VaccinationCard {
    private Dog dog;
    private Vaccine vaccine;
    private Date date;

    public VaccinationCard() {
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "VaccinationCard [dog = " + dog + ", vaccine=" + vaccine + ", date=" + date + "]";
    }
}
