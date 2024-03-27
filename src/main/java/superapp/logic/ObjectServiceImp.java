package superapp.logic;

import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import superapp.boundaries.*;
import superapp.data.*;

import java.util.*;
import java.util.stream.Stream;

@Service
public class ObjectServiceImp implements ObjectServiceWithRoleAuthorizationAndRelationsAndPaginationSupport {
    private final SuperAppObjectCrud objectCrud;
    private final UserCrud userCrud;
    private final Log logger = LogFactory.getLog(ObjectServiceImp.class);
    private String springApplicationName;


    @Autowired
    public ObjectServiceImp(SuperAppObjectCrud objectCrud, UserCrud userCrud) {
        this.objectCrud = objectCrud;
        this.userCrud = userCrud;
    }

    // this method injects a configuration value of spring
    @Value("${spring.application.name:defaultSuperAppName}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springApplicationName = springApplicationName;
    }

    @PostConstruct
    public void init() {
        this.logger.info("***** ObjectService is running, " + this.springApplicationName);
    }

    public SuperAppObjectEntity boundaryToEntity(SuperAppObjectBoundary boundary) {
        SuperAppObjectEntity entity = new SuperAppObjectEntity();
        entity.setType(boundary.getType());
        entity.setAlias(boundary.getAlias());
        entity.setActive(boundary.getActive());
        entity.setCreationTimestamp(boundary.getCreationTimestamp());
        entity.setLocation(this.boundaryToEntity(boundary.getLocation()));
        entity.setCreatedBy(this.boundaryToEntity(boundary.getCreatedBy()));
        entity.setObjectDetails(boundary.getObjectDetails());
        return entity;
    }

    public SuperAppObjectBoundary entityToBoundary(SuperAppObjectEntity entity) {
        SuperAppObjectBoundary boundary = new SuperAppObjectBoundary();
        boundary.setObjectId(this.entityToBoundary(entity.getObjectId()));
        boundary.setType(entity.getType());
        boundary.setAlias(entity.getAlias());
        boundary.setActive(entity.getActive());
        boundary.setCreationTimestamp(entity.getCreationTimestamp());
        boundary.setLocation(this.entityToBoundary(entity.getLocation()));
        boundary.setCreatedBy(this.entityToBoundary(entity.getCreationBy()));
        boundary.setObjectDetails(entity.getObjectDetails());
        return boundary;
    }

    public Point boundaryToEntity(LocationBoundary locationBoundary) {
        float lat = 0, lng = 0;
        if (locationBoundary != null) {
            lat = locationBoundary.getLat() != null ? locationBoundary.getLat() : lat;
            lng = locationBoundary.getLng() != null ? locationBoundary.getLng() : lng;
        }
        return new Point(lat, lng);
    }

    public LocationBoundary entityToBoundary(Point locationEntity) {
        return new LocationBoundary((float) locationEntity.getX(), (float) locationEntity.getY());
    }

    public CreatedByEntity boundaryToEntity(CreatedByBoundary createdByBoundary) {
        return new CreatedByEntity(new UserIdEntity(createdByBoundary.getUserId().getSuperapp(), createdByBoundary.getUserId().getEmail()));
    }

    public CreatedByBoundary entityToBoundary(CreatedByEntity createdByEntity) {
        return new CreatedByBoundary(new UserIdBoundary(createdByEntity.getUserId().getSuperapp(), createdByEntity.getUserId().getEmail()));
    }

    // TODO - move duplicated code to a shared class
    public SuperAppObjectIdEntity boundaryToEntity(SuperAppObjectIdBoundary boundary) {
        return new SuperAppObjectIdEntity(boundary.getSuperapp(), boundary.getInternalObjectId());
    }

    // TODO - move duplicated code to a shared class
    public SuperAppObjectIdBoundary entityToBoundary(SuperAppObjectIdEntity entity) {
        return new SuperAppObjectIdBoundary(entity.getSuperapp(), entity.getInternalObjectId());
    }

