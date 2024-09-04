package tracker.states;

import tracker.Context;
import tracker.State;
import tracker.commands.AddStudentsCommand;
import tracker.exceptions.CommandErrorMessageEnum;
import tracker.exceptions.InvalidCommandException;
import tracker.messages.AddStudentsMessages;
import tracker.students.StudentsService;
import tracker.students.exceptions.InvalidStudentCredentials;

public class AddStudentsState implements State<AddStudentsCommand> {
    StudentsService studentsService;
    int numberOfAddedStudents = 0;

    public AddStudentsState(StudentsService studentsService) {
        this.studentsService = studentsService;
        System.out.println(AddStudentsMessages.WELCOME_MESSAGE.getMessage());
    }

    @Override
    public void handleCommand(Context context, String commandInput) {
        try {
            AddStudentsCommand parsedCommand = this.parseCommand(AddStudentsCommand.class, commandInput);
            switch (parsedCommand) {
                case BACK:
                    context.setState(MainState.class);
                    System.out.println("Total " + numberOfAddedStudents + " students have been added.");
                    break;
                default:
                    throw new InvalidCommandException(CommandErrorMessageEnum.UNKNOWN_COMMAND.getMessage());
            }
        } catch (IllegalArgumentException | InvalidCommandException e) {
            createStudentFromUserInput(commandInput);
        }
    }

    public void createStudentFromUserInput(String userInput) {
        try {
            studentsService.createStudentFromUserInput(userInput);
            numberOfAddedStudents++;

            System.out.println(AddStudentsMessages.STUDENT_ADDED_MESSAGE.getMessage());
        } catch (InvalidStudentCredentials ex) {
            System.out.println(ex.getMessage());
        }
    }
}
