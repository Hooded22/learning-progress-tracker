package tracker.points;

import tracker.points.exceptions.IncorrectPointFormat;

public class Point {
    private String studentId;
    private String courseId;
    private int pointsNumber;


    public Point(String studentId, String courseId, int pointsNumber) {
        this.studentId = studentId;
        this.courseId = courseId;

        validatePointValue(pointsNumber);

        this.pointsNumber = pointsNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getPointsNumber() {
        return pointsNumber;
    }

    public void setPointsNumber(int pointsNumber) {
        validatePointValue(pointsNumber);

        this.pointsNumber = pointsNumber;
    }

    private void validatePointValue(int pointValue) throws IncorrectPointFormat {
        if (pointValue < 0) {
            throw new IncorrectPointFormat();
        }
    }
}
