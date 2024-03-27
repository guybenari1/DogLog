package superapp.data;

public class TargetObjectEntity {
    private SuperAppObjectIdEntity objectId;

    public TargetObjectEntity() {
    }

    public TargetObjectEntity(SuperAppObjectIdEntity objectId) {
        this.objectId = objectId;
    }

    public SuperAppObjectIdEntity getObjectId() {
        return objectId;
    }

    public void setObjectId(SuperAppObjectIdEntity objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "TargetObjectEntity{" +
                "objectId=" + objectId +
                '}';
    }
}
