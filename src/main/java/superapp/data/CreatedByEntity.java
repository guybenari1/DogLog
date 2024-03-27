package superapp.data;

public class CreatedByEntity {
    private UserIdEntity userId;

    public CreatedByEntity() {
    }

    public CreatedByEntity(UserIdEntity userId) {
        this.userId = userId;
    }

    public UserIdEntity getUserId() {
        return userId;
    }

    public void setUserId(UserIdEntity userId) {
        this.userId = userId;
    }
}
