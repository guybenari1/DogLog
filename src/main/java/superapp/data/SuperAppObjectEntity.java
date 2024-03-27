package superapp.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "OBJECTS")
public class SuperAppObjectEntity {
    @Id
    private SuperAppObjectIdEntity objectId;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimestamp;
    @GeoSpatialIndexed
    private Point location;
    private CreatedByEntity createdBy;
    private Map<String, Object> objectDetails;
    @DBRef(lazy = true)
    private Set<SuperAppObjectEntity> children;
    @DBRef
    private SuperAppObjectEntity parents;

    public SuperAppObjectEntity() {
        this.children = new HashSet<>();
    }

    public SuperAppObjectIdEntity getObjectId() {
        return objectId;
    }

    public void setObjectId(SuperAppObjectIdEntity objectId) {
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

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public CreatedByEntity getCreationBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedByEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails(Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
    }

    public Set<SuperAppObjectEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<SuperAppObjectEntity> children) {
        this.children = children;
    }

    public SuperAppObjectEntity getParents() {
        return parents;
    }

    public void setParents(SuperAppObjectEntity parents) {
        this.parents = parents;
    }

    public void addChild(SuperAppObjectEntity child) {
        this.children.add(child);
    }

    public void removeChild(SuperAppObjectEntity child) {
        this.children.remove(child);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SuperAppObjectEntity other = (SuperAppObjectEntity) obj;
        return Objects.equals(objectId, other.objectId);
    }

    @Override
    public String toString() {
        return "SuperAppObjectEntity{" +
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
