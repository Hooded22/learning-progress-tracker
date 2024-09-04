package tracker.states;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tracker.Context;
import tracker.messages.AddStudentsMessages;
import tracker.students.StudentMessages;
import tracker.students.StudentsService;
import tracker.students.exceptions.InvalidStudentCredentials;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AddStudentsStateTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private AddStudentsState addStudentsState;
    private Context context;
    private StudentsService studentsService;


    @BeforeEach
    void setUp() {
        studentsService = mock(StudentsService.class);
        context = mock(Context.class);
        addStudentsState = new AddStudentsState(studentsService);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void handleCommand_backCommand_shouldSetContextStateToMain() {
        addStudentsState.handleCommand(context, "BACK");

        verify(context).setState(MainState.class);
    }

    @Test
    void handleCommand_unknownCommand_shouldCallCreateStudentFromUserInput() {
        AddStudentsState addStudentsStateSpy = spy(addStudentsState);
        String unknownCommand = "unknownCommand";

        addStudentsStateSpy.handleCommand(context, unknownCommand);

        verify(addStudentsStateSpy).createStudentFromUserInput(eq(unknownCommand));
    }

    @Test
    void createStudentFromUserInput_validCredentials_shouldCallStudentsServiceCreateStudentFromUserInput() throws InvalidStudentCredentials {
        String credentials = "Name Lastname email@email.com";
        addStudentsState.createStudentFromUserInput(credentials);

        verify(studentsService).createStudentFromUserInput(eq(credentials));
    }

    @Test
    void createStudentFromUserInput_validCredentials_shouldDisplaySuccessMessage() throws InvalidStudentCredentials {
        String credentials = "Name Lastname email@email.com";
        addStudentsState.createStudentFromUserInput(credentials);

        String expectedOutput = AddStudentsMessages.STUDENT_ADDED_MESSAGE.getMessage();
        Assertions.assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void createStudentFromUserInput_invalidCredentials_shouldPrintErrorMessage() throws InvalidStudentCredentials {
        String credentials = "invalidCredentials";
        Mockito.doThrow(new InvalidStudentCredentials(StudentMessages.INVALID_CREDENTIALS.getMessage())).when(studentsService).createStudentFromUserInput(eq(credentials));

        addStudentsState.createStudentFromUserInput(credentials);

        Assertions.assertEquals(StudentMessages.INVALID_CREDENTIALS.getMessage(), outContent.toString().trim());
    }

    @Test
    void handleCommand_validCredentialsAndBackMethod_shouldDisplayAddedStudentsNumber() {
        String credentials = "Name Lastname email@email.com";
        String backCommand = "BACK";

        addStudentsState.handleCommand(context, credentials);
        addStudentsState.handleCommand(context, backCommand);

        String expectedOutput = "The student has been added.\nTotal 1 students have been added.";
        Assertions.assertEquals(expectedOutput, outContent.toString().trim());
    }


}