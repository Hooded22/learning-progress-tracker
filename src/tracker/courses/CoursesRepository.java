package tracker.courses;

import java.util.ArrayList;
import java.util.List;

public class CoursesRepository {
    private final List<Course> courses = new ArrayList<>();

    public CoursesRepository() {
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
