package com.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "video_gen")
    @SequenceGenerator(name = "video_gen", sequenceName = "video_seq", allocationSize = 1)
    private Long id;
    @Column(name = "video_name")
    private String videoName;
    @Column(length = 20000)
    private String link;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Lesson lesson;

    public Video(String videoName, String link) {
        this.videoName = videoName;
        this.link = link;
    }
}
