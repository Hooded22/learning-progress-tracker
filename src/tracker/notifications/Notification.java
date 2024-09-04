package tracker.notifications;

public class Notification {
    private final String email;
    private final String subject;
    private final String message;

    public Notification(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public String getNotification() {
        return String.format("To: %s\nRe: %s\n%s", email, subject, message);
    }

    public String getEmail() {
        return email;
    }


}
