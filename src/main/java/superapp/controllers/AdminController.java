package superapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.boundaries.MiniAppCommandBoundary;
import superapp.boundaries.UserBoundary;
import superapp.logic.MiniAppCommandServiceWithRoleAuthorizationAndAsyncAndPaginationSupport;
import superapp.logic.ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport;
import superapp.logic.UserServiceWithRoleAuthorizationAndPaginationSupport;

@CrossOrigin(origins = "http://localhost:5173")
// needed in case the server and client runs on the same machine, change 5173 to your client port number (5173 is React client port number)
@RestController
public class AdminController {
    private ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport objectService;
    private UserServiceWithRoleAuthorizationAndPaginationSupport userService;
    private MiniAppCommandServiceWithRoleAuthorizationAndAsyncAndPaginationSupport commandService;

    @Autowired
    public void setObjectService(ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport objectService) {
        this.objectService = objectService;
    }

    @Autowired
    public void setUserService(UserServiceWithRoleAuthorizationAndPaginationSupport userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCommandService(MiniAppCommandServiceWithRoleAuthorizationAndAsyncAndPaginationSupport commandService) {
        this.commandService = commandService;
    }

    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.DELETE})
    public void deleteAllUsers(
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail) {
        this.userService.deleteAllUsers(userSuperapp, userEmail);
    }

    @RequestMapping(
            path = {"/superapp/admin/objects"},
            method = {RequestMethod.DELETE})
    public void deleteAllObjects(
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail) {
        this.objectService.deleteAllObjects(userSuperapp, userEmail);
    }

    @RequestMapping(
            path = {"/superapp/admin/miniapp"},
            method = {RequestMethod.DELETE})
    public void deleteAllCommands(
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail) {
        this.commandService.deleteAllCommands(userSuperapp, userEmail);
    }

    @RequestMapping(
            path = {"/superapp/admin/users"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserBoundary[] exportAllUsers(
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.userService
                .getAllUsers(userSuperapp, userEmail, page, size)
                .toArray(new UserBoundary[0]);
    }

    @RequestMapping(
            path = {"/superapp/admin/miniapp"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MiniAppCommandBoundary[] getAllCommands(
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.commandService
                .getAllCommands(userSuperapp, userEmail, page, size)
                .toArray(new MiniAppCommandBoundary[0]);
    }

    @RequestMapping(
            path = {"/superapp/admin/miniapp/{miniAppName}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MiniAppCommandBoundary[] getAllMiniAppCommands(
            @PathVariable("miniAppName") String miniAppName,
            @RequestParam(name = "userSuperapp") String userSuperapp,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.commandService
                .getAllMiniAppCommands(miniAppName, userSuperapp, userEmail, page, size)
                .toArray(new MiniAppCommandBoundary[0]);
    }
}
