package superapp.boundaries;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NewUserBoundary {
    @NotBlank(message = "email cannot be empty or null")
    @Email(message = "email must be a valid email address")
    private String email;
    private UserBoundaryRole role;
    @NotBlank(message = "username cannot be empty or null")
    private String username;
    @NotBlank(message = "avatar cannot be empty or null")
    private String avatar;

    public NewUserBoundary() {
    }

    public NewUserBoundary(String email, UserBoundaryRole role, String username, String avatar) {
        super();
        this.email = email;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserBoundaryRole getRole() {
        return role;
    }

    public void setRole(UserBoundaryRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "NewUserBoundary{" +
                "email='" + email + '\'' +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
