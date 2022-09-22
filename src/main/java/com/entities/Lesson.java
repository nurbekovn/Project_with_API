package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor

public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "lesson_seq")
    @SequenceGenerator(name = "lesson_seq",sequenceName = "lesson_seq",allocationSize = 1)
    private Long id;
    @Column(name = "lesson_name")
    private String lessonName;
//V - Сабака бир канча тапшырма жана бир видео жуктосо болот

    @OneToOne(cascade = ALL,mappedBy = "lesson")
    private Video video;

    @ManyToOne(cascade = {PERSIST,MERGE,DETACH,REFRESH})
    private Course course;

    @OneToMany(cascade = ALL,mappedBy = "lesson",fetch = FetchType.EAGER)
    private List<Task> tasks;

    public Lesson(String lessonName) {
        this.lessonName = lessonName;
    }

    public void addVideo(Video video) {
        System.out.println(video);
    }

    public void addTask(Task task) {
        if (tasks==null) {
            tasks=new ArrayList<>();
        } else {
            this.tasks.add(task);
        }
    }
}