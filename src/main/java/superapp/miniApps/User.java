package superapp.miniApps;

import java.util.Map;

public class User { //need to add attributes: picture.
    private String name;
    private String password;
    private String phoneNumber;
    private Map<String, Dog> dogs;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Dog> getDogs() {
        return dogs;
    }

    public void setDogs(Map<String, Dog> dogs) {
        this.dogs = dogs;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", password=" + password + ", phoneNumber=" + phoneNumber + ", dogs=" + dogs
                + "]";
    }
}
