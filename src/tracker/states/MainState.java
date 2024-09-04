package tracker.states;

import tracker.Context;
import tracker.State;
import tracker.commands.MainStateCommands;
import tracker.exceptions.CommandErrorMessageEnum;
import tracker.exceptions.InvalidCommandException;
import tracker.exceptions.ProgramExitException;
import tracker.messages.MainStateMessagesEnum;
import tracker.notifications.NotificationsService;
import tracker.students.Student;
import tracker.students.StudentsService;

import java.util.List;

public class MainState implements State<MainStateCommands> {
    StudentsService studentsService;
    NotificationsService notificationsService;

    public MainState(StudentsService studentsService, NotificationsService notificationsService) {
        this.studentsService = studentsService;
        this.notificationsService = notificationsService;
    }

    @Override
    public void handleCommand(Context context, String commandInput) throws InvalidCommandException, ProgramExitException {
        MainStateCommands command = parseCommand(MainStateCommands.class, commandInput);
        switch (command) {
            case EXIT:
                throw new ProgramExitException();
            case BACK:
                System.out.println(MainStateMessagesEnum.BACK_MESSAGE.getMessage());
                break;
            case ADD_STUDENTS:
                context.setState(AddStudentsState.class);
                break;
            case STUDENTS_LIST:
                printStudentsList(context.getStudentList());
                break;
            case ADD_POINTS:
                context.setState(AddPointsState.class);
                break;
            case FIND_STUDENT:
                context.setState(FindStudentState.class);
                break;
            case STATISTICS:
                context.setState(StatisticsState.class);
                break;
            case NOTIFY:
                sendAllNotifications();
                break;
            default:
                throw new InvalidCommandException(CommandErrorMessageEnum.UNKNOWN_COMMAND.getMessage());
        }

    }

    private void printStudentsList(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println(MainStateMessagesEnum.NO_STUDENTS.getMessage());
            return;
        }

        System.out.println(MainStateMessagesEnum.STUDENTS.getMessage());
        studentsService.displayStudentsTable(students);
    }

    private void sendAllNotifications() {
        notificationsService.sendAllNotifications();
    }
}
