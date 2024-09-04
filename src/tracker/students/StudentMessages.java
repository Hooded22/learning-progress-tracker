package tracker.students;

public enum StudentMessages {
    INVALID_NAME("Incorrect first name"),
    INVALID_LAST_NAME("Incorrect last name"),
    INVALID_EMAIL("Incorrect email"),
    EMAIL_ALREADY_EXISTS("This email is already taken."),
    NOT_FOUND("No student is found for id=%s"),
    INVALID_CREDENTIALS("Incorrect credentials"),
    STUDENT_EMPTY_POINTS("%s points: Java=0; DSA=0; Databases=0; Spring=0"),
    STUDENT_POINTS("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d");
    private final String message;

    StudentMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
