package src.main.java.org.example.flyweight;

public class TaskFlyweight {
    private final String description;

    public TaskFlyweight(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}