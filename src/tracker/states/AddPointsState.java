package tracker.states;

import tracker.Context;
import tracker.State;
import tracker.commands.CommonCommands;
import tracker.courses.CourseManagementService;
import tracker.exceptions.InvalidCommandException;
import tracker.messages.CommonMessages;
import tracker.points.exceptions.IncorrectPointFormat;
import tracker.students.exceptions.StudentNotFound;

import java.util.Objects;

public class AddPointsState implements State<CommonCommands> {
    CourseManagementService courseManagementService;

    public AddPointsState(CourseManagementService courseManagementService) {
        this.courseManagementService = courseManagementService;

        System.out.println(CommonMessages.ADD_POINTS.getMessage());
    }

    @Override
    public void handleCommand(Context context, String commandInput) {
        try {
            CommonCommands parsedCommand = this.parseCommand(CommonCommands.class, commandInput);
            if (Objects.requireNonNull(parsedCommand) == CommonCommands.BACK) {
                context.setState(MainState.class);
            } else {
                addPointFromUserInput(commandInput);
            }
        } catch (InvalidCommandException e) {
            addPointFromUserInput(commandInput);
        }
    }

    private void addPointFromUserInput(String commandInput) {
        try {
            courseManagementService.addPointFromUserInput(commandInput);

        } catch (StudentNotFound | IncorrectPointFormat e) {
            System.out.println(e.getMessage());
        }
    }
}
