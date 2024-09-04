package tracker.states;

import tracker.Context;
import tracker.State;
import tracker.commands.CommonCommands;
import tracker.courses.exceptions.CourseNotFound;
import tracker.exceptions.InvalidCommandException;
import tracker.messages.StatisticsMessages;
import tracker.statistics.BestLearnerStatistics;
import tracker.statistics.CourseStatistics;
import tracker.statistics.StatisticsService;

import java.util.List;
import java.util.Objects;

public class StatisticsState implements State<CommonCommands> {
    StatisticsService statisticsService;

    public StatisticsState(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;

        System.out.println(StatisticsMessages.WELCOME_MESSAGE.getMessage());
        displayCoursesStatistics();
    }

    @Override
    public void handleCommand(Context context, String commandInput) {
        try {
            CommonCommands parsedCommand = this.parseCommand(CommonCommands.class, commandInput);
            if (Objects.requireNonNull(parsedCommand) == CommonCommands.BACK) {
                context.setState(MainState.class);
            } else {
                displayStatisticsForChosenCourse(commandInput);
            }
        } catch (InvalidCommandException e) {
            displayStatisticsForChosenCourse(commandInput);
        }
    }

    public void displayCoursesStatistics() {
        CourseStatistics courseStatistics = statisticsService.getCoursesStats();

        System.out.println(StatisticsMessages.MOST_POPULAR.getMessage() + courseStatistics.getMostPopularCourseNames());
        System.out.println(StatisticsMessages.LEAST_POPULAR.getMessage() + courseStatistics.getLeastPopularCourseNames());
        System.out.println(StatisticsMessages.HIGHEST_ACTIVITY.getMessage() + courseStatistics.getCourseWithHighestActivityNames());
        System.out.println(StatisticsMessages.LOWEST_ACTIVITY.getMessage() + courseStatistics.getCourseWithLowestActivityNames());
        System.out.println(StatisticsMessages.EASIEST_COURSE.getMessage() + courseStatistics.getEasiestCourseNames());
        System.out.println(StatisticsMessages.HARDEST_COURSE.getMessage() + courseStatistics.getHardestCoursesNames());
    }

    public void displayStatisticsForChosenCourse(String courseNme) {
        try {
            List<BestLearnerStatistics> bestLearnerStatistics = statisticsService.getBestLearnersForCourse(courseNme);
            System.out.println(courseNme);
            System.out.println("id\tpoints\tcompleted");


            for (BestLearnerStatistics bestLearnerStats : bestLearnerStatistics) {
                String completionPercentText = bestLearnerStats.getCompletionPercent() + "%";
                System.out.println(bestLearnerStats.getStudentID() + "\t" + bestLearnerStats.getPoints() + "\t" + completionPercentText);
            }
        } catch (CourseNotFound e) {
            System.out.println(StatisticsMessages.UNKNOWN_COURSE.getMessage());
        }
    }
}
