package test.tracker.students;

import org.junit.Test;
import tracker.students.Student;
import tracker.students.exceptions.InvalidStudentCredentials;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentTest {

    @Test
    public void constructor_validInputs_shouldCreateStudent() {
        Student student = new Student("John", "Doe", "john.doe@example.com");

        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("john.doe@example.com", student.getEmail());
    }

    @Test
    public void validateStudentName_invalidName_shouldThrowException() {
        String invalidName = "'invalidName";
        assertThrows(InvalidStudentCredentials.class, () -> Student.validateStudentName(invalidName));
    }

    @Test
    public void validateStudentLastName_invalidLastName_shouldThrowException() {
        String invalidLastName = "'invalidLastName";
        assertThrows(InvalidStudentCredentials.class, () -> Student.validateStudentLastName(invalidLastName));
    }

    @Test
    public void validateStudentEmail_invalidEmail_shouldThrowException() {
        String invalidEmail = "invalidEmail";
        List<Student> students = new ArrayList<>();
        assertThrows(InvalidStudentCredentials.class, () -> Student.validateStudentEmail(invalidEmail, students));
    }

    @Test
    public void validateStudentEmail_takenEmail_shouldThrowException() {
        String takenEmail = "john.doe@example.com";
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", "Doe", takenEmail));
        assertThrows(InvalidStudentCredentials.class, () -> Student.validateStudentEmail(takenEmail, students));
    }
}