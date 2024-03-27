package superapp.boundaries;

public class UserBoundary {
    private UserIdBoundary userId;
    private UserBoundaryRole role;
    private String username;
    private String avatar;

    public UserBoundary() {
    }

    public UserBoundary(UserIdBoundary userId, UserBoundaryRole role, String username, String avatar) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
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

    public UserIdBoundary getUserId() {
        return userId;
    }

    public void setUserId(UserIdBoundary userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserBoundary{" +
                "userId=" + userId +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}



