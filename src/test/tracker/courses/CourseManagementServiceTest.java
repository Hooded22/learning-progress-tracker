package test.tracker.courses;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import tracker.courses.Course;
import tracker.courses.CourseManagementService;
import tracker.courses.CoursesService;
import tracker.notifications.NotificationsService;
import tracker.points.Point;
import tracker.points.PointsMessages;
import tracker.points.PointsService;
import tracker.points.exceptions.IncorrectPointFormat;
import tracker.students.Student;
import tracker.students.StudentsService;
import tracker.students.exceptions.StudentNotFound;
import tracker.validation.ValidationService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class CourseManagementServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    StudentsService studentsService;
    PointsService pointsService;
    CoursesService coursesService;
    ValidationService validationService;
    CourseManagementService courseManagementService;
    NotificationsService notificationsService;

    @BeforeEach
    void setUp() {
        studentsService = Mockito.mock(StudentsService.class);
        coursesService = Mockito.mock(CoursesService.class);
        validationService = Mockito.mock(ValidationService.class);
        pointsService = Mockito.mock(PointsService.class);
        notificationsService = Mockito.mock(NotificationsService.class);

        courseManagementService = new CourseManagementService(coursesService, pointsService, validationService, studentsService, notificationsService);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void addPointFromUserInput_correctInput_shouldCallPointsServiceAddPoint() throws StudentNotFound {
        String credentials = "abc123 123 123 123 123";
        String courseID = "mocked-course-i";
        Mockito.when(coursesService.getCourseIdByName(ArgumentMatchers.any())).thenReturn(courseID);
        Mockito.when(coursesService.getCourseById(ArgumentMatchers.anyString())).thenReturn(new Course("Test course"));

        courseManagementService.addPointFromUserInput(credentials);

        Mockito.verify(pointsService, Mockito.times(4)).addPoint(ArgumentMatchers.eq("abc123"), ArgumentMatchers.eq("mocked-course-i"), ArgumentMatchers.eq(123));
    }

    @Test
    void addPointFromUserInput_correctInput_shouldDisplayMessage() throws StudentNotFound {
        String credentials = "abc123 123 123 123 123";
        String courseID = "mocked-course-i";
        Mockito.when(coursesService.getCourseIdByName(ArgumentMatchers.anyString())).thenReturn(courseID);
        Mockito.when(coursesService.getCourseById(ArgumentMatchers.anyString())).thenReturn(new Course("Test course"));

        courseManagementService.addPointFromUserInput(credentials);

        Assertions.assertEquals(outContent.toString().trim(), PointsMessages.POINTS_UPDATED.getMessage());
    }

    @Test
    void addPointFromUserInput_correctInput_shouldAddNotification() throws StudentNotFound {
        Student student = new Student("Jan", "Nowak", "jan.nowak@gmail.com");
        String credentials = "abc123 123 123 123 123";
        Course course = new Course("Test course", 10);
        List<Point> points = new ArrayList<>();

        points.add(new Point("abc123", course.getId(), 10));

        Mockito.when(coursesService.getCourseIdByName(ArgumentMatchers.anyString())).thenReturn(course.getId());
        Mockito.when(coursesService.getCourseById(ArgumentMatchers.anyString())).thenReturn(course);
        Mockito.when(pointsService.getPointsByStudentIdAndCourseId(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(points);
        Mockito.when(studentsService.getStudentByID(ArgumentMatchers.anyString())).thenReturn(student);

        courseManagementService.addPointFromUserInput(credentials);

        Mockito.verify(notificationsService, Mockito.times(4)).addCourseCompletedNotification(ArgumentMatchers.eq(course), ArgumentMatchers.eq(student), ArgumentMatchers.eq(1));
    }

    @Test
    void addPointFromUserInput_inputWithoutSpaces_shouldThrowException() {
        String invalidInput = "invalidInput";

        Assertions.assertThrows(IncorrectPointFormat.class, () -> courseManagementService.addPointFromUserInput(invalidInput));
    }

    @Test
    void addPointFromUserInput_inputWithLessThan5elements_shouldThrowException() {
        String invalidInput = "studentid 123 123 123";

        Assertions.assertThrows(IncorrectPointFormat.class, () -> courseManagementService.addPointFromUserInput(invalidInput));
    }

    @Test
    void addPointFromUserInput_inputWithMoreThan5elements_shouldThrowException() {
        String invalidInput = "studentid 123 123 123 123 123";

        Assertions.assertThrows(IncorrectPointFormat.class, () -> courseManagementService.addPointFromUserInput(invalidInput));
    }

    @Test
    void addPointFromUserInput_incorrectPointsFormat_shouldThrowException() {
        String invalidInput = "studentid 123 123 acv 123";

        Assertions.assertThrows(IncorrectPointFormat.class, () -> courseManagementService.addPointFromUserInput(invalidInput));
    }

    @Test
    void addPointFromUserInput_studentsServiceException_shouldDisplayMessage() throws StudentNotFound {
        String credentials = "studentid 123 123 123 123";
        Mockito.doThrow(new StudentNotFound("studentid")).when(validationService).validateStudentExist(ArgumentMatchers.eq("studentid"));

        Assertions.assertThrows(StudentNotFound.class, () -> courseManagementService.addPointFromUserInput(credentials));
    }

}