package tracker.courses;

import java.util.UUID;

public class Course {
    private final String id;
    private String name;
    private int maxPointsValue;


    public Course(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public Course(String name, int maxPointsValue) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.maxPointsValue = maxPointsValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public int getMaxPointsValue() {
        return maxPointsValue;
    }

    public void setMaxPointsValue(int maxPointsValue) {
        this.maxPointsValue = maxPointsValue;
    }
}
