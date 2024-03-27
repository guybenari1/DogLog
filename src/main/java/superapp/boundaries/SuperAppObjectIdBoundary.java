package superapp.boundaries;

import jakarta.validation.constraints.NotBlank;

public class SuperAppObjectIdBoundary {
    @NotBlank(message = "superapp cannot be empty or null")
    private String superapp;
    @NotBlank(message = "internalObjectId cannot be empty or null")
    private String internalObjectId;

    public SuperAppObjectIdBoundary() {
    }

    public SuperAppObjectIdBoundary(String superapp, String internalObjectId) {
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
    public String toString() {
        return "SuperAppObjectIdBoundary{" +
                "superapp='" + superapp + '\'' +
                ", internalObjectId='" + internalObjectId + '\'' +
                '}';
    }
}
