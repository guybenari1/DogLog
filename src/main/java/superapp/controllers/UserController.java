package superapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import superapp.boundaries.NewUserBoundary;
import superapp.boundaries.UserBoundary;
import superapp.boundaries.UserIdBoundary;
import superapp.logic.UserService;

@CrossOrigin(origins = "http://localhost:5173")
// needed in case the server and client runs on the same machine, change 5173 to your client port number (5173 is React client port number)
@RestController
@Validated
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            path = {"/superapp/users"},
            method = {RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}) // takes a JSON as argument)
    public UserBoundary createUser(
            @Valid @RequestBody NewUserBoundary newUser) {
        return this.userService.createUser(new UserBoundary(new UserIdBoundary(newUser.getEmail()), newUser.getRole(), newUser.getUsername(), newUser.getAvatar()));
    }

    @RequestMapping(
            path = {"/superapp/users/login/{superapp}/{email}"},
            method = {RequestMethod.GET},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserBoundary login(
            @PathVariable("superapp") String superapp,
            @PathVariable("email") String email) {
        return this.userService.login(superapp, email);
    }

    @RequestMapping(
            path = {"/superapp/users/{superapp}/{userEmail}"},
            method = {RequestMethod.PUT},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser(
            @RequestBody UserBoundary updatedUser,
            @PathVariable("superapp") String superapp,
            @PathVariable("userEmail") String userEmail) {
        this.userService.updateUser(superapp, userEmail, updatedUser);
    }
}




