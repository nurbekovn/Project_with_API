package com.entities;

import com.enums.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_name")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private int phoneNumber;
    private String email;

    @Enumerated(value = EnumType.ORDINAL)
//    @Column(name = "study_format")
    private Study studyFormat;



    @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private Company company;


    @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private Course course;

    public Student(String firstName, String lastName, int phoneNumber, String email, Study studyFormat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.studyFormat = studyFormat;
    }

}
