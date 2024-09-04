package test.tracker.students;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import tracker.students.Student;
import tracker.students.StudentsRepository;
import tracker.students.StudentsService;
import tracker.students.exceptions.InvalidStudentCredentials;
import tracker.students.exceptions.StudentNotFound;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

class StudentsServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    StudentsRepository studentsRepository;
    StudentsService studentsService;


    @BeforeEach
    void setUp() {
        studentsRepository = Mockito.mock(StudentsRepository.class);
        studentsService = new StudentsService(studentsRepository);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void displayStudentsTable_shouldDisplayStudentsIds() {
        Student student1 = new Student("John", "Doe", "john.doe@example.com");
        Student student2 = new Student("Jane", "Doe", "jane.doe@example.com");
        List<Student> students = Arrays.asList(student1, student2);

        studentsService.displayStudentsTable(students);

        String output = outContent.toString();
        Assertions.assertTrue(output.contains(student1.getID()));
        Assertions.assertTrue(output.contains(student2.getID()));
    }

    @Test
    void getStudentsList_shouldCallStudentsRepositoryGetStudents() {
        studentsService.getStudentList();

        Mockito.verify(studentsRepository, Mockito.times(1)).getStudents();
    }


    @Test
    void getStudentList_shouldCallStudentsRepository() {
        studentsService.getStudentList();

        Mockito.verify(studentsRepository).getStudents();
    }

    @Test
    void addStudent_shouldCallStudentsRepositoryAddStudent() {
        Student student = Mockito.mock(Student.class);
        studentsService.addStudent(student);

        Mockito.verify(studentsRepository, Mockito.times(1)).addStudent(student);
    }

    @Test
    void createStudentFromUserInput_incorrectCredentials_shouldThrowException() {
        String incorrectCredentials = "incorrectCredentials";

        Assertions.assertThrows(InvalidStudentCredentials.class, () -> studentsService.createStudentFromUserInput(incorrectCredentials));
    }

    @Test
    void createStudentFromUserInput_incorrectFirstName_shouldThrowException() {
        String incorrectCredentials = "'name surname email@email.com";

        Assertions.assertThrows(InvalidStudentCredentials.class, () -> studentsService.createStudentFromUserInput(incorrectCredentials));
    }

    @Test
    void createStudentFromUserInput_incorrectLastName_shouldThrowException() {
        String incorrectCredentials = "name 'surname email@email.com";

        Assertions.assertThrows(InvalidStudentCredentials.class, () -> studentsService.createStudentFromUserInput(incorrectCredentials));
    }

    @Test
    void createStudentFromUserInput_incorrectEmail_shouldThrowException() {
        String incorrectCredentials = "name surname email";

        Assertions.assertThrows(InvalidStudentCredentials.class, () -> studentsService.createStudentFromUserInput(incorrectCredentials));
    }

    @Test
    void getStudentByID_existingStudent() throws StudentNotFound {
        Student student = new Student("John", "Doe", "john.doe@example.com");
        String studentID = student.getID();
        Mockito.when(studentsRepository.findStudentById(ArgumentMatchers.eq(studentID))).thenReturn(student);

        Student foundStudent = studentsService.getStudentByID(studentID);

        Assertions.assertEquals(student, foundStudent);
    }

    @Test
    void getStudentByID_studentNotFound() throws StudentNotFound {
        Mockito.doThrow(new StudentNotFound("nonexistent-id")).when(studentsRepository).findStudentById(ArgumentMatchers.anyString());

        Assertions.assertThrows(StudentNotFound.class, () -> studentsService.getStudentByID("nonexistent-id"));
    }
}