package tracker.statistics;

public class BestLearnerStatistics {
    private final String studentID;
    private final int points;
    private final double completionPercent;

    public BestLearnerStatistics(String studentID, int points, double completed) {
        this.studentID = studentID;
        this.points = points;
        this.completionPercent = completed;
    }

    public String getStudentID() {
        return studentID;
    }

    public int getPoints() {
        return points;
    }

    public double getCompletionPercent() {
        return completionPercent;
    }
}
