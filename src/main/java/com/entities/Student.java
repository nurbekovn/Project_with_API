package com.entities;

import com.enums.Study;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String phoneNumber;
    private String email;
    private int age;

    @Enumerated(value = EnumType.STRING)
    private Study studyFormat;



    @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private Company company;


    @ManyToOne(cascade = {PERSIST,MERGE,REFRESH,DETACH})
    private Course course;


    @JsonIgnore
    @OneToOne(cascade = ALL)
    private User user ;


    public Student(String firstName, String lastName, String phoneNumber,User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.user = user;
    }



//    public Student(UserRegisterRequest userRegisterRequest) {
//        this.firstName = userRegisterRequest.getFirstName();
//        this.lastName= userRegisterRequest.getLastName();
//        this.age = userRegisterRequest.getAge();
//        this.phoneNumber = userRegisterRequest.getPhoneNumber();
//        this.studyFormat = userRegisterRequest.getStudyFormat();
//
//        User user1 = new User();
//        user1.setEmail(userRegisterRequest.getEmail());
//        user1.setPassword(userRegisterRequest.getPassword());
//        user1.setRole(Role.STUDENT);
//        this.user = user1;
//    }

}