    boolean isSuperAppUser(String userSuperapp, String userEmail) {
        UserEntity user = this.userCrud.findByUserId_SuperappAndUserId_Email(userSuperapp, userEmail)
                .orElseThrow(() -> new UserNotFoundException("Could not find user by email: " + userEmail));
        if (user.getRole().equals(UserRole.ADMIN))
            throw new UserNotAuthorizedException("User is not authorized to perform this action");
        return user.getRole().equals(UserRole.SUPERAPP_USER);
    }

    // TODO - move duplicated code to a shared class
    void checkUserAuthorization(String userSuperapp, String userEmail, UserRole role) {
        UserEntity user = this.userCrud.findByUserId_SuperappAndUserId_Email(userSuperapp, userEmail)
                .orElseThrow(() -> new UserNotFoundException("Could not find user by email: " + userEmail));
        if (!user.getRole().equals(role))
            throw new UserNotAuthorizedException("User is not authorized to perform this action");
    }

    @Override
    public SuperAppObjectBoundary createObject(SuperAppObjectBoundary boundary) {
        UserIdBoundary userId = boundary.getCreatedBy().getUserId();
        if (!userId.getSuperapp().equals(springApplicationName))
            throw new BadRequestException("Wrong superapp name in createdBy.userId.superapp: " + boundary.getCreatedBy().getUserId().getSuperapp());
        checkUserAuthorization(userId.getSuperapp(), userId.getEmail(), UserRole.SUPERAPP_USER);
        if (boundary.getActive() == null) {
            boundary.setActive(true);
        }
        SuperAppObjectEntity entity = this.boundaryToEntity(boundary);
        entity.setObjectId(new SuperAppObjectIdEntity(this.springApplicationName, UUID.randomUUID().toString()));
        entity.setCreationTimestamp(new Date());
        entity = this.objectCrud.save(entity);
        this.logger.info("*** New Object was created: " + entity);
        return this.entityToBoundary(entity);
    }

