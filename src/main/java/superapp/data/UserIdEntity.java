package superapp.data;

public class UserIdEntity {
    private String superapp;
    private String email;

    public UserIdEntity() {
    }

    public UserIdEntity(String superapp, String email) {
        super();
        this.superapp = superapp;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSuperapp() {
        return superapp;
    }

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }

    @Override
    public String toString() {
        return "UserIdEntity{" +
                "superapp='" + superapp + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
