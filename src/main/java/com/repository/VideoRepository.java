package com.repository;

import com.dto.response.VideoResponse;
import com.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("select v from Video v where v.lesson.id =:lessonId")
    List<Video> getVideoByLessonId(@Param("lessonId") Long lessonId);

    @Query("select new com.dto.response.VideoResponse(v.id," +
            "v.videoName,v.link)from Video v")
    List<VideoResponse> getAllVideos();
}
