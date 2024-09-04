package tracker.exceptions;

import tracker.messages.CommonMessages;

public class ProgramExitException extends Exception {
    public ProgramExitException() {
        super(CommonMessages.EXIT_MESSAGE.getMessage());
    }
}
