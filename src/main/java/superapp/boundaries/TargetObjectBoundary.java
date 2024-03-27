package superapp.boundaries;

import jakarta.validation.Valid;

public class TargetObjectBoundary {

    @Valid
    private SuperAppObjectIdBoundary objectId;

    public TargetObjectBoundary() {
    }

    public TargetObjectBoundary(SuperAppObjectIdBoundary objectId) {
        this.objectId = objectId;
    }

    public SuperAppObjectIdBoundary getObjectId() {
        return objectId;
    }

    public void setObjectId(SuperAppObjectIdBoundary objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "TargetObjectBoundary{" +
                "objectIdBoundary=" + objectId +
                '}';
    }
}
