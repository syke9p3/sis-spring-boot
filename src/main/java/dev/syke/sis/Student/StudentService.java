package dev.syke.sis.Student;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Page<Student> getStudentsBySearch(String search, Pageable pageable) {
        return studentRepository.findBySearch(search, pageable);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Student with id = " + id + " does not exist"));

        // Update the existing student with the provided data
        if (updatedStudent.getName() != null && !updatedStudent.getName().isEmpty()) {
            existingStudent.setName(updatedStudent.getName());
        }
        if (updatedStudent.getLastName() != null && !updatedStudent.getLastName().isEmpty()) {
            existingStudent.setLastName(updatedStudent.getLastName());
        }
        if (updatedStudent.getEmail() != null && !updatedStudent.getEmail().isEmpty()) {
            existingStudent.setEmail(updatedStudent.getEmail());
        }
        if (updatedStudent.getDob() != null) {
            existingStudent.setDob(updatedStudent.getDob());
        }

        // Save the updated student
        studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("Student with id = " + id + " does not exists");
        }

        studentRepository.deleteById(id);
    }

}
