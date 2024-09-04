package tracker.students.exceptions;

import tracker.students.StudentMessages;

public class StudentNotFound extends Exception {
    public StudentNotFound(String studentId) {
        super(String.format(StudentMessages.NOT_FOUND.getMessage(), studentId));
    }
}
