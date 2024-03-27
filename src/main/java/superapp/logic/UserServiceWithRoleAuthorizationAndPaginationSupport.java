package superapp.logic;

import superapp.boundaries.UserBoundary;

import java.util.List;

public interface UserServiceWithRoleAuthorizationAndPaginationSupport extends UserService {
    List<UserBoundary> getAllUsers(String userSuperapp, String userEmail, int page, int size);   // Admins only

    void deleteAllUsers(String userSuperapp, String userEmail);  // Admins only
}
