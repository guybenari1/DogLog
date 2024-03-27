package superapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.boundaries.SuperAppObjectBoundary;
import superapp.boundaries.SuperAppObjectIdBoundary;
import superapp.logic.ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport;

@CrossOrigin(origins = "http://localhost:5173")
// needed in case the server and client runs on the same machine, change 5173 to your client port number (5173 is React client port number)
@RestController
public class RelationshipController {
    private ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport objectService;

    @Autowired
    public void setObjectService(ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport objectService) {
        this.objectService = objectService;
    }

    @RequestMapping(method = {RequestMethod.PUT},
            path = {"/superapp/objects/{superapp}/{InternalObjectId}/children"},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateChildren(
            @RequestBody SuperAppObjectIdBoundary childId,
            @PathVariable("superapp") String superapp,
            @PathVariable("InternalObjectId") String internalObjectId,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail) {
        this.objectService.updateChildren(new SuperAppObjectIdBoundary(superapp, internalObjectId), childId, userSuperapp, userEmail);
    }

    @RequestMapping(method = {RequestMethod.GET},
            path = {"/superapp/objects/{superapp}/{InternalObjectId}/children"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getChildren(
            @PathVariable("superapp") String superapp,
            @PathVariable("InternalObjectId") String internalObjectId,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.objectService
                .getChildren(new SuperAppObjectIdBoundary(superapp, internalObjectId), userSuperapp, userEmail, page, size)
                .toArray(new SuperAppObjectBoundary[0]);
    }

    @RequestMapping(method = {RequestMethod.GET},
            path = {"/superapp/objects/{superapp}/{InternalObjectId}/parents"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getParents(
            @PathVariable("superapp") String superapp,
            @PathVariable("InternalObjectId") String internalObjectId,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.objectService
                .getParents(new SuperAppObjectIdBoundary(superapp, internalObjectId), userSuperapp, userEmail, page, size)
                .toArray(new SuperAppObjectBoundary[0]);
    }
}
