package tracker.students;

import tracker.courses.Course;

public class GraduatedStudent {
    private final Student student;
    private final Course course;
    private final int submissionsNumber;

    public GraduatedStudent(Student student, Course course, int submissionsNumber) {
        this.student = student;
        this.course = course;
        this.submissionsNumber = submissionsNumber;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getSubmissionsNumber() {
        return submissionsNumber;
    }
}
