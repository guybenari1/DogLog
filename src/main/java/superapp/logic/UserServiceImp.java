package superapp.logic;

import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import superapp.boundaries.UserBoundary;
import superapp.boundaries.UserBoundaryRole;
import superapp.boundaries.UserIdBoundary;
import superapp.data.UserCrud;
import superapp.data.UserEntity;
import superapp.data.UserIdEntity;
import superapp.data.UserRole;

import java.util.List;

@Service
public class UserServiceImp implements UserServiceWithRoleAuthorizationAndPaginationSupport {
    private final UserCrud userCrud;
    private final Log logger = LogFactory.getLog(UserServiceImp.class);
    private String springApplicationName;

    @Autowired
    public UserServiceImp(UserCrud userCrud) {
        this.userCrud = userCrud;
    }

    // this method injects a configuration value of spring
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springApplicationName = springApplicationName;
    }

    @PostConstruct
    public void init() {
        this.logger.info("***** UserService is running, " + this.springApplicationName);
    }

    public UserEntity boundaryToEntity(UserBoundary boundary) {
        UserEntity entity = new UserEntity();
        entity.setUserId(this.boundaryToEntity(boundary.getUserId()));
        entity.setRole(UserRole.valueOf(boundary.getRole().name()));
        entity.setUsername(boundary.getUsername());
        entity.setAvatar(boundary.getAvatar());
        return entity;
    }

    public UserBoundary entityToBoundary(UserEntity entity) {
        UserBoundary boundary = new UserBoundary();
        boundary.setUserId(this.entityToBoundary(entity.getUserId()));
        boundary.setRole(UserBoundaryRole.valueOf(entity.getRole().name()));
        boundary.setUsername(entity.getUsername());
        boundary.setAvatar(entity.getAvatar());
        return boundary;
    }

    public UserIdEntity boundaryToEntity(UserIdBoundary boundary) {
        return new UserIdEntity(boundary.getSuperapp(), boundary.getEmail());
    }

    public UserIdBoundary entityToBoundary(UserIdEntity entity) {
        return new UserIdBoundary(entity.getSuperapp(), entity.getEmail());
    }

    // TODO - move duplicated code to a shared class
    void checkUserAuthorization(String userSuperapp, String userEmail, UserRole role) {
        UserEntity user = this.userCrud.findByUserId_SuperappAndUserId_Email(userSuperapp, userEmail)
                .orElseThrow(() -> new UserNotFoundException("Could not find user by email: " + userEmail));
        if (!user.getRole().equals(role))
            throw new UserNotAuthorizedException("User is not authorized to perform this action");
    }

    @Override
    public UserBoundary createUser(UserBoundary user) {
        UserEntity newUser = this.boundaryToEntity(user);
        UserIdEntity userId = newUser.getUserId();
        userId.setSuperapp(springApplicationName);
        if (this.userCrud.existsByUserId_SuperappAndUserId_Email(userId.getSuperapp(), userId.getEmail())) // check if user already exist
            throw new UserAlreadyExistsException("User already exist with email: " + userId.getEmail());
        newUser = this.userCrud.save(newUser);
        this.logger.info("*** User created!\n" + newUser);
        return this.entityToBoundary(newUser);
    }

    @Override
    public UserBoundary login(String userSuperapp, String userEmail) {
        if (!userSuperapp.equals(this.springApplicationName))
            throw new BadRequestException("Wrong superapp name");
        UserEntity user = this.userCrud.findByUserId_SuperappAndUserId_Email(userSuperapp, userEmail)
                .orElseThrow(() -> new UserNotFoundException("Could not find user by email: " + userEmail));
        this.logger.info("*** User login!\n" + user);
        return entityToBoundary(user);
    }

    @Override
    public UserBoundary updateUser(String userSuperapp, String userEmail, UserBoundary update) {
        if (!userSuperapp.equals(this.springApplicationName)) {
            throw new BadRequestException("Wrong superapp name");
        }
        UserEntity existing = this.userCrud.findByUserId_SuperappAndUserId_Email(userSuperapp, userEmail)
                .orElseThrow(() -> new UserNotFoundException("Could not find user by email: " + userEmail));
        boolean dirtyFlag = false;
        if (update.getRole() != null) {
            existing.setRole(UserRole.valueOf(update.getRole().name()));
            dirtyFlag = true;
        }
        if (update.getUsername() != null) {
            existing.setUsername(update.getUsername());
            dirtyFlag = true;
        }
        if (update.getAvatar() != null) {
            existing.setAvatar(update.getAvatar());
            dirtyFlag = true;
        }
        if (dirtyFlag) {
            existing = this.userCrud.save(existing);
            this.logger.info("*** User updated!\n" + existing);
        }
        return this.entityToBoundary(existing);
    }

    @Override
    @Deprecated
    public List<UserBoundary> getAllUsers() {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public List<UserBoundary> getAllUsers(String userSuperapp, String userEmail, int page, int size) {
        checkUserAuthorization(userSuperapp, userEmail, UserRole.ADMIN);
        return this.userCrud
                .findAll(PageRequest.of(page, size, Sort.Direction.DESC, "role"))   // Page<MiniAppCommandEntity> (sorted by role)
                .stream()   // Stream<UserEntity>
                .map(this::entityToBoundary)    // Stream<UserBoundary>
                .toList();  // List<UserBoundary>;
    }

    @Override
    @Deprecated
    public void deleteAllUsers() {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public void deleteAllUsers(String userSuperapp, String userEmail) {
        checkUserAuthorization(userSuperapp, userEmail, UserRole.ADMIN);
        this.userCrud.deleteAll();
        this.logger.info("*** All the users has been deleted");
    }
}