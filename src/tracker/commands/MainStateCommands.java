package tracker.commands;

public enum MainStateCommands implements Command {
    ADD_STUDENTS("add students"),
    STUDENTS_LIST("list"),
    BACK("back"),
    EXIT("exit"),
    ADD_POINTS("add points"),
    FIND_STUDENT("find"),
    STATISTICS("statistics"),
    NOTIFY("notify");

    private final String message;

    MainStateCommands(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
