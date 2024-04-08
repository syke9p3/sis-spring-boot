package dev.syke.sis.Student;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

// TODO: Add mock repository with @Mock annotation

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void StudentService_CreateStudent_ReturnsSavedStudent() {

        // Arrange
        Student student = Student.builder()
                .name("Name")
                .lastName("Last Name")
                .email("email@gmail.com")
                .dob(LocalDate.of(2001, 9, 16))
                .build();

        Student createdStudent = studentService.createStudent(student);

        Assertions.assertThat(createdStudent).isNotNull();
        Assertions.assertThat(createdStudent.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateStudent() {
        // Create a student
        Student student = createTestStudent("John", "Doe", "john@example.com", LocalDate.of(1990, 5, 15));
        Student createdStudent = studentService.createStudent(student);

        // Update student
        Student updatedStudent = new Student();
        updatedStudent.setName("UpdatedName");
        updatedStudent.setLastName("UpdatedLastName");
        updatedStudent.setEmail("updated@example.com");
        updatedStudent.setDob(LocalDate.of(1995, 10, 20));

        studentService.updateStudent(createdStudent.getId(), updatedStudent);

        // Retrieve the updated student
        Student retrievedStudent = studentService.getStudentById(createdStudent.getId());

        Assertions.assertThat(retrievedStudent).isNotNull();
        Assertions.assertThat(retrievedStudent.getName()).isEqualTo("UpdatedName");
        Assertions.assertThat(retrievedStudent.getLastName()).isEqualTo("UpdatedLastName");
        Assertions.assertThat(retrievedStudent.getEmail()).isEqualTo("updated@example.com");
        Assertions.assertThat(retrievedStudent.getDob()).isEqualTo(LocalDate.of(1995, 10, 20));
    
    }

    @Test
    public void testDeleteStudent() {
        // Create a student
        Student student = createTestStudent("Alice", "Smith", "alice@example.com", LocalDate.of(1992, 3, 25));
        Student createdStudent = studentService.createStudent(student);

        // Delete the student
        studentService.deleteStudent(createdStudent.getId());

        // Attempt to retrieve the deleted student
        Student retrievedStudent = studentService.getStudentById(createdStudent.getId());

        // Assert that the retrieved student is null, indicating deletion
        Assertions.assertThat(retrievedStudent).isNull();
    }

    private Student createTestStudent(String name, String lastName, String email, LocalDate dob) {
        return Student.builder()
                .name(name)
                .lastName(lastName)
                .email(email)
                .dob(dob)
                .build();
    }
}
