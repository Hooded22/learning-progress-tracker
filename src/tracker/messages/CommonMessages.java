package tracker.messages;

public enum CommonMessages {
    WELCOME_MESSAGE("Learning Progress Tracker"),
    ADD_POINTS("Enter an id and points or 'back' to return"),
    EXIT_MESSAGE("Bye!");
    private final String message;

    CommonMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
