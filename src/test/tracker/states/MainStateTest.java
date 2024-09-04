package tracker.states;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tracker.Context;
import tracker.exceptions.InvalidCommandException;
import tracker.exceptions.ProgramExitException;
import tracker.messages.MainStateMessagesEnum;
import tracker.notifications.NotificationsService;
import tracker.students.Student;
import tracker.students.StudentsService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class MainStateTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private MainState mainState;
    private Context context;
    private StudentsService studentsService;
    private NotificationsService notificationsService;

    @BeforeEach
    void setUp() {
        context = Mockito.mock(Context.class);
        studentsService = Mockito.mock(StudentsService.class);
        notificationsService = Mockito.mock(NotificationsService.class);
        mainState = new MainState(studentsService, notificationsService);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void handleCommand_exitCommand_shouldThrowProgramExitException() {
        Assertions.assertThrows(ProgramExitException.class, () -> mainState.handleCommand(context, "exit"));
    }

    @Test
    void handleCommand_backCommand_shouldPrintMessage() throws InvalidCommandException, ProgramExitException {
        String expectedMessage = MainStateMessagesEnum.BACK_MESSAGE.getMessage() + "\n";

        mainState.handleCommand(context, "back");

        Assertions.assertEquals(expectedMessage, outContent.toString());
    }

    @Test
    void handleCommand_addStudentsCommand_shouldSetStateToAddStudents() throws InvalidCommandException, ProgramExitException {
        mainState.handleCommand(context, "add students");

        Mockito.verify(context).setState(AddStudentsState.class);

    }

    @Test
    void handleCommand_findStudentCommand_shouldSetStateToFindStudent() throws InvalidCommandException, ProgramExitException {
        mainState.handleCommand(context, "find");

        Mockito.verify(context).setState(FindStudentState.class);

    }

    @Test
    void handleCommand_shouldPrintStudentsList_andCallDisplayStudentsTable_whenStudentsListCommand() throws InvalidCommandException, ProgramExitException {
        List<Student> mockStudents = new ArrayList<>();
        Student mockStudent = Mockito.mock(Student.class);
        mockStudents.add(mockStudent);

        Mockito.when(context.getStudentList()).thenReturn(mockStudents);

        mainState.handleCommand(context, "list");

        Mockito.verify(studentsService).displayStudentsTable(mockStudents);
        Assertions.assertTrue(outContent.toString().contains(MainStateMessagesEnum.STUDENTS.getMessage()));
    }

    @Test
    void handleCommand_notifyCommand_shouldCallNotificationsService() throws InvalidCommandException, ProgramExitException {
        mainState.handleCommand(context, "notify");

        Mockito.verify(notificationsService).sendAllNotifications();
    }

    @Test
    void handleCommand_invalidCommand_shouldThrowInvalidCommandException() {
        Assertions.assertThrows(InvalidCommandException.class, () -> mainState.handleCommand(context, "invalid command"));
    }
}