package test.tracker.points;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import tracker.courses.CoursesService;
import tracker.points.Point;
import tracker.points.PointNotFound;
import tracker.points.PointsRepository;
import tracker.points.PointsService;
import tracker.students.StudentsService;
import tracker.students.exceptions.StudentNotFound;
import tracker.validation.ValidationService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class PointsServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    StudentsService studentsService;
    PointsService pointsService;
    PointsRepository pointsRepository;
    CoursesService coursesService;
    ValidationService validationService;

    @BeforeEach
    void setUp() {
        studentsService = Mockito.mock(StudentsService.class);
        pointsRepository = Mockito.mock(PointsRepository.class);
        coursesService = Mockito.mock(CoursesService.class);
        validationService = Mockito.mock(ValidationService.class);

        pointsService = new PointsService(pointsRepository);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void updatePoint_studentWithoutPoint_shouldCallAddPoint() {
        PointsService pointServiceSpy = Mockito.spy(pointsService);
        Mockito.doThrow(new PointNotFound()).when(pointsRepository).findPointByStudentIdAndCourseId(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

        pointServiceSpy.updatePoint("student-id", "course-id", 12);

        Mockito.verify(pointServiceSpy).addPoint("student-id", "course-id", 12);
    }

    @Test
    void updatePoint_studentWithPoint_shouldSetPointValue() {
        String courseID = "mocked-course-i";
        String studentID = "mocked-student-i";
        Point pointSpy = Mockito.spy(new Point("abc123", courseID, 12));
        Mockito.when(pointsRepository.findPointByStudentIdAndCourseId(ArgumentMatchers.eq(studentID), ArgumentMatchers.eq(courseID))).thenReturn(pointSpy);

        pointsService.updatePoint(studentID, courseID, 123);

        Mockito.verify(pointSpy).setPointsNumber(ArgumentMatchers.eq(12 + 123));
        Mockito.verify(pointsRepository).updatePointByStudentIdAndCourseName(ArgumentMatchers.eq(studentID), ArgumentMatchers.eq(courseID), ArgumentMatchers.eq(pointSpy));
    }

    @Test
    void updatePoint_studentWithPoint_shouldCallPointsRepository() throws StudentNotFound {
        String courseID = "mocked-course-i";
        String studentID = "mocked-student-i";
        Point pointSpy = Mockito.spy(new Point("abc123", courseID, 12));
        Mockito.when(pointsRepository.findPointByStudentIdAndCourseId(ArgumentMatchers.eq(studentID), ArgumentMatchers.eq(courseID))).thenReturn(pointSpy);

        pointsService.updatePoint(studentID, courseID, 123);

        Mockito.verify(pointsRepository).updatePointByStudentIdAndCourseName(ArgumentMatchers.eq(studentID), ArgumentMatchers.eq(courseID), ArgumentMatchers.eq(pointSpy));
    }

    @Test
    void addPoint_shouldCallPointsRepository() throws StudentNotFound {
        pointsService.addPoint("student-id", "course-id", 12);

        Mockito.verify(pointsRepository).addPoint(ArgumentMatchers.isA(Point.class));

    }

    //TODO: Add tests for getPointsValueForCourse, getPointsForCourse and getAllPoints

}