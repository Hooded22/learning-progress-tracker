package tracker.commands;

public enum CommonCommands implements Command {
    BACK("back");

    private final String message;

    CommonCommands(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
