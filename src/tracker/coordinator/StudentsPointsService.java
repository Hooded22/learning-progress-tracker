package tracker.coordinator;

import tracker.constants.CoursesNames;
import tracker.courses.CoursesService;
import tracker.points.PointsService;
import tracker.students.Student;
import tracker.students.StudentMessages;
import tracker.students.StudentsService;
import tracker.students.exceptions.StudentNotFound;

public class StudentsPointsService {
    PointsService pointsService;
    StudentsService studentsService;
    CoursesService coursesService;

    public StudentsPointsService(PointsService pointsService, StudentsService studentsService, CoursesService coursesService) {
        this.pointsService = pointsService;
        this.studentsService = studentsService;
        this.coursesService = coursesService;
    }

    public void displayStudentPoints(String studentID) throws StudentNotFound {
        Student student = studentsService.getStudentByID(studentID);


        int javaPoints = pointsService.getPointsValueForCourse(coursesService.getCourseByName(CoursesNames.JAVA.getMessage()).getId(), studentID);
        int springPoints = pointsService.getPointsValueForCourse(coursesService.getCourseByName(CoursesNames.SPRING.getMessage()).getId(), studentID);
        int dsPoints = pointsService.getPointsValueForCourse(coursesService.getCourseByName(CoursesNames.DATA_STRUCTURES.getMessage()).getId(), studentID);
        int dbPoints = pointsService.getPointsValueForCourse(coursesService.getCourseByName(CoursesNames.DATABASES.getMessage()).getId(), studentID);

        String formattedMessage = String.format(
                StudentMessages.STUDENT_POINTS.getMessage(),
                student.getID(),
                javaPoints,
                springPoints,
                dsPoints,
                dbPoints
        );

        System.out.println(formattedMessage);
    }
}
