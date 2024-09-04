package tracker.messages;

public enum FindStudentMessages {
    WELCOME_MESSAGE("Enter an id or 'back' to return.");
    private final String message;

    FindStudentMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
