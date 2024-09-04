package tracker.points;

public class PointNotFound extends RuntimeException {
    public PointNotFound() {
        super("Point not found");
    }
}
