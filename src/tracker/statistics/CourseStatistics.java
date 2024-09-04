package tracker.statistics;

import tracker.courses.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseStatistics {
    private List<Course> coursesWithHighestActivity = new ArrayList<Course>();
    private List<Course> coursesWithLowestActivity = new ArrayList<Course>();
    private List<Course> easiestCourses = new ArrayList<Course>();
    private List<Course> hardestCourses = new ArrayList<Course>();
    private List<Course> mostPopularCourses = new ArrayList<Course>();
    private List<Course> leastPopularCourses = new ArrayList<Course>();

    public CourseStatistics() {
    }

    public List<Course> getMostPopularCourses() {
        return mostPopularCourses;
    }

    public void setMostPopularCourses(List<Course> mostPopularCourses) {
        this.mostPopularCourses = mostPopularCourses;
    }

    public void addMostPopularCourse(Course course) {
        mostPopularCourses.add(course);
    }

    public String getMostPopularCourseNames() {
        if (mostPopularCourses.isEmpty()) {
            return "n/a";
        }

        if (mostPopularCourses.size() == 1) {
            return mostPopularCourses.get(0).getName();
        } else {
            return mostPopularCourses.stream()
                    .map(Course::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    public List<Course> getLeastPopularCourses() {
        return leastPopularCourses;
    }

    public void setLeastPopularCourses(List<Course> leastPopularCourses) {
        this.leastPopularCourses = leastPopularCourses;
    }

    public void addLeastPopularCourse(Course course) {
        leastPopularCourses.add(course);
    }

    public String getLeastPopularCourseNames() {
        if (leastPopularCourses.isEmpty()) {
            return "n/a";
        }

        if (leastPopularCourses.size() == 1) {
            return leastPopularCourses.get(0).getName();
        } else {
            return leastPopularCourses.stream()
                    .map(Course::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    public List<Course> getCoursesWithHighestActivity() {
        return coursesWithHighestActivity;
    }

    public void setCoursesWithHighestActivity(List<Course> coursesWithHighestActivity) {
        this.coursesWithHighestActivity = coursesWithHighestActivity;
    }

    public void addCourseWithHighestActivity(Course course) {
        coursesWithHighestActivity.add(course);
    }

    public String getCourseWithHighestActivityNames() {
        if (coursesWithHighestActivity.isEmpty()) {
            return "n/a";
        }

        if (coursesWithHighestActivity.size() == 1) {
            return coursesWithHighestActivity.get(0).getName();
        } else {
            return coursesWithHighestActivity.stream()
                    .map(Course::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    public List<Course> getCoursesWithLowestActivity() {
        return coursesWithLowestActivity;
    }

    public void setCoursesWithLowestActivity(List<Course> coursesWithLowestActivity) {
        this.coursesWithLowestActivity = coursesWithLowestActivity;
    }

    public void addCourseWithLowestActivity(Course course) {
        coursesWithLowestActivity.add(course);
    }

    public String getCourseWithLowestActivityNames() {
        if (coursesWithLowestActivity.isEmpty()) {
            return "n/a";
        }

        if (coursesWithLowestActivity.size() == 1) {
            return coursesWithLowestActivity.get(0).getName();
        } else {
            return coursesWithLowestActivity.stream()
                    .map(Course::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    public List<Course> getEasiestCourses() {
        return easiestCourses;
    }

    public void setEasiestCourses(List<Course> easiestCourses) {
        this.easiestCourses = easiestCourses;
    }

    public void addEasiestCourse(Course course) {
        easiestCourses.add(course);
    }

    public String getEasiestCourseNames() {
        if (easiestCourses.isEmpty()) {
            return "n/a";
        }

        if (easiestCourses.size() == 1) {
            return easiestCourses.get(0).getName();
        } else {
            return easiestCourses.stream()
                    .map(Course::getName)
                    .collect(Collectors.joining(", "));
        }
    }

    public List<Course> getHardestCourses() {
        return hardestCourses;
    }

    public void setHardestCourses(List<Course> hardestCourses) {
        this.hardestCourses = hardestCourses;
    }

    public void addHardestCourse(Course course) {
        hardestCourses.add(course);
    }

    public String getHardestCoursesNames() {
        if (hardestCourses.isEmpty()) {
            return "n/a";
        }

        if (hardestCourses.size() == 1) {
            return hardestCourses.get(0).getName();
        } else {
            return hardestCourses.stream().map(Course::getName).collect(Collectors.joining(", "));
        }
    }
}
