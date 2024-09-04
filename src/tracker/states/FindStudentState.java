package tracker.states;

import tracker.Context;
import tracker.State;
import tracker.commands.CommonCommands;
import tracker.coordinator.StudentsPointsService;
import tracker.exceptions.InvalidCommandException;
import tracker.messages.FindStudentMessages;
import tracker.students.exceptions.StudentNotFound;

import java.util.Objects;

public class FindStudentState implements State<CommonCommands> {
    StudentsPointsService studentsPointsService;

    public FindStudentState(StudentsPointsService studentsPointsService) {
        this.studentsPointsService = studentsPointsService;
        System.out.println(FindStudentMessages.WELCOME_MESSAGE.getMessage());
    }

    @Override
    public void handleCommand(Context context, String commandInput) {
        try {
            CommonCommands command = this.parseCommand(CommonCommands.class, commandInput);
            if (Objects.requireNonNull(command) == CommonCommands.BACK) {
                this.backToMainState(context);
            } else {
                displayStudentDetails(commandInput);
            }
        } catch (InvalidCommandException e) {
            displayStudentDetails(commandInput);
        }
    }

    private void displayStudentDetails(String studentId) {
        try {
            studentsPointsService.displayStudentPoints(studentId);
        } catch (StudentNotFound ex) {
            System.out.println(ex.getMessage());
        }
    }

}
