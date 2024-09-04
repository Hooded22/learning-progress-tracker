package tracker;

import tracker.commands.Command;
import tracker.exceptions.CommandErrorMessageEnum;
import tracker.exceptions.InvalidCommandException;
import tracker.exceptions.ProgramExitException;
import tracker.states.MainState;

public interface State<C extends Enum<C> & Command> {
    void handleCommand(Context context, String commandInput) throws InvalidCommandException, ProgramExitException;

    default void validateCommand(String command) throws InvalidCommandException {
        if (command.trim().isEmpty()) {
            throw new InvalidCommandException(CommandErrorMessageEnum.NO_INPUT.getMessage());
        }
    }

    default C parseCommand(Class<C> commandEnum, String command) throws InvalidCommandException {
        try {
            validateCommand(command);
            return Command.fromString(commandEnum, command);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException(CommandErrorMessageEnum.UNKNOWN_COMMAND.getMessage());
        }
    }

    default void backToMainState(Context context) {
        context.setState(MainState.class);
    }
}
