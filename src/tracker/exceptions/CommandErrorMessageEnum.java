package tracker.exceptions;

public enum CommandErrorMessageEnum {
    NO_INPUT("No input"), UNKNOWN_COMMAND("Error: unknown command!");

    private final String message;

    CommandErrorMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
