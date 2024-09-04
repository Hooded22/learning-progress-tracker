package tracker.points;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PointsRepository {
    List<Point> points = new ArrayList<>();

    public PointsRepository() {
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point findPointByStudentIdAndCourseId(String studentId, String courseId) throws PointNotFound {
        return getPoints().stream().filter((point) -> Objects.equals(point.getStudentId(), studentId) && Objects.equals(point.getCourseId(), courseId)).findFirst().orElseThrow(PointNotFound::new);
    }

    public List<Point> findAllPointsByStudentIdAndCourseId(String studentId, String courseId) {
        return getPoints().stream().filter((point) -> Objects.equals(point.getStudentId(), studentId) && Objects.equals(point.getCourseId(), courseId)).collect(Collectors.toList());
    }

    public List<Point> findPointByCourseId(String courseId) {
        return getPoints().stream().filter((point) -> Objects.equals(point.getCourseId(), courseId)).toList();
    }

    public List<Point> findPointByStudentId(String studentId) {
        return getPoints().stream().filter((point) -> Objects.equals(point.getStudentId(), studentId)).toList();
    }

    public void updatePointByStudentIdAndCourseName(String studentId, String courseId, Point point) {
        points.set(getPoints().indexOf(point), point);
    }
}
