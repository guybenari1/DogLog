package superapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import superapp.boundaries.CommandIdBoundary;
import superapp.boundaries.MiniAppCommandBoundary;
import superapp.logic.MiniAppCommandServiceWithRoleAuthorizationAndAsyncAndPaginationSupport;

@CrossOrigin(origins = "http://localhost:5173")
// needed in case the server and client runs on the same machine, change 5173 to your client port number (5173 is React client port number)
@RestController
public class MiniAppCommandController {
    private MiniAppCommandServiceWithRoleAuthorizationAndAsyncAndPaginationSupport commandService;

    @Autowired
    public void setCommandService(MiniAppCommandServiceWithRoleAuthorizationAndAsyncAndPaginationSupport commandService) {
        this.commandService = commandService;
    }

    @RequestMapping(
            path = {"/superapp/miniapp/{miniAppName}"},
            method = {RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object InvokeCommand(
            @Valid @RequestBody MiniAppCommandBoundary commandBoundary,
            @PathVariable("miniAppName") String miniAppName,
            @RequestParam(name = "async", required = false, defaultValue = "false") boolean async) {
        commandBoundary.setCommandId(new CommandIdBoundary(miniAppName));
        return this.commandService.invokeCommand(commandBoundary, async);
    }
}
