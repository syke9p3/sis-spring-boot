package dev.syke.sis.Student;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Builder
@Table(name="student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @Column(unique = true)
    @Email
    private String email;

    private LocalDate dob;

    @Transient
    private Integer age;


    public Student(String name, String lastName, @Email String email, LocalDate dob) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    
}
