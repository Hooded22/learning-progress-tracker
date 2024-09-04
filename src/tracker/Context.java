package tracker;

import tracker.constants.CoursesNames;
import tracker.coordinator.StudentsPointsService;
import tracker.courses.Course;
import tracker.courses.CourseManagementService;
import tracker.courses.CoursesRepository;
import tracker.courses.CoursesService;
import tracker.exceptions.CommandErrorMessageEnum;
import tracker.exceptions.InvalidCommandException;
import tracker.exceptions.ProgramExitException;
import tracker.messages.MainStateMessagesEnum;
import tracker.notifications.NotificationsRepository;
import tracker.notifications.NotificationsService;
import tracker.points.PointsRepository;
import tracker.points.PointsService;
import tracker.states.*;
import tracker.statistics.StatisticsService;
import tracker.students.Student;
import tracker.students.StudentsRepository;
import tracker.students.StudentsService;
import tracker.validation.ValidationService;

import java.util.List;

public class Context {
    StudentsRepository studentsRepository = new StudentsRepository();
    PointsRepository pointsRepository = new PointsRepository();
    CoursesRepository coursesRepository = new CoursesRepository();
    NotificationsRepository notificationsRepository = new NotificationsRepository();

    NotificationsService notificationsService = new NotificationsService(notificationsRepository);
    ValidationService validationService = new ValidationService(studentsRepository);
    PointsService pointsService = new PointsService(pointsRepository);
    CoursesService coursesService = new CoursesService(coursesRepository, pointsService);
    StatisticsService statisticsService = new StatisticsService(coursesService, pointsService);
    StudentsService studentsService = new StudentsService(studentsRepository);
    CourseManagementService courseManagementService = new CourseManagementService(coursesService, pointsService, validationService, studentsService, notificationsService);
    StudentsPointsService studentsPointsService = new StudentsPointsService(pointsService, studentsService, coursesService);
    private State currentState;

    public Context() {
        System.out.println(MainStateMessagesEnum.WELCOME_MESSAGE.getMessage());
        addDefaultCourses();
    }

    public State getState() {
        return this.currentState;
    }

    public void setState(Class<? extends State> stateClass) {
        if (stateClass.equals(AddStudentsState.class)) {
            this.currentState = new AddStudentsState(studentsService);
        } else if (stateClass.equals(AddPointsState.class)) {
            this.currentState = new AddPointsState(courseManagementService);
        } else if (stateClass.equals(MainState.class)) {
            this.currentState = new MainState(studentsService, notificationsService);
        } else if (stateClass.equals(FindStudentState.class)) {
            this.currentState = new FindStudentState(studentsPointsService);
        } else if (stateClass.equals(StatisticsState.class)) {
            this.currentState = new StatisticsState(statisticsService);
        } else {
            throw new IllegalStateException("Unknown state class: " + stateClass);
        }
    }

    public void handleCommand(String rawInput) {
        try {
            currentState.handleCommand(this, rawInput);
        } catch (InvalidCommandException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println(CommandErrorMessageEnum.UNKNOWN_COMMAND.getMessage());
        } catch (ProgramExitException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }

    }

    public List<Student> getStudentList() {
        return this.studentsService.getStudentList();
    }

    public void addStudent(Student student) {
        this.studentsService.addStudent(student);
    }

    private void addDefaultCourses() {
        coursesRepository.addCourse(new Course(CoursesNames.JAVA.getMessage(), 600));
        coursesRepository.addCourse(new Course(CoursesNames.SPRING.getMessage(), 550));
        coursesRepository.addCourse(new Course(CoursesNames.DATA_STRUCTURES.getMessage(), 400));
        coursesRepository.addCourse(new Course(CoursesNames.DATABASES.getMessage(), 480));
    }
}
