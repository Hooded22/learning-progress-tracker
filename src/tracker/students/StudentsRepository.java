package tracker.students;

import tracker.students.exceptions.StudentNotFound;

import java.util.ArrayList;
import java.util.List;

public class StudentsRepository {
    private final List<Student> students;

    public StudentsRepository() {
        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student findStudentById(String studentID) throws StudentNotFound {
        return students.stream().filter(student -> student.getID().equals(studentID)).findFirst().orElseThrow(() -> new StudentNotFound(studentID));
    }
}
