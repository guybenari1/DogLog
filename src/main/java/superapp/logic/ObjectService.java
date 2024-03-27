package superapp.logic;

import superapp.boundaries.SuperAppObjectBoundary;

import java.util.List;

public interface ObjectService {
    SuperAppObjectBoundary createObject(SuperAppObjectBoundary object); // Superapp users only

    @Deprecated
    SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update);

    @Deprecated
    SuperAppObjectBoundary getSpecificObject(String objectSuperApp, String internalObjectId);

    @Deprecated
    List<SuperAppObjectBoundary> getAllObjects();

    @Deprecated
    void deleteAllObjects();
}
