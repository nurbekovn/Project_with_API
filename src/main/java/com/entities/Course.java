package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_gen")
    @SequenceGenerator(name = "course_gen", sequenceName = "course_seq", allocationSize = 1)
    private Long id;
    @Column(name = "course_name")
    private String courseName;
    private int duration;
    @Column(length = 20000)
    private String image;
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfStart;

    @ManyToOne(cascade = {MERGE, REFRESH, PERSIST, DETACH})
    private Company company;

    @ManyToMany(cascade = {MERGE, REFRESH, PERSIST, DETACH}, mappedBy = "courses")
    private List<Instructor> instructors;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "course")
    private List<Student> students;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Lesson> lessons;

    public void addInstructor(Instructor instructor) {
        if (instructors == null) {
            instructors = new ArrayList<>();
        } else {
            this.instructors.add(instructor);
        }
    }

    public void addStudent(Student student) {
        if (students==null) {
            students = new ArrayList<>();
        }
        else {
            this.students.add(student);
        }
    }

    public void addLesson(Lesson lesson) {
        if (lessons==null) {
            lessons=new ArrayList<>();
        }
        else {
            this.lessons.add(lesson);
        }
    }


    public Course(String courseName, int duration, String image, String description) {
        this.courseName = courseName;
        this.duration = duration;
        this.image = image;
        this.description = description;
    }

    //II - Бир курста бир канча инструкторлор, студенттер жана сабактар боло алат

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", duration=" + duration +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
