package tracker.constants;

public enum CoursesNames {
    JAVA("Java"),
    SPRING("Spring"),
    DATA_STRUCTURES("DSA"),
    DATABASES("Databases");


    private final String message;

    CoursesNames(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
