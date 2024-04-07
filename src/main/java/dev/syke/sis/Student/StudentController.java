package dev.syke.sis.Student;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    @Autowired
    public StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping
    public Page<Student> getAllStudents(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        if (searchTerm != null && !searchTerm.isEmpty()) {
            return studentService.getStudentsBySearch(searchTerm, PageRequest.of(page, size));
        } else {
            return studentService.getAllStudents(PageRequest.of(page, size));
        }
    }

    @GetMapping("/{id}")
    public Student getLearnerById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }


    @PutMapping("/{id}")
    public void updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return true;
    }
}
