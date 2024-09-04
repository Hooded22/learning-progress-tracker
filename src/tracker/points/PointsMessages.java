package tracker.points;

public enum PointsMessages {
    INCORRECT_FORMAT("Incorrect points format"),
    POINTS_UPDATED("Points updated");
    private final String message;

    PointsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
