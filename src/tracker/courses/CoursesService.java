package tracker.courses;

import tracker.courses.exceptions.CourseNotFound;
import tracker.points.PointsService;

import java.util.List;
import java.util.Objects;

public class CoursesService {
    CoursesRepository coursesRepository;
    PointsService pointsService;

    public CoursesService(CoursesRepository coursesRepository, PointsService pointsService) {
        this.coursesRepository = coursesRepository;
        this.pointsService = pointsService;
    }

    public List<Course> getAllCourses() {
        return coursesRepository.getCourses();
    }

    public Course getCourseByName(String courseName) throws CourseNotFound {
        return coursesRepository.getCourses().stream().filter((course -> Objects.equals(course.getName(), courseName))).findFirst().orElseThrow(() -> new CourseNotFound(courseName));
    }

    public Course getCourseById(String courseId) throws CourseNotFound {
        return coursesRepository.getCourses().stream().filter((course -> Objects.equals(course.getId(), courseId))).findFirst().orElseThrow(() -> new CourseNotFound(courseId));
    }

    public String getCourseIdByName(String courseName) throws CourseNotFound {
        return this.getCourseByName(courseName).getId();
    }

    public int getNumberOfStudentsAssignedToCourse(String courseId) throws CourseNotFound {
        return pointsService.getPointsForCourse(courseId).size();
    }

    public int getSumOfCoursePoints(String courseId) throws CourseNotFound {
        return pointsService.getPointsForCourse(courseId).stream().reduce(0, (result, point) -> result + point.getPointsNumber(), Integer::sum);
    }

    public int getCourseAvgPoints(String courseId) throws CourseNotFound {
        int coursePoints = getSumOfCoursePoints(courseId);
        int pointsCount = pointsService.getPointsCountForCourse(courseId);

        if (pointsCount == 0) {
            return 0;
        }

        return coursePoints / pointsCount;
    }

    public boolean areAllCoursesWithoutPoints() throws CourseNotFound {
        return pointsService.getAllPoints().isEmpty();
    }
}
