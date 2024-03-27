package superapp.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import superapp.boundaries.*;
import superapp.data.*;
import superapp.miniApps.getParks.GetParksMethods;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:6002") // change 6002 to your client port number
@Service
public class MiniAppCommandServiceImp implements MiniAppCommandServiceWithRoleAuthorizationAndAsyncAndPaginationSupport {
    private final MiniAppCommandCrud commandCrud;
    private final UserCrud userCrud;
    private final SuperAppObjectCrud objectCrud;
    private final Log logger = LogFactory.getLog(MiniAppCommandServiceImp.class);
    private String springApplicationName;
    private ObjectMapper jackson;
    private JmsTemplate jmsTemplate;
    private GetParksMethods parkMethods;

    @Value("${googleAPI.key}")
    private String mapsKey;

    @Autowired
    public MiniAppCommandServiceImp(MiniAppCommandCrud commandCrud, UserCrud userCrud, SuperAppObjectCrud objectCrud) {
        this.commandCrud = commandCrud;
        this.userCrud = userCrud;
        this.objectCrud = objectCrud;
    }

    // this method injects a configuration value of spring
    @Value("${spring.application.name:iAmTheDefaultNameOfTheApplication}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springApplicationName = springApplicationName;
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.jmsTemplate.setDeliveryDelay(3000L);
    }

    // this method is invoked after values are injected to instance
    @PostConstruct
    public void init() {
        this.logger.info("***** MiniAppCommandService is running, " + this.springApplicationName);
        this.jackson = new ObjectMapper();
    }

    public MiniAppCommandEntity boundaryToEntity(MiniAppCommandBoundary boundary) {
        MiniAppCommandEntity entity = new MiniAppCommandEntity();
        entity.setCommandId(this.boundaryToEntity(boundary.getCommandId()));
        entity.setCommand(boundary.getCommand());
        entity.setTargetObject(this.boundaryToEntity(boundary.getTargetObject()));
        entity.setInvocationTimestamp(boundary.getInvocationTimestamp());
        entity.setInvokedBy(this.boundaryToEntity(boundary.getInvokedBy()));
        entity.setCommandAttributes(boundary.getCommandAttributes());
        return entity;
    }

    public MiniAppCommandBoundary entityToBoundary(MiniAppCommandEntity entity) {
        MiniAppCommandBoundary boundary = new MiniAppCommandBoundary();
        boundary.setCommandId(this.entityToBoundary(entity.getCommandId()));
        boundary.setCommand(entity.getCommand());
        boundary.setTargetObject(this.entityToBoundary(entity.getTargetObject()));
        boundary.setInvocationTimestamp(entity.getInvocationTimestamp());
        boundary.setInvokedBy(this.entityToBoundary(entity.getInvokedBy()));
        boundary.setCommandAttributes(entity.getCommandAttributes());
        return boundary;
    }

    public CommandIdEntity boundaryToEntity(CommandIdBoundary boundary) {
        CommandIdEntity entity = new CommandIdEntity();
        entity.setSuperapp(boundary.getSuperapp());
        entity.setMiniapp(boundary.getMiniapp());
        entity.setInternalCommandId(boundary.getInternalCommandId());
        return entity;
    }

    public CommandIdBoundary entityToBoundary(CommandIdEntity entity) {
        CommandIdBoundary boundary = new CommandIdBoundary();
        boundary.setSuperapp(entity.getSuperapp());
        boundary.setMiniapp(entity.getMiniapp());
        boundary.setInternalCommandId(entity.getInternalCommandId());
        return boundary;
    }

    public TargetObjectEntity boundaryToEntity(TargetObjectBoundary boundary) {
        SuperAppObjectIdBoundary objectIdBoundary = boundary.getObjectId();
        SuperAppObjectIdEntity objectIdEntity = new SuperAppObjectIdEntity(objectIdBoundary.getSuperapp(), objectIdBoundary.getInternalObjectId());
        return new TargetObjectEntity(objectIdEntity);
    }

    public TargetObjectBoundary entityToBoundary(TargetObjectEntity entity) {
        SuperAppObjectIdEntity objectIdEntity = entity.getObjectId();
        SuperAppObjectIdBoundary objectIdBoundary = new SuperAppObjectIdBoundary(objectIdEntity.getSuperapp(), objectIdEntity.getInternalObjectId());
        return new TargetObjectBoundary(objectIdBoundary);
    }

    public InvokedByEntity boundaryToEntity(InvokedByBoundary boundary) {
        UserIdBoundary userIdBoundary = boundary.getUserId();
        UserIdEntity userIdEntity = new UserIdEntity(userIdBoundary.getSuperapp(), userIdBoundary.getEmail());
        return new InvokedByEntity(userIdEntity);
    }

    public InvokedByBoundary entityToBoundary(InvokedByEntity entity) {
        UserIdEntity userIdEntity = entity.getUserId();
        UserIdBoundary userIdBoundary = new UserIdBoundary(userIdEntity.getSuperapp(), userIdEntity.getEmail());
        return new InvokedByBoundary(userIdBoundary);
    }

    // TODO - move duplicated code to a shared class
    public SuperAppObjectIdEntity boundaryToEntity(SuperAppObjectIdBoundary boundary) {
        return new SuperAppObjectIdEntity(boundary.getSuperapp(), boundary.getInternalObjectId());
    }

