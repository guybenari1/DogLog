package superapp.logic;

import superapp.boundaries.UserBoundary;

import java.util.List;

public interface UserService {
    UserBoundary createUser(UserBoundary user);

    UserBoundary login(String userSuperApp, String userEmail);

    UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update);

    @Deprecated
    List<UserBoundary> getAllUsers();

    @Deprecated
    void deleteAllUsers();
}
