package superapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.boundaries.SuperAppObjectBoundary;
import superapp.logic.ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport;

@CrossOrigin(origins = "http://localhost:5173")
// needed in case the server and client runs on the same machine, change 5173 to your client port number (5173 is React client port number)
@RestController
public class ObjectController {
    private ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport objectService;

    @Autowired
    public void setObjectService(ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport objectService) {
        this.objectService = objectService;
    }

    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary createObject(
            @Valid @RequestBody SuperAppObjectBoundary objBoundary) {
        return this.objectService.createObject(objBoundary);
    }

    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{InternalObjectId}"},
            method = {RequestMethod.PUT},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateObject(
            @RequestBody SuperAppObjectBoundary obj,
            @PathVariable("superapp") String superapp,
            @PathVariable("InternalObjectId") String internalObjectId,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail) {
        this.objectService.updateObject(superapp, internalObjectId, obj, userSuperapp, userEmail);
    }

    @RequestMapping(
            path = {"/superapp/objects/{superapp}/{InternalObjectId}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary getSpecificObject(
            @PathVariable("superapp") String superapp,
            @PathVariable("InternalObjectId") String internalObjectId,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail) {
        return this.objectService.getSpecificObject(superapp, internalObjectId, userSuperapp, userEmail);
    }

    @RequestMapping(
            path = {"/superapp/objects"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getAllObjects(
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.objectService
                .getAllObjects(userSuperapp, userEmail, page, size)
                .toArray(new SuperAppObjectBoundary[0]);
    }

    @RequestMapping(
            path = {"/superapp/objects/search/byType/{type}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getAllObjectsByType(
            @PathVariable("type") String type,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.objectService
                .getAllObjectsByType(type, userSuperapp, userEmail, page, size)
                .toArray(new SuperAppObjectBoundary[0]);
    }

    @RequestMapping(
            path = {"/superapp/objects/search/byAlias/{alias}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getAllObjectsByAlias(
            @PathVariable("alias") String alias,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.objectService
                .getAllObjectsByAlias(alias, userSuperapp, userEmail, page, size)
                .toArray(new SuperAppObjectBoundary[0]);
    }

    @RequestMapping(
            path = {"/superapp/objects/search/byLocation/{lat}/{lng}/{distance}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuperAppObjectBoundary[] getAllObjectsByLocation(
            @PathVariable("lat") float lat,
            @PathVariable("lng") float lng,
            @PathVariable("distance") float distance,
            @RequestParam(name = "units", required = false, defaultValue = "NEUTRAL") String units,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.objectService
                .getAllObjectsByLocation(lat, lng, distance, units, userSuperapp, userEmail, page, size)
                .toArray(new SuperAppObjectBoundary[0]);
    }
}
