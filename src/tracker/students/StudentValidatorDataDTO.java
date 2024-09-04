package tracker.students;

import java.util.List;

public record StudentValidatorDataDTO(String firstName, String lastName, String email, List<Student> students) {
}
