package com.repository;

import com.dto.response.LessonResponse;
import com.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long > {
    List<Lesson> getLessonByCourseId(Long courseId);

    @Query("select new com.dto.response.LessonResponse(l.id , l.lessonName ,l.course.courseName)from Lesson l")
    List<LessonResponse> getAllLessons();

}
