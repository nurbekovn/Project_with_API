package com.services;

import com.dto.requests.LessonRequest;
import com.dto.response.LessonResponse;
import com.entities.Course;
import com.entities.Lesson;
import com.exceptions.NotFoundException;
import com.repository.CourseRepository;
import com.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class LessonService {
    private final LessonRepository lessonRepo;
    private final CourseRepository courseRepo;

    public LessonResponse saveLesson(LessonRequest lessonRequest) {
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonRequest.getLessonName());
        Course course = courseRepo.findById(lessonRequest.getCourseId()).orElseThrow(
                () -> new NotFoundException(String.format("Course with =%s id not found", lessonRequest.getCourseId())));
        course.addLesson(lesson);
        lesson.setCourse(course);
        Lesson lesson1 = lessonRepo.save(lesson);
        return response(lesson1);
    }

    public LessonResponse getLessonById(Long id) {
        Lesson lesson = lessonRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Lesson with = %s id not found", id)));
        Lesson lesson1 = lessonRepo.save(lesson);
        return response(lesson1);
    }

    public LessonResponse updateTaskById(Long id, LessonRequest lessonRequest) {
        Lesson lesson = lessonRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Lesson with = %s id not found", id)));
        Lesson lesson1 = update(lesson, lessonRequest);
        return response(lesson1);
    }

    private Lesson update(Lesson lesson, LessonRequest lessonRequest) {
        lesson.setLessonName(lessonRequest.getLessonName());
        Course course = courseRepo.findById(lessonRequest.getCourseId()).orElseThrow(
                () -> new NotFoundException(String.format("Course with : %s id not found", lessonRequest.getCourseId())));
        lesson.setCourse(course);
        course.addLesson(lesson);
        return lessonRepo.save(lesson);

    }


    public LessonResponse deleteLessonById(Long id) {
        Lesson lesson = lessonRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Lesson with = %s id not found", id)));
        String courseName = lesson.getCourse().getCourseName();
        lesson.setCourse(null);
        lessonRepo.delete(lesson);
        return new LessonResponse(lesson.getId(), lesson.getLessonName(), courseName);
    }

    public List<LessonResponse> getAllLessons() {
        return lessonRepo.getAllLessons();
    }

    public LessonResponse response(Lesson lesson) {
        LessonResponse response = new LessonResponse();
        response.setId(lesson.getId());
        response.setLessonName(lesson.getLessonName());
        response.setCourseName(lesson.getCourse().getCourseName());
        return response;
    }
}