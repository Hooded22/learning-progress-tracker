package tracker.students;

import tracker.students.exceptions.InvalidStudentCredentials;
import tracker.students.exceptions.StudentNotFound;

import java.util.List;

public class StudentsService {
    StudentsRepository studentsRepository;

    public StudentsService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public void displayStudentsTable(List<Student> students) {
        for (Student student : students) {
            System.out.println(student.getID());
        }
    }

    public List<Student> getStudentList() {
        return this.studentsRepository.getStudents();
    }

    public void addStudent(Student student) {
        this.studentsRepository.addStudent(student);
    }

    public void createStudentFromUserInput(String userInput) throws InvalidStudentCredentials {
        String[] credentials = userInput.split("\\s+(?=[A-Za-z-'])", 2); // Split name and the rest
        validateCredentials(credentials);

        String firstName = credentials[0];
        String lastAndEmailPart = credentials[1];
        int emailIndex = lastAndEmailPart.lastIndexOf(" ");
        String lastName = lastAndEmailPart.substring(0, emailIndex);
        String email = lastAndEmailPart.substring(emailIndex + 1);

        validateStudentDetails(new StudentValidatorDataDTO(firstName, lastName, email, getStudentList()));

        Student newStudent = new Student(firstName, lastName, email);
        addStudent(newStudent);
    }

    public Student getStudentByID(String studentID) throws StudentNotFound {
        return studentsRepository.findStudentById(studentID);
    }

    private void validateCredentials(String[] credentials) throws InvalidStudentCredentials {
        if (credentials.length != 2 || !credentials[1].contains(" ")) {
            throw new InvalidStudentCredentials(StudentMessages.INVALID_CREDENTIALS.getMessage());
        }
    }

    private void validateStudentDetails(StudentValidatorDataDTO dataDTO) throws InvalidStudentCredentials {
        Student.validateStudentName(dataDTO.firstName());
        Student.validateStudentLastName(dataDTO.lastName());
        Student.validateStudentEmail(dataDTO.email(), dataDTO.students());
    }
}
