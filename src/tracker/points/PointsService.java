package tracker.points;

import java.util.List;

//TODO: Consider rename to SubmissionsService
public class PointsService {
    final PointsRepository pointsRepository;

    public PointsService(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    public void updatePoint(String studentId, String courseId, int pointsNumberToUpdate) {
        try {
            Point point = pointsRepository.findPointByStudentIdAndCourseId(studentId, courseId);
            point.setPointsNumber(point.getPointsNumber() + pointsNumberToUpdate);

            pointsRepository.updatePointByStudentIdAndCourseName(studentId, courseId, point);
        } catch (PointNotFound ex) {
            this.addPoint(studentId, courseId, pointsNumberToUpdate);
        }
    }

    public void addPoint(String studentId, String courseId, int pointsNumber) {
        Point point = new Point(studentId, courseId, pointsNumber);
        pointsRepository.addPoint(point);
    }

    public int getPointsValueForCourse(String courseId, String studentId) {
        List<Point> points = pointsRepository.findAllPointsByStudentIdAndCourseId(studentId, courseId);

        if (points.isEmpty()) {
            return 0;
        }

        return points.stream().reduce(0, (result, point) -> result + point.getPointsNumber(), Integer::sum);
    }

    public List<Point> getPointsForCourse(String courseId) {
        return pointsRepository.findPointByCourseId(courseId);
    }

    public List<Point> getPointsForStudent(String studentId) {
        return pointsRepository.findPointByStudentId(studentId);
    }

    public List<Point> getPointsByStudentIdAndCourseId(String studentId, String courseId) {
        return pointsRepository.findAllPointsByStudentIdAndCourseId(studentId, courseId);
    }

    public int getPointsCountForCourse(String courseId) {
        return pointsRepository.findPointByCourseId(courseId).size();
    }

    public List<Point> getAllPoints() {
        return pointsRepository.getPoints();
    }


}
