package superapp.miniApps;

import java.util.Date;

//to do
public class Appointment { //need to add attributes: dog, business.
    private Date date;

    public Appointment() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment [date=" + date + "]";
    }
}
