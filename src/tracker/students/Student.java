package tracker.students;

import tracker.students.exceptions.InvalidStudentCredentials;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Student {
    private String ID;
    private String firstName;
    private String lastName;
    private String email;

    public Student(String firstName, String lastName, String email) {
        this.ID = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static void validateStudentName(String firstName) throws InvalidStudentCredentials {
        String namePattern = "^[A-Za-z][a-zA-Z]*(?:[-'][a-zA-Z]+)*(?: [A-Z][a-zA-Z]*(?:[-'][a-zA-Z]+)*)*$";

        if (!firstName.matches(namePattern) || firstName.length() <= 1) {
            throw new InvalidStudentCredentials(StudentMessages.INVALID_NAME.getMessage());
        }
    }

    public static void validateStudentLastName(String lastName) throws InvalidStudentCredentials {
        String lastNamePattern = "^[A-Za-z][a-zA-Z]*(?:[-'][a-zA-Z]+)*(?: [a-zA-Z]+)*(?: [a-zA-Z]+(?:[-'][a-zA-Z]+)*)*$";


        if (!lastName.matches(lastNamePattern) || lastName.length() <= 1) {
            throw new InvalidStudentCredentials(StudentMessages.INVALID_LAST_NAME.getMessage());
        }
    }

    public static void validateStudentEmail(String email, List<Student> students) throws InvalidStudentCredentials {
        String emailPattern = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z\\d.-]+$";


        if (!email.matches(emailPattern)) {
            throw new InvalidStudentCredentials(StudentMessages.INVALID_EMAIL.getMessage());
        }

        if (students.stream().anyMatch(student -> Objects.equals(student.getEmail(), email))) {
            throw new InvalidStudentCredentials(StudentMessages.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
