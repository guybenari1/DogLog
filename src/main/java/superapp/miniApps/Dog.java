package superapp.miniApps;

import java.util.Date;

public class Dog { //need to add attributes: picture, certificates.
    private String name;
    private String race;
    private Date birthDate;

    public Dog() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Dog [name=" + name + ", race=" + race + ", birthDate=" + birthDate + "]";
    }
}
