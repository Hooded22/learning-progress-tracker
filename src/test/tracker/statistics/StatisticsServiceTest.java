package test.tracker.statistics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import tracker.courses.Course;
import tracker.courses.CoursesService;
import tracker.points.Point;
import tracker.points.PointsService;
import tracker.statistics.BestLearnerStatistics;
import tracker.statistics.CourseStatistics;
import tracker.statistics.StatisticsService;

import java.util.ArrayList;
import java.util.List;

class StatisticsServiceTest {
    StatisticsService statisticsService;
    PointsService pointsService;
    CoursesService coursesService;

    @BeforeEach
    void setUp() {
        coursesService = Mockito.mock(CoursesService.class);
        pointsService = Mockito.mock(PointsService.class);
        statisticsService = new StatisticsService(coursesService, pointsService);
    }


    @Test
    public void getCoursesStats_differentValues_shouldReturnCourseStatistics() {
        List<Course> courses = this.mockCoursesServiceMethods(3);
        Course course1 = courses.get(0);
        Course course3 = courses.get(2);

        CourseStatistics result = statisticsService.getCoursesStats();

        Assertions.assertEquals(1, result.getMostPopularCourses().size());
        Assertions.assertEquals(1, result.getLeastPopularCourses().size());
        Assertions.assertEquals(1, result.getCoursesWithHighestActivity().size());
        Assertions.assertEquals(1, result.getCoursesWithLowestActivity().size());
        Assertions.assertEquals(1, result.getEasiestCourses().size());
        Assertions.assertEquals(1, result.getHardestCourses().size());

        Assertions.assertEquals(course3, result.getMostPopularCourses().get(0));
        Assertions.assertEquals(course1, result.getLeastPopularCourses().get(0));
        Assertions.assertEquals(course3, result.getCoursesWithHighestActivity().get(0));
        Assertions.assertEquals(course1, result.getCoursesWithLowestActivity().get(0));
        Assertions.assertEquals(course1, result.getHardestCourses().get(0));
        Assertions.assertEquals(course3, result.getEasiestCourses().get(0));
    }

    @Test
    public void getCoursesStats_sameValues_shouldReturnCourseStatistics() {
        this.mockCoursesServiceMethodsWithSameReturnedValues(3);

        CourseStatistics result = statisticsService.getCoursesStats();

        Assertions.assertEquals(3, result.getMostPopularCourses().size());
        Assertions.assertEquals(0, result.getLeastPopularCourses().size());
        Assertions.assertEquals(3, result.getCoursesWithHighestActivity().size());
        Assertions.assertEquals(0, result.getCoursesWithLowestActivity().size());
        Assertions.assertEquals(3, result.getEasiestCourses().size());
        Assertions.assertEquals(0, result.getHardestCourses().size());
    }


    @Test
    public void getCoursesStats_empty_data_shouldReturnCourseStatistics() {
        Mockito.when(coursesService.areAllCoursesWithoutPoints()).thenReturn(true);

        CourseStatistics result = statisticsService.getCoursesStats();

        Assertions.assertTrue(result.getMostPopularCourses().isEmpty());
        Assertions.assertTrue(result.getLeastPopularCourses().isEmpty());
        Assertions.assertTrue(result.getCoursesWithHighestActivity().isEmpty());
        Assertions.assertTrue(result.getCoursesWithLowestActivity().isEmpty());
        Assertions.assertTrue(result.getEasiestCourses().isEmpty());
        Assertions.assertTrue(result.getHardestCourses().isEmpty());
    }

    @Test
    public void getBestLearnersForCourse_shouldReturnBestLearnersForCourseInCorrectOrder() {
        Course course = new Course("Course 1", 12);
        List<Point> points = new ArrayList<>();
        points.add(new Point("student-1", course.getId(), 6));
        points.add(new Point("student-1", course.getId(), 60));
        points.add(new Point("student-2", course.getId(), 12));
        points.add(new Point("student-2", course.getId(), 30));
        points.add(new Point("student-3", course.getId(), 3));
        points.add(new Point("student-3", course.getId(), 3));

        Mockito.when(coursesService.getCourseByName(ArgumentMatchers.anyString())).thenReturn(course);
        Mockito.when(pointsService.getPointsForCourse(ArgumentMatchers.anyString())).thenReturn(points);

        List<BestLearnerStatistics> result = statisticsService.getBestLearnersForCourse("Course 1");

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("student-1", result.get(0).getStudentID());
        Assertions.assertEquals("student-2", result.get(1).getStudentID());
        Assertions.assertEquals("student-3", result.get(2).getStudentID());
    }

    private List<Course> mockCoursesServiceMethods(int coursesNumber) {
        List<Course> courses = new ArrayList<>();
        Mockito.when(coursesService.getAllCourses()).thenReturn(courses);

        for (int i = 0; i < coursesNumber; i++) {
            Course course = new Course("Course " + i);
            courses.add(course);

            Mockito.when(coursesService.getNumberOfStudentsAssignedToCourse(ArgumentMatchers.eq(course.getId()))).thenReturn(i + 1);
            Mockito.when(pointsService.getPointsCountForCourse(ArgumentMatchers.eq(course.getId()))).thenReturn(i + 1);
            Mockito.when(coursesService.getCourseAvgPoints(ArgumentMatchers.eq(course.getId()))).thenReturn(i + 1);
        }

        return courses;
    }

    private List<Course> mockCoursesServiceMethodsWithSameReturnedValues(int coursesNumber) {
        List<Course> courses = new ArrayList<>();
        Mockito.when(coursesService.getAllCourses()).thenReturn(courses);

        for (int i = 0; i < coursesNumber; i++) {
            Course course = new Course("Course " + i);
            courses.add(course);

            Mockito.when(coursesService.getNumberOfStudentsAssignedToCourse(ArgumentMatchers.eq(course.getId()))).thenReturn(3);
            Mockito.when(pointsService.getPointsCountForCourse(ArgumentMatchers.eq(course.getId()))).thenReturn(3);
            Mockito.when(coursesService.getCourseAvgPoints(ArgumentMatchers.eq(course.getId()))).thenReturn(3);
        }

        return courses;
    }
}