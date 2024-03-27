package superapp.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Document(collection = "COMMANDS")
public class MiniAppCommandEntity {
    @Id
    private CommandIdEntity commandId;
    private String command;
    private TargetObjectEntity targetObject;
    private Date invocationTimestamp;
    private InvokedByEntity invokedBy;
    private Map<String, Object> commandAttributes;

    public MiniAppCommandEntity() {
    }

    public CommandIdEntity getCommandId() {
        return commandId;
    }

    public void setCommandId(CommandIdEntity commandId) {
        this.commandId = commandId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public TargetObjectEntity getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(TargetObjectEntity targetObject) {
        this.targetObject = targetObject;
    }

    public Date getInvocationTimestamp() {
        return invocationTimestamp;
    }

    public void setInvocationTimestamp(Date invocationTimestamp) {
        this.invocationTimestamp = invocationTimestamp;
    }

    public InvokedByEntity getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(InvokedByEntity invokedBy) {
        this.invokedBy = invokedBy;
    }

    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }

    public void setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MiniAppCommandEntity other = (MiniAppCommandEntity) obj;
        return Objects.equals(commandId, other.commandId);
    }

    @Override
    public String toString() {
        return "MiniAppCommandEntity{" +
                "commandId=" + commandId +
                ", command='" + command + '\'' +
                ", targetObject=" + targetObject +
                ", invocationTimestamp=" + invocationTimestamp +
                ", invokedBy=" + invokedBy +
                ", commandAttributes=" + commandAttributes +
                '}';
    }
}
