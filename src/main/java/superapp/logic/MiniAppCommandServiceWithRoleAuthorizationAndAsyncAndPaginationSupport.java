package superapp.logic;

import superapp.boundaries.MiniAppCommandBoundary;

import java.util.List;

public interface MiniAppCommandServiceWithRoleAuthorizationAndAsyncAndPaginationSupport extends MiniAppCommandService {
    Object invokeCommand(MiniAppCommandBoundary command, boolean async);    // Miniapp users only

    List<MiniAppCommandBoundary> getAllCommands(String userSuperapp, String userEmail, int page, int size); // Admins only

    List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName, String userSuperapp, String userEmail, int page, int size);  // Admins only

    void deleteAllCommands(String userSuperapp, String userEmail);  // Admins only

}
