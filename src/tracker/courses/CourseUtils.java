package tracker.courses;

import java.util.Optional;

public class CourseUtils {
    public static String getCourseNameSafely(Course course) {
        return Optional.ofNullable(course)
                .map(Course::getName)
                .orElse("n/a");
    }

    public static int getMaxPointsNumberSafely(Course course) {
        return Optional.of(course.getMaxPointsValue()).orElse(0);
    }

    public static double getCompletedPercent(Course course, int points) {
        int maxPoints = getMaxPointsNumberSafely(course);

        return Math.round(((double) (points * 100) / maxPoints) * 10.0) / 10.0;
    }
}
