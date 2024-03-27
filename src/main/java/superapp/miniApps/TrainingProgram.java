package superapp.miniApps;

import java.util.Date;

public class TrainingProgram {
    private String instructions;
    private Date date;
    private String notes;
    private Boolean completed;

    public TrainingProgram() {
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "TrainingProgram [instructions=" + instructions + ", notes=" + notes + ", completed=" + completed + "]";
    }
}
