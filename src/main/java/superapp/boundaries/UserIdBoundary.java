package superapp.boundaries;

import jakarta.validation.constraints.NotBlank;

public class UserIdBoundary {
    @NotBlank(message = "superapp cannot be empty or null")
    private String superapp;
    @NotBlank(message = "email cannot be empty or null")
    private String email;

    public UserIdBoundary() {
    }

    public UserIdBoundary(String email) {
        super();
        this.email = email;
    }

    public UserIdBoundary(String superapp, String email) {
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
        return "UserIdBoundary{" +
                "superapp='" + superapp + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
