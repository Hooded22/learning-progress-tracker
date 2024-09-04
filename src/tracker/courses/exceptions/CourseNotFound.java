package tracker.courses.exceptions;

public class CourseNotFound extends RuntimeException {
    public CourseNotFound(String courseName) {
        super("Course " + courseName + " not found");
    }
}
