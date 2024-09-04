package tracker.messages;

public enum StatisticsMessages {
    WELCOME_MESSAGE("Type the name of a course to see details or 'back' to quit:"),
    MOST_POPULAR("Most popular: "),
    LEAST_POPULAR("Least popular: "),
    HIGHEST_ACTIVITY("Highest activity: "),
    LOWEST_ACTIVITY("Lowest activity "),
    HARDEST_COURSE("Hardest course: "),
    EASIEST_COURSE("Easiest course: "),
    UNKNOWN_COURSE("Unknown course.");
    private final String message;

    StatisticsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