    // TODO - move duplicated code to a shared class
    public SuperAppObjectIdBoundary entityToBoundary(SuperAppObjectIdEntity entity) {
        return new SuperAppObjectIdBoundary(entity.getSuperapp(), entity.getInternalObjectId());
    }

    // TODO - move duplicated code to a shared class
    void checkUserAuthorization(String userSuperapp, String userEmail, UserRole role) {
        UserEntity user = this.userCrud.findByUserId_SuperappAndUserId_Email(userSuperapp, userEmail)
                .orElseThrow(() -> new UserNotFoundException("Could not find user by email: " + userEmail));
        if (!user.getRole().equals(role))
            throw new UserNotAuthorizedException("User is not authorized to perform this action");
    }

    @Override
    @Deprecated
    public Object invokeCommand(MiniAppCommandBoundary command) {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public Object invokeCommand(MiniAppCommandBoundary command, boolean async) {
        UserIdBoundary userId = command.getInvokedBy().getUserId();
        checkUserAuthorization(userId.getSuperapp(), userId.getEmail(), UserRole.MINIAPP_USER);

        SuperAppObjectIdEntity ObjectId = boundaryToEntity(command.getTargetObject().getObjectId());
        this.objectCrud.findByObjectIdAndActiveIsTrue(ObjectId)
                .orElseThrow(() -> new SuperAppObjectNotFoundException("Could not find Object by id: " + ObjectId));
        command.getCommandId().setSuperapp(this.springApplicationName);
        command.getCommandId().setInternalCommandId(UUID.randomUUID().toString());
        command.setInvocationTimestamp(new Date());
        if (command.getCommandAttributes() == null)
            command.setCommandAttributes(new HashMap<>());
        if (async) {
            command.getCommandAttributes().put("status", "in-process...");
            try {
                String json = this.jackson
                        .writeValueAsString(command);
                // send json to commandsQueue
                this.logger.info("*** Sending: " + json);
                this.jmsTemplate
                        .convertAndSend("commandsQueue", json);
                return command;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            MiniAppCommandEntity entity = this.boundaryToEntity(command);

            // if command name == locatingDogParks: 
            if (entity.getCommand().equals("getParks")) {
                this.parkMethods = new GetParksMethods();
                LocationBoundary location = this.parkMethods.getLocationInMap(command.getCommandAttributes());
                Object parks = this.parkMethods.getParksByLatLng(
                        String.valueOf(location.getLat()),
                        String.valueOf(location.getLng()),
                        this.mapsKey,
                        command.getInvokedBy());
                return parks;
            } else {
                entity = this.commandCrud.save(entity);
                this.logger.info("*** New Command invoked: " + entity);
                return entityToBoundary(entity);
            }

            // End of logic

        }
    }

    @Override
    @Deprecated
    public List<MiniAppCommandBoundary> getAllCommands() {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public List<MiniAppCommandBoundary> getAllCommands(String userSuperapp, String userEmail, int page, int size) {
        checkUserAuthorization(userSuperapp, userEmail, UserRole.ADMIN);
        return this.commandCrud
                .findAll(PageRequest.of(page, size, Sort.Direction.DESC, "invocationTimestamp"))    // Page<MiniAppCommandEntity> (sorted by invocationTimestamp)
                .stream()   // Stream<MiniAppCommandEntity>
                .map(this::entityToBoundary)    // Stream<MiniAppCommandBoundary>
                .toList();  // List<MiniAppCommandBoundary>
    }

    @Override
    @Deprecated
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName) {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName, String userSuperapp, String userEmail, int page, int size) {
        checkUserAuthorization(userSuperapp, userEmail, UserRole.ADMIN);
        return this.commandCrud
                .findAllByCommandId_Miniapp(miniAppName, PageRequest.of(page, size, Sort.Direction.DESC, "invocationTimestamp"))    // List<MiniAppCommandEntity> (only commands of the given miniapp, sorted by invocationTimestamp)
                .stream()   // Stream<MiniAppCommandEntity>
                .map(this::entityToBoundary // Stream<MiniAppCommandBoundary>
                ).toList(); // List<MiniAppCommandBoundary>
    }

    @Override
    @Deprecated
    public void deleteAllCommands() {
        throw new DeprecatedOperationException("Do not use this operation any more, as it is deprecated");
    }

    @Override
    public void deleteAllCommands(String userSuperapp, String userEmail) {
        checkUserAuthorization(userSuperapp, userEmail, UserRole.ADMIN);
        this.commandCrud.deleteAll();
        this.logger.info("*** All the commands history has been deleted");
    }

    // TODO - add logic to handle the commands (base on the use for the mini applications)
    @JmsListener(destination = "commandsQueue")
    public void handleCommand(String json) {
        try {
            // convert json to boundary
            // than convert boundary to entity
            // than store entity in database
            // than convert entity back to boundary
            // than print the boundary to the console
            this.logger.info("*** New Command invoked: " +
                    this.entityToBoundary(
                            this.commandCrud
                                    .save(
                                            this.boundaryToEntity(
                                                    this.setStatus(
                                                            this.jackson.
                                                                    readValue(json, MiniAppCommandBoundary.class), "remotely-accepted")))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MiniAppCommandBoundary setStatus(MiniAppCommandBoundary command, String status) {
        if (command.getCommandAttributes() == null)
            command.setCommandAttributes(new HashMap<>());
        command.getCommandAttributes().put("status", status);
        return command;
    }
}