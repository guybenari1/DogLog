package superapp.boundaries;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Map;

public class SuperAppObjectBoundary {
    @NotBlank(message = "type cannot be empty or null")
    String type;
    @NotBlank(message = "alias cannot be empty or null")
    String alias;
    private SuperAppObjectIdBoundary objectId;
    private Boolean active;
    private Date creationTimestamp;
    private LocationBoundary location;
    @Valid
    private CreatedByBoundary createdBy;
    private Map<String, Object> objectDetails;

    public SuperAppObjectBoundary() {
    }

    public SuperAppObjectBoundary(String type) {
        this.type = type;
    }

    public SuperAppObjectBoundary(String type, String alias, Boolean active, LocationBoundary location, CreatedByBoundary cratedBy, Map<String, Object> objectDetails) {
        this.type = type;
        this.alias = alias;
        this.active = active;
        this.location = location;
        this.createdBy = cratedBy;
        this.objectDetails = objectDetails;
        this.creationTimestamp = new Date();
    }

    public SuperAppObjectBoundary(String type, String alias, Boolean active, LocationBoundary location, CreatedByBoundary cratedBy, Map<String, Object> objectDetails, SuperAppObjectIdBoundary objectId) {
        this.type = type;
        this.alias = alias;
        this.active = active;
        this.location = location;
        this.createdBy = cratedBy;
        this.objectDetails = objectDetails;
        this.objectId = objectId;
        this.creationTimestamp = new Date();
    }

    public SuperAppObjectIdBoundary getObjectId() {
        return objectId;
    }

    public void setObjectId(SuperAppObjectIdBoundary objectId) {
        this.objectId = objectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocationBoundary getLocation() {
        return location;
    }

    public void setLocation(LocationBoundary location) {
        this.location = location;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public CreatedByBoundary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedByBoundary createdBy) {
        this.createdBy = createdBy;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails(Map<String, Object> moreData) {
        this.objectDetails = moreData;
    }

    @Override
    public String toString() {
        return "SuperAppObjectBoundary{" +
                "objectId=" + objectId +
                ", type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                ", active=" + active +
                ", creationTimestamp=" + creationTimestamp +
                ", location=" + location +
                ", createdBy=" + createdBy +
                ", objectDetails=" + objectDetails +
                '}';
    }
}