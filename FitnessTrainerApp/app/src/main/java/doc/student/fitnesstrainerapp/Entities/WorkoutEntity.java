package doc.student.fitnesstrainerapp.Entities;

public abstract class WorkoutEntity {
    private String name;
    private String repetitionType;
    private String repetitionItem;


    public WorkoutEntity(String name, String repetitionType, String repetitionItem) {
        this.name = name;
        this.repetitionType = repetitionType;
        this.repetitionItem = repetitionItem;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepetitionType() {
        return repetitionType;
    }

    public void setRepetitionType(String repetitionType) {
        this.repetitionType = repetitionType;
    }

    public String getRepetitionItem() {
        return repetitionItem;
    }

    public void setRepetitionItem(String repetitionItem) {
        this.repetitionItem = repetitionItem;
    }
}
