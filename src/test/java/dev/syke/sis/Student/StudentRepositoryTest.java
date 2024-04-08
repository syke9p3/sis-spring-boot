package dev.syke.sis.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StudentRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryTest.class);

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void StudentRepository_SaveAll_ReturnSavedStudent() {

        // Arrage
        Student student = Student.builder()
                .name("Name")
                .lastName("Last Name")
                .email("email@gmail.com")
                .dob(LocalDate.of(2001, 9, 16))
                .build();

        // Act
        Student savedStudent = studentRepository.save(student);

        // Assert
        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getId()).isGreaterThan(0);

    }

    @Test
    public void StudentRepository_GetAll_ReturnMoreThanOneStudent() {

        // Arrage
        Student student1 = Student.builder()
                .name("Name")
                .lastName("Last Name")
                .email("email@gmail.com")
                .dob(LocalDate.of(2001, 9, 16))
                .build();

        Student student2 = Student.builder()
                .name("Andres")
                .lastName("Bonifacio")
                .email("abonifacio@gmail.com")
                .dob(LocalDate.of(1999, 1, 25))
                .build();

        // Act
        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Student> studentList = studentRepository.findAll();

        // Assert
        Assertions.assertThat(studentList).isNotNull();
        Assertions.assertThat(studentList.size()).isEqualTo(2);

    }

    @Test
    public void StudentRepository_FindById_ReturnsOneStudent() {
        // Arrange
        Student student = Student.builder()
                .name("Maria")
                .lastName("Clara")
                .email("mclara@gmail.com")
                .dob(LocalDate.of(1999, 1, 25))
                .build();

        // Act
        Student savedStudent = studentRepository.save(student);

        logger.info("Student ID: {}", savedStudent.getId());

        // Retrieve the student by ID
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());

        // Assert
        Assertions.assertThat(optionalStudent.isPresent()).isTrue(); // Check if the optional contains a value
        optionalStudent.ifPresent(retrievedStudent -> { // Use ifPresent to safely access the retrieved student
            Assertions.assertThat(retrievedStudent.getName()).isEqualTo(student.getName());
            Assertions.assertThat(retrievedStudent.getLastName()).isEqualTo(student.getLastName());
            Assertions.assertThat(retrievedStudent.getEmail()).isEqualTo(student.getEmail());
            Assertions.assertThat(retrievedStudent.getDob()).isEqualTo(student.getDob());
        });
    }

    @Test
    public void StudentRepository_FindStudentByEmail_ReturnsOneStudent() {

        Student student = Student.builder()
                .name("Andres")
                .lastName("Bonifacio")
                .email("abonifacio@gmail.com")
                .dob(LocalDate.of(1999, 1, 25))
                .build();

        // Act
        studentRepository.save(student);

        Student studentList = studentRepository.findStudentByEmail("abonifacio@gmail.com").get();

        // Assert
        Assertions.assertThat(studentList).isNotNull();

    }

    @Test
    public void StudentRepository_FindBySearch_ReturnsOneStudent() {

        Student student = Student.builder()
                .name("Andres")
                .lastName("Bonifacio")
                .email("abonifacio@gmail.com")
                .dob(LocalDate.of(1999, 1, 25))
                .build();

        Student student2 = Student.builder()
                .name("Emilio")
                .lastName("Aguinaldo")
                .email("eaguinaldo@gmail.com")
                .dob(LocalDate.of(2002, 2, 22))
                .build();

        // Act
        studentRepository.save(student);
        studentRepository.save(student2);

        Page<Student> studentPage = studentRepository.findBySearch("andres", PageRequest.of(0, 10));

        // Assert
        Assertions.assertThat(studentPage.hasContent());
        Assertions.assertThat(studentPage.getContent().stream().anyMatch(s -> s.getName().equalsIgnoreCase("Andres")));

    }

    @Test
    public void StudentRepository_FindBySearch_ShouldNotReturnWhenSearchDontMatch() {

        Student student = Student.builder()
                .name("Andres")
                .lastName("Bonifacio")
                .email("abonifacio@gmail.com")
                .dob(LocalDate.of(1999, 1, 25))
                .build();

        Student student2 = Student.builder()
                .name("Emilio")
                .lastName("Aguinaldo")
                .email("eaguinaldo@gmail.com")
                .dob(LocalDate.of(2002, 2, 22))
                .build();

        // Act
        studentRepository.save(student);
        studentRepository.save(student2);

        Page<Student> studentPage = studentRepository.findBySearch("jose", PageRequest.of(0, 10));

        // Assert
        Assertions.assertThat(studentPage).isNotNull();
        Assertions.assertThat(studentPage).isEmpty();

    }

}
