package tracker.courses;

import tracker.constants.CoursesNames;
import tracker.notifications.NotificationsService;
import tracker.points.Point;
import tracker.points.PointsMessages;
import tracker.points.PointsService;
import tracker.points.exceptions.IncorrectPointFormat;
import tracker.students.Student;
import tracker.students.StudentsService;
import tracker.students.exceptions.StudentNotFound;
import tracker.validation.ValidationService;

import java.util.List;

public class CourseManagementService {
    final CoursesService coursesService;
    final PointsService pointsService;
    final ValidationService validationService;
    final NotificationsService notificationsService;
    final StudentsService studentsService;


    public CourseManagementService(CoursesService coursesService, PointsService pointsService, ValidationService validationService, StudentsService studentsService, NotificationsService notificationsService) {
        this.coursesService = coursesService;
        this.pointsService = pointsService;
        this.validationService = validationService;
        this.studentsService = studentsService;
        this.notificationsService = notificationsService;
    }

    public void addPointFromUserInput(String commandInput) throws IncorrectPointFormat, StudentNotFound {
        String[] credentials = commandInput.split("\\s+", 5);

        if (credentials.length != 5) {
            throw new IncorrectPointFormat();
        }

        try {
            String studentId = credentials[0];
            int javaPoints = Integer.parseInt(credentials[1]);
            int dsPoints = Integer.parseInt(credentials[2]);
            int dbPoints = Integer.parseInt(credentials[3]);
            int springPoints = Integer.parseInt(credentials[4]);

            validationService.validateStudentExist(studentId);

            String javaCourseId = coursesService.getCourseIdByName(CoursesNames.JAVA.getMessage());
            String dsCourseId = coursesService.getCourseIdByName(CoursesNames.DATA_STRUCTURES.getMessage());
            String dbCourseId = coursesService.getCourseIdByName(CoursesNames.DATABASES.getMessage());
            String springCourseId = coursesService.getCourseIdByName(CoursesNames.SPRING.getMessage());

            pointsService.addPoint(studentId, javaCourseId, javaPoints);
            pointsService.addPoint(studentId, springCourseId, springPoints);
            pointsService.addPoint(studentId, dsCourseId, dsPoints);
            pointsService.addPoint(studentId, dbCourseId, dbPoints);

            addNotificationForUserWhoCompletedCourse(studentId, javaCourseId);
            addNotificationForUserWhoCompletedCourse(studentId, springCourseId);
            addNotificationForUserWhoCompletedCourse(studentId, dsCourseId);
            addNotificationForUserWhoCompletedCourse(studentId, dbCourseId);

            System.out.println(PointsMessages.POINTS_UPDATED.getMessage());
        } catch (NumberFormatException e) {
            throw new IncorrectPointFormat();
        }
    }

    private void addNotificationForUserWhoCompletedCourse(String studentId, String courseId) throws StudentNotFound {
        List<Point> pointsForStudentAndCourse = pointsService.getPointsByStudentIdAndCourseId(studentId, courseId);
        Course course = coursesService.getCourseById(courseId);
        int pointsValue = pointsForStudentAndCourse.stream().mapToInt(Point::getPointsNumber).sum();
        boolean studentCompletedCourse = pointsValue >= course.getMaxPointsValue();

        if (studentCompletedCourse) {
            Student student = studentsService.getStudentByID(studentId);
            notificationsService.addCourseCompletedNotification(course, student, pointsForStudentAndCourse.size());
        }
    }

    //TODO: enrollToCourse (new point + increase students number in Course (?))
    //TODO: submitTask (add new point + submission to course (?))
}
