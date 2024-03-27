package superapp.boundaries;

import jakarta.validation.Valid;

public class CreatedByBoundary {
    @Valid
    private UserIdBoundary userId;

    public CreatedByBoundary() {
    }

    public CreatedByBoundary(UserIdBoundary userId) {
        this.userId = userId;
    }

    public UserIdBoundary getUserId() {
        return userId;
    }

    public void setUserId(UserIdBoundary userId) {
        this.userId = userId;
    }
}
