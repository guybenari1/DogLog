package superapp.miniApps;

import java.util.Date;

public class Vaccine {
    private String name;
    private Date validity;

    public Vaccine() {
        // TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "Vaccine [name=" + name + ", validity=" + validity + "]";
    }
}
