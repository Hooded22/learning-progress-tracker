package tracker.messages;

public enum MainStateMessagesEnum {
    WELCOME_MESSAGE("Learning Progress Tracker"),
    BACK_MESSAGE("Enter 'exit' to exit the program"),
    EXIT_MESSAGE("Bye!"),
    STUDENTS("Students: "),
    NO_STUDENTS("No students found"),
    ADD_POINTS("Enter an id and points or 'back' to return");
    private final String message;

    MainStateMessagesEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
