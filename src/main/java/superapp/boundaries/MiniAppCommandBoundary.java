package superapp.boundaries;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Map;

public class MiniAppCommandBoundary {
    private CommandIdBoundary commandId;
    @NotBlank(message = "command cannot be empty or null")
    private String command;
    @Valid
    private TargetObjectBoundary targetObject;
    private Date invocationTimestamp;
    @Valid
    private InvokedByBoundary invokedBy;
    private Map<String, Object> commandAttributes;

    public MiniAppCommandBoundary() {
    }

    public CommandIdBoundary getCommandId() {
        return commandId;
    }

    public void setCommandId(CommandIdBoundary commandId) {
        this.commandId = commandId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public TargetObjectBoundary getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(TargetObjectBoundary targetObject) {
        this.targetObject = targetObject;
    }

    public Date getInvocationTimestamp() {
        return invocationTimestamp;
    }

    public void setInvocationTimestamp(Date invocationTimestamp) {
        this.invocationTimestamp = invocationTimestamp;
    }

    public InvokedByBoundary getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(InvokedByBoundary invokedBy) {
        this.invokedBy = invokedBy;
    }

    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }

    public void setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
    }

    @Override
    public String toString() {
        return "MiniAppCommandBoundary{" +
                "commandId=" + commandId +
                ", command='" + command + '\'' +
                ", targetObject=" + targetObject +
                ", invocationTimestamp=" + invocationTimestamp +
                ", invokedBy=" + invokedBy +
                ", commandAttributes=" + commandAttributes +
                '}';
    }
}
