package tracker.points.exceptions;

import tracker.points.PointsMessages;

public class IncorrectPointFormat extends RuntimeException {
    public IncorrectPointFormat() {
        super(PointsMessages.INCORRECT_FORMAT.getMessage());
    }
}
