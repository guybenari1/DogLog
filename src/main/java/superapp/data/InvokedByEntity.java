package superapp.data;

public class InvokedByEntity {
    private UserIdEntity userId;

    public InvokedByEntity() {
    }

    public InvokedByEntity(UserIdEntity userId) {
        this.userId = userId;
    }

    public UserIdEntity getUserId() {
        return userId;
    }

    public void setUserId(UserIdEntity userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "InvokedByEntity{" +
                "userId=" + userId +
                '}';
    }
}
