package tracker.commands;

public enum AddStudentsCommand implements Command {
    BACK("back");

    private final String message;

    AddStudentsCommand(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
