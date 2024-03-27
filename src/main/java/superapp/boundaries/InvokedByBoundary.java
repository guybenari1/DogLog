package superapp.boundaries;

import jakarta.validation.Valid;

public class InvokedByBoundary {
    @Valid
    private UserIdBoundary userId;

    public InvokedByBoundary() {
    }

    public InvokedByBoundary(UserIdBoundary userId) {
        this.userId = userId;
    }

    public UserIdBoundary getUserId() {
        return userId;
    }

    public void setUserId(UserIdBoundary userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "InvokedByBoundary{" +
                "userId=" + userId +
                '}';
    }
}
