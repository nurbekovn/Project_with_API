package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor

public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "instructor_seq")
    @SequenceGenerator(name = "instructor_seq",sequenceName = "instructor_seq",allocationSize = 1)
    private Long id;
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String specialization;

    @ManyToMany(cascade = {MERGE, PERSIST, DETACH, REFRESH})
    @JoinTable
    private List<Course> courses;

    @ManyToOne(cascade = {MERGE, PERSIST, DETACH, REFRESH})
    private Company company;


    public void addCourse(Course course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        courses.add(course);
    }


    public Instructor(String firstName, String lastName, String phoneNumber, String email, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.specialization = specialization;
    }

}
