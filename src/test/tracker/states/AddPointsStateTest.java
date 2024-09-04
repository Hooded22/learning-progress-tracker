package test.tracker.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import tracker.Context;
import tracker.courses.CourseManagementService;
import tracker.states.AddPointsState;
import tracker.states.MainState;
import tracker.students.exceptions.StudentNotFound;

class AddPointsStateTest {
    CourseManagementService courseManagementService;
    AddPointsState addPointsState;
    Context context;

    @BeforeEach
    void setUp() {
        context = Mockito.mock(Context.class);
        courseManagementService = Mockito.mock(CourseManagementService.class);
        addPointsState = new AddPointsState(courseManagementService);
    }

    @Test
    void handleCommand_backCommand_shouldSetStateToMain() {
        addPointsState.handleCommand(context, "back");

        Mockito.verify(context).setState(ArgumentMatchers.eq(MainState.class));
    }

    @Test
    void handleCommand_otherInput_shouldCallPointsServiceAddPointFromUserInput() throws StudentNotFound {
        String input = "student-id 12 12 12 12";
        addPointsState.handleCommand(context, input);

        Mockito.verify(courseManagementService).addPointFromUserInput(ArgumentMatchers.eq(input));
    }

}