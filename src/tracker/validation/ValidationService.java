package tracker.validation;

import tracker.students.StudentsRepository;
import tracker.students.exceptions.StudentNotFound;

public class ValidationService {
    private final StudentsRepository studentsRepository;

    public ValidationService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public void validateStudentExist(String studentId) throws StudentNotFound {
        studentsRepository.findStudentById(studentId);
    }
}
