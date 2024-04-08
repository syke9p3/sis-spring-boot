package dev.syke.sis.Student;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:search% OR s.lastName LIKE %:search%")
    Page<Student> findBySearch(String search, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    Page<Student> findAll(Pageable pageable);
}
