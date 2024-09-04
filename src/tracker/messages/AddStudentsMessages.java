package tracker.messages;

public enum AddStudentsMessages {
    WELCOME_MESSAGE("Enter student credentials or 'back' to return"),
    STUDENT_ADDED_MESSAGE("The student has been added.");
    private final String message;

    AddStudentsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
