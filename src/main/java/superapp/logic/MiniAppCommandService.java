package superapp.logic;

import superapp.boundaries.MiniAppCommandBoundary;

import java.util.List;

public interface MiniAppCommandService {
    @Deprecated
    Object invokeCommand(MiniAppCommandBoundary command);

    @Deprecated
    List<MiniAppCommandBoundary> getAllCommands();

    @Deprecated
    List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName);

    @Deprecated
    void deleteAllCommands();
}
