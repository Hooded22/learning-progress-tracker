package tracker.states;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import tracker.Context;
import tracker.coordinator.StudentsPointsService;
import tracker.messages.FindStudentMessages;
import tracker.students.StudentMessages;
import tracker.students.exceptions.StudentNotFound;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class FindStudentStateTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    StudentsPointsService studentsPointsService;
    FindStudentState findStudentState;
    Context context;


    @BeforeEach
    void setUp() {
        context = Mockito.mock(Context.class);
        studentsPointsService = Mockito.mock(StudentsPointsService.class);
        findStudentState = new FindStudentState(studentsPointsService);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testFindStudent_shouldDisplayWelcomeMessage() {
        new FindStudentState(studentsPointsService);

        Assertions.assertEquals(outContent.toString().trim(), FindStudentMessages.WELCOME_MESSAGE.getMessage());
    }

    @Test
    void handleCommand_backCommand_shouldSetStateToMain() {
        findStudentState.handleCommand(context, "back");

        Mockito.verify(context).setState(ArgumentMatchers.eq(MainState.class));
    }

    @Test
    void handleCommand_correctStudentId_shouldCallStudentsServiceDisplayStudentDetails() throws StudentNotFound {
        String studentId = "student-id";

        findStudentState.handleCommand(context, studentId);

        Mockito.verify(studentsPointsService).displayStudentPoints(ArgumentMatchers.eq(studentId));
    }

    @Test
    void handleCommand_incorrectStudentId_shouldDisplayErrorMessage() throws StudentNotFound {
        String studentId = "student-id";
        Mockito.doThrow(new StudentNotFound(studentId)).when(studentsPointsService).displayStudentPoints(ArgumentMatchers.eq(studentId));
        String expectedErrorMessage = String.format(StudentMessages.NOT_FOUND.getMessage(), studentId);

        findStudentState.handleCommand(context, studentId);


        Assertions.assertEquals(outContent.toString().trim(), expectedErrorMessage);
    }
}