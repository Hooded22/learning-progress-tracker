package tracker.notifications;

public enum NotificationsMessages {
    GRADUATED_STUDENTS_SUBJECT("Your Learning Progress"),
    GRADUATED_STUDENTS_MESSAGE("Hello, %s! You have accomplished our %s course!"),
    TOTAL_NOTIFIED_STUDENTS("Total %d students have been notified.");

    private final String message;

    NotificationsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