    @Override
    @Deprecated
    public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update) {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId, SuperAppObjectBoundary update, String userSuperapp, String userEmail) {
        checkUserAuthorization(userSuperapp, userEmail, UserRole.SUPERAPP_USER);
        SuperAppObjectIdEntity ObjectId = new SuperAppObjectIdEntity(objectSuperApp, internalObjectId);
        SuperAppObjectEntity existing = this.objectCrud.findById(ObjectId)
                .orElseThrow(() -> new SuperAppObjectNotFoundException("Could not find Object by id: " + ObjectId));
        boolean dirtyFlag = false;
        if (update.getType() != null) {
            existing.setType(update.getType());
            dirtyFlag = true;
        }
        if (update.getAlias() != null) {
            existing.setAlias(update.getAlias());
            dirtyFlag = true;
        }
        if (update.getActive() != null) {
            existing.setActive(update.getActive());
            dirtyFlag = true;
        }
        if (update.getLocation() != null) {
            existing.setLocation(this.boundaryToEntity(update.getLocation()));
            dirtyFlag = true;
        }
        if (update.getObjectDetails() != null) {
            existing.setObjectDetails(update.getObjectDetails());
            dirtyFlag = true;
        }
        if (dirtyFlag) {
            existing = this.objectCrud.save(existing);
            this.logger.info("*** Object Updated!\n" + existing);
        }
        return this.entityToBoundary(existing);
    }

    @Override
    @Deprecated
    public SuperAppObjectBoundary getSpecificObject(String objectSuperapp, String internalObjectId) {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public SuperAppObjectBoundary getSpecificObject(String objectSuperapp, String internalObjectId, String userSuperapp, String userEmail) {
        boolean seeActive = isSuperAppUser(userSuperapp, userEmail);
        SuperAppObjectIdEntity ObjectId = new SuperAppObjectIdEntity(objectSuperapp, internalObjectId);
        Optional<SuperAppObjectEntity> result = seeActive ? this.objectCrud.findById(ObjectId) : this.objectCrud.findByObjectIdAndActiveIsTrue(ObjectId);
        return result
                .map(this::entityToBoundary)
                .orElseThrow(() -> new SuperAppObjectNotFoundException("Could not find Object by id: " + ObjectId));
    }

    @Override
    @Deprecated
    public List<SuperAppObjectBoundary> getAllObjects() {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public List<SuperAppObjectBoundary> getAllObjects(String userSuperapp, String userEmail, int page, int size) {
        boolean seeActive = isSuperAppUser(userSuperapp, userEmail);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationTimestamp");
        Stream<SuperAppObjectEntity> results = seeActive ?
                this.objectCrud.findAll(pageable).stream() :    // Stream<SuperAppObjectEntity> (all objects)
                this.objectCrud.findAllByActiveIsTrue(pageable).stream();   // Stream<SuperAppObjectEntity> (only active objects)
        return results
                .map(this::entityToBoundary)    // Stream<SuperAppObjectBoundary>
                .toList();  // List<SuperAppObjectBoundary>
    }

    @Override
    @Deprecated
    public void deleteAllObjects() {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public void deleteAllObjects(String userSuperapp, String userEmail) {
        checkUserAuthorization(userSuperapp, userEmail, UserRole.ADMIN);
        this.objectCrud.deleteAll();
        this.logger.info("*** All the objects hase been deleted");
    }

    @Override
    public void updateChildren(SuperAppObjectIdBoundary parentId, SuperAppObjectIdBoundary childId, String userSuperapp, String userEmail) {
        checkUserAuthorization(userSuperapp, userEmail, UserRole.SUPERAPP_USER);
        SuperAppObjectEntity parent =
                this.objectCrud
                        .findById(boundaryToEntity(parentId))
                        .orElseThrow(() -> new SuperAppObjectNotFoundException("Could not find parent object by id: " + parentId));
        SuperAppObjectEntity child =
                this.objectCrud
                        .findById(boundaryToEntity(childId))
                        .orElseThrow(() -> new SuperAppObjectNotFoundException("could not find child object by id: " + childId));
        SuperAppObjectEntity oldParent = child.getParents();
        if (oldParent != null) {
            oldParent.removeChild(child);
            this.objectCrud.save(oldParent);
        }
        parent.addChild(child);
        child.setParents(parent);
        this.objectCrud.save(parent);
        this.objectCrud.save(child);
        this.logger.info("*** New relationship created between parent: " + parent + " and child: " + child);
    }

    // TODO - move the pagination and the filtering to the DB
    @Override
    public List<SuperAppObjectBoundary> getChildren(SuperAppObjectIdBoundary parentId, String userSuperapp, String userEmail, int page, int size) {
        boolean seeActive = isSuperAppUser(userSuperapp, userEmail);
        Optional<SuperAppObjectEntity> parent = seeActive ?
                this.objectCrud.findById(boundaryToEntity(parentId)) :  // see all objects
                this.objectCrud.findByObjectIdAndActiveIsTrue(boundaryToEntity(parentId));    // see only active objects
        return parent.map(p -> p.getChildren()
                        .stream()   // Stream<SuperAppObjectEntity>
                        .filter(c -> seeActive || c.getActive())   // Stream<SuperAppObjectEntity> (if seeActive is true, then all children are returned, otherwise only active children)
                        .skip((long) size * page)   // skip the first page * size elements
                        .limit(size)    // limit the results to size elements
                        .map(this::entityToBoundary)    // Stream<SuperAppObjectBoundary>
                        .toList())  // List<SuperAppObjectBoundary>
                .orElseGet(ArrayList::new);   // new List<SuperAppObjectBoundary> (empty list)
    }

    // TODO - move the pagination and the filtering to the DB
    @Override
    public List<SuperAppObjectBoundary> getParents(SuperAppObjectIdBoundary childId, String userSuperapp, String userEmail, int page, int size) {
        boolean seeActive = isSuperAppUser(userSuperapp, userEmail);
        Optional<SuperAppObjectEntity> child = seeActive ?
                this.objectCrud.findById(boundaryToEntity(childId)) :   // see all objects
                this.objectCrud.findByObjectIdAndActiveIsTrue(boundaryToEntity(childId)); // see only active objects
        Set<SuperAppObjectEntity> parents = new HashSet<>();
        if (child.isPresent() && child.get().getParents() != null)  // check if child exists and has parents
            parents.add(child.get().getParents());
        return parents
                .stream() // Stream
                .filter(c -> seeActive || c.getActive())   // Stream<SuperAppObjectEntity> (if seeActive is true, then all parents are returned, otherwise only active parents)
                .skip((long) size * page)   // skip the first page * size elements
                .limit(size)    // limit the results to size elements
                .map(this::entityToBoundary) // Stream<SuperAppObjectBoundary>
                .toList(); // List<SuperAppObjectBoundary>
    }

    @Override
    public List<SuperAppObjectBoundary> getAllObjectsByType(String type, String userSuperapp, String userEmail, int page, int size) {
        boolean seeActive = isSuperAppUser(userSuperapp, userEmail);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationTimestamp");
        Stream<SuperAppObjectEntity> results = seeActive ?
                this.objectCrud.findAllByType(type, pageable).stream() :    // Stream<SuperAppObjectEntity> (all objects)
                this.objectCrud.findAllByTypeAndActiveIsTrue(type, pageable).stream();   // Stream<SuperAppObjectEntity> (only active objects)
        return results
                .map(this::entityToBoundary)    // Stream<SuperAppObjectBoundary>
                .toList();  // List<SuperAppObjectBoundary>
    }

    @Override
    public List<SuperAppObjectBoundary> getAllObjectsByAlias(String alias, String userSuperapp, String userEmail, int page, int size) {
        boolean seeActive = isSuperAppUser(userSuperapp, userEmail);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationTimestamp");
        Stream<SuperAppObjectEntity> results = seeActive ?
                this.objectCrud.findAllByAlias(alias, pageable).stream() :    // Stream<SuperAppObjectEntity> (all objects)
                this.objectCrud.findAllByAliasAndActiveIsTrue(alias, pageable).stream();   // Stream<SuperAppObjectEntity> (only active objects)
        return results
                .map(this::entityToBoundary)    // Stream<SuperAppObjectBoundary>
                .toList();  // List<SuperAppObjectBoundary>
    }

    // TODO - write the query to get all objects by location
    @Override
    public List<SuperAppObjectBoundary> getAllObjectsByLocation(float lat, float lng, float distance, String units, String userSuperapp, String userEmail, int page, int size) {
        boolean seeActive = isSuperAppUser(userSuperapp, userEmail);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationTimestamp");
        Stream<SuperAppObjectEntity> results = seeActive ?
                this.objectCrud.findAllByLocationNear(new Point(lat, lng), new Distance(distance, getMetrics(units)), pageable).stream() :    // Stream<SuperAppObjectEntity> (all objects)
                this.objectCrud.findAllByLocationNearAndActiveIsTrue(new Point(lat, lng), new Distance(distance,  getMetrics(units)), pageable).stream();   // Stream<SuperAppObjectEntity> (only active objects)
        return results
                .map(this::entityToBoundary)    // Stream<SuperAppObjectBoundary>
                .toList();  // List<SuperAppObjectBoundary>
    }

    private Metrics getMetrics(String units) {
        switch (units.toUpperCase()) {
            case "NEUTRAL":
                return Metrics.NEUTRAL;
            case "KILOMETERS":
                return Metrics.KILOMETERS;
            case "MILES":
                return Metrics.MILES;
            default:    //  Bad units
                throw new BadRequestException("Bad units: " + units);
        }
    }
}