package superapp.data;

import java.util.Objects;

public class SuperAppObjectIdEntity {
    private String superapp;
    private String internalObjectId;

    public SuperAppObjectIdEntity() {
    }

    public SuperAppObjectIdEntity(String superapp, String internalObjectId) {
        super();
        this.superapp = superapp;
        this.internalObjectId = internalObjectId;
    }

    public String getSuperapp() {
        return superapp;
    }

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }

    public String getInternalObjectId() {
        return internalObjectId;
    }

    public void setInternalObjectId(String internalObjectId) {
        this.internalObjectId = internalObjectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(superapp, internalObjectId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SuperAppObjectIdEntity other = (SuperAppObjectIdEntity) obj;
        return Objects.equals(superapp, other.superapp) && Objects.equals(internalObjectId, other.internalObjectId);
    }

    @Override
    public String toString() {
        return "SuperAppObjectIdEntity{" +
                "superapp='" + superapp + '\'' +
                ", internalObjectId='" + internalObjectId + '\'' +
                '}';
    }
}
