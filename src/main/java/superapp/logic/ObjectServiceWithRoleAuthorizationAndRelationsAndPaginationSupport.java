package superapp.logic;

import superapp.boundaries.SuperAppObjectBoundary;
import superapp.boundaries.SuperAppObjectIdBoundary;

import java.util.List;

public interface ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport extends ObjectService {
    SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update, String userSuperapp, String userEmail);  // Superapp users only

    SuperAppObjectBoundary getSpecificObject(String objectSuperApp, String internalObjectId, String userSuperapp, String userEmail);    // Miniapp users see only active objects

    List<SuperAppObjectBoundary> getAllObjects(String userSuperapp, String userEmail, int page, int size);  // Miniapp users see only active objects

    void deleteAllObjects(String userSuperapp, String userEmail);   // Admins only

    void updateChildren(SuperAppObjectIdBoundary parentId, SuperAppObjectIdBoundary childId, String userSuperapp, String userEmail);    // Superapp users only

    List<SuperAppObjectBoundary> getChildren(SuperAppObjectIdBoundary parentId, String userSuperapp, String userEmail, int page, int size); // Miniapp users see only active objects

    List<SuperAppObjectBoundary> getParents(SuperAppObjectIdBoundary childId, String userSuperapp, String userEmail, int page, int size);   // Miniapp users see only active objects

    List<SuperAppObjectBoundary> getAllObjectsByType(String type, String userSuperapp, String userEmail, int page, int size);

    List<SuperAppObjectBoundary> getAllObjectsByAlias(String alias, String userSuperapp, String userEmail, int page, int size);

    List<SuperAppObjectBoundary> getAllObjectsByLocation(float lat, float lng, float distance, String units, String userSuperapp, String userEmail, int page, int size);
}
