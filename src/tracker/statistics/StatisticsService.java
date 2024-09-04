package tracker.statistics;

import tracker.courses.Course;
import tracker.courses.CourseUtils;
import tracker.courses.CoursesService;
import tracker.courses.exceptions.CourseNotFound;
import tracker.points.Point;
import tracker.points.PointsService;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsService {
    final CoursesService coursesService;
    final PointsService pointsService;

    public StatisticsService(CoursesService coursesService, PointsService pointsService) {
        this.coursesService = coursesService;
        this.pointsService = pointsService;
    }

    public CourseStatistics getCoursesStats() {
        boolean allCoursesWithoutPoints = coursesService.areAllCoursesWithoutPoints();

        if (allCoursesWithoutPoints) {
            return getCoursesStatsWithEmptyData();
        }

        List<Course> courses = coursesService.getAllCourses();
        CourseStatistics courseStatistics = new CourseStatistics();

        courses.forEach((course) -> getCourseStatisticsForChosenCourse(course, courseStatistics));

        List<Course> filteredLeastPopularCourses = courseStatistics.getLeastPopularCourses().stream()
                .filter((coursesListItem) -> !courseStatistics.getMostPopularCourses().contains(coursesListItem))
                .collect(Collectors.toList());
        List<Course> filteredCoursesWithLowestActivity = courseStatistics.getCoursesWithLowestActivity().stream()
                .filter((coursesListItem) -> !courseStatistics.getCoursesWithHighestActivity().contains(coursesListItem))
                .collect(Collectors.toList());
        List<Course> filteredHardestCourses = courseStatistics.getHardestCourses().stream()
                .filter((coursesListItem) -> !courseStatistics.getEasiestCourses().contains(coursesListItem))
                .collect(Collectors.toList());


        courseStatistics.setLeastPopularCourses(filteredLeastPopularCourses);
        courseStatistics.setCoursesWithLowestActivity(filteredCoursesWithLowestActivity);
        courseStatistics.setHardestCourses(filteredHardestCourses);

        return courseStatistics;
    }

    public CourseStatistics getCoursesStatsWithEmptyData() {
        return new CourseStatistics();
    }

    public void getCourseStatisticsForChosenCourse(Course course, CourseStatistics courseStatistics) {
        int currentCourseStudentsNumber = coursesService.getNumberOfStudentsAssignedToCourse(course.getId());
        int submissionsCount = pointsService.getPointsCountForCourse(course.getId());
        int avgPointsValue = coursesService.getCourseAvgPoints(course.getId());

        setMostPopularCourse(courseStatistics, course, currentCourseStudentsNumber);
        setLeastPopularCourse(courseStatistics, course, currentCourseStudentsNumber);
        setCourseWithHighestActivity(courseStatistics, course, submissionsCount);
        setCourseWithLowestActivity(courseStatistics, course, submissionsCount);
        setEasiestCourse(courseStatistics, course, avgPointsValue);
        setHardestCourse(courseStatistics, course, avgPointsValue);
    }

    void setMostPopularCourse(CourseStatistics courseStatistics, Course course, int currentCourseStudentsNumber) {
        List<Course> mostPopularCourses = courseStatistics.getMostPopularCourses();

        if (mostPopularCourses.isEmpty()) {
            courseStatistics.addMostPopularCourse(course);
            return;
        }

        int mostPopularCourseStudentsNumber = coursesService.getNumberOfStudentsAssignedToCourse(mostPopularCourses.get(0).getId());

        if (currentCourseStudentsNumber == mostPopularCourseStudentsNumber) {
            courseStatistics.addMostPopularCourse(course);
        } else if (currentCourseStudentsNumber > mostPopularCourseStudentsNumber) {
            List<Course> newList = new ArrayList<>();
            newList.add(course);

            courseStatistics.setMostPopularCourses(newList);
        }
    }

    void setLeastPopularCourse(CourseStatistics courseStatistics, Course course, int currentCourseStudentsNumber) {
        List<Course> leastPopularCourses = courseStatistics.getLeastPopularCourses();

        if (leastPopularCourses.isEmpty()) {
            courseStatistics.addLeastPopularCourse(course);
            return;
        }

        int leastPopularCoursesStudentsNumber = coursesService.getNumberOfStudentsAssignedToCourse(leastPopularCourses.get(0).getId());

        if (currentCourseStudentsNumber == leastPopularCoursesStudentsNumber) {
            courseStatistics.addLeastPopularCourse(course);
        } else if (currentCourseStudentsNumber < leastPopularCoursesStudentsNumber) {
            List<Course> newList = new ArrayList<>();
            newList.add(course);

            courseStatistics.setLeastPopularCourses(newList);
        }
    }

    public void setCourseWithHighestActivity(CourseStatistics courseStatistics, Course course, int currentCoursePointsValue) {
        List<Course> courseWithHighestActivity = courseStatistics.getCoursesWithHighestActivity();

        if (courseWithHighestActivity.isEmpty()) {
            courseStatistics.addCourseWithHighestActivity(course);
            return;
        }
        int courseWithHighestActivityPointsValue = pointsService.getPointsCountForCourse(courseWithHighestActivity.get(0).getId());

        if (currentCoursePointsValue == courseWithHighestActivityPointsValue) {
            courseStatistics.addCourseWithHighestActivity(course);
        } else if (currentCoursePointsValue > courseWithHighestActivityPointsValue) {
            List<Course> newList = new ArrayList<>();
            newList.add(course);

            courseStatistics.setCoursesWithHighestActivity(newList);
        }

    }

    public void setCourseWithLowestActivity(CourseStatistics courseStatistics, Course course, int currentCoursePointsValue) {
        List<Course> courseWithLowestActivity = courseStatistics.getCoursesWithLowestActivity();

        if (courseWithLowestActivity.isEmpty()) {
            courseStatistics.addCourseWithLowestActivity(course);
            return;
        }
        int courseWithLowestActivityPointsValue = pointsService.getPointsCountForCourse(courseWithLowestActivity.get(0).getId());

        if (currentCoursePointsValue == courseWithLowestActivityPointsValue) {
            courseStatistics.addCourseWithLowestActivity(course);
        } else if (currentCoursePointsValue < courseWithLowestActivityPointsValue) {
            List<Course> newList = new ArrayList<>();
            newList.add(course);

            courseStatistics.setCoursesWithLowestActivity(newList);
        }

    }

    public void setHardestCourse(CourseStatistics courseStatistics, Course course, int avgPoints) {
        List<Course> hardestCourse = courseStatistics.getHardestCourses();

        if (hardestCourse.isEmpty()) {
            courseStatistics.addHardestCourse(course);
            return;
        }
        int hardestCourseAvgPoints = coursesService.getCourseAvgPoints(hardestCourse.get(0).getId());

        if (avgPoints == hardestCourseAvgPoints) {
            courseStatistics.addHardestCourse(course);

        } else if (avgPoints < hardestCourseAvgPoints) {
            List<Course> newList = new ArrayList<>();
            newList.add(course);

            courseStatistics.setHardestCourses(newList);
        }

    }

    public void setEasiestCourse(CourseStatistics courseStatistics, Course course, int avgPoints) {
        List<Course> easiestCourse = courseStatistics.getEasiestCourses();

        if (easiestCourse.isEmpty()) {
            courseStatistics.addEasiestCourse(course);
            return;
        }
        int easiestCourseAvgPoints = coursesService.getCourseAvgPoints(easiestCourse.get(0).getId());

        if (avgPoints == easiestCourseAvgPoints) {
            courseStatistics.addEasiestCourse(course);

        } else if (avgPoints > easiestCourseAvgPoints) {
            List<Course> newList = new ArrayList<>();
            newList.add(course);

            courseStatistics.setEasiestCourses(newList);
        }

    }


    public List<BestLearnerStatistics> getBestLearnersForCourse(String courseName) throws CourseNotFound {
        Course course = coursesService.getCourseByName(courseName);
        List<Point> allCoursePoints = pointsService.getPointsForCourse(course.getId());
        List<BestLearnerStatistics> bestLearnerStatisticsList = new ArrayList<>();
        Map<String, List<Point>> studentWithPointsValue = allCoursePoints.stream()
                .collect(Collectors.groupingBy(Point::getStudentId, TreeMap::new, Collectors.toList()));

        studentWithPointsValue.forEach((studentId, pointsValue) -> {
            int pointsSum = pointsValue.stream().mapToInt(Point::getPointsNumber).sum();

            if (pointsSum == 0) {
                return;
            }

            double completionPercent = CourseUtils.getCompletedPercent(course, pointsSum);
            bestLearnerStatisticsList.add(new BestLearnerStatistics(studentId, pointsSum, completionPercent));

        });

        return this.sortByPointsValue(bestLearnerStatisticsList);
    }

    private List<BestLearnerStatistics> sortByPointsValue(List<BestLearnerStatistics> points) {
        Comparator<BestLearnerStatistics> comparator = new Comparator<BestLearnerStatistics>() {
            @Override
            public int compare(BestLearnerStatistics o1, BestLearnerStatistics o2) {
                return Integer.compare(o2.getPoints(), o1.getPoints());
            }
        };

        return points.stream().sorted(comparator).collect(Collectors.toList());
    }
}
