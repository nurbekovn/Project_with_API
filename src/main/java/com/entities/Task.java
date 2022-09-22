package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "task_gen")
    @SequenceGenerator(name = "task_gen",sequenceName = "task_seq",allocationSize = 1)
    private Long id;
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_text",length = 20000)
    private String taskText;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    private LocalDate deadline;


    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH})
    private Lesson lesson;

    public Task(String taskName, String taskText, LocalDate deadline, Lesson lesson) {
        this.taskName = taskName;
        this.taskText = taskText;
        this.deadline = deadline;
        this.lesson = lesson;
    }


}
