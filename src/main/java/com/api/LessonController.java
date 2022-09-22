package com.api;

import com.dto.requests.LessonRequest;
import com.dto.response.LessonResponse;
import com.entities.Lesson;
import com.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequiredArgsConstructor

public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    public LessonResponse saveLesson(@RequestBody LessonRequest lessonRequest) {
        return lessonService.saveLesson(lessonRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public LessonResponse getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @PutMapping("/{id}")
    public LessonResponse updateLessonById(@PathVariable Long id,
                                           @RequestBody LessonRequest lessonRequest) {
        return lessonService.updateTaskById(id,lessonRequest);
    }


    @DeleteMapping("/{id}")
    public LessonResponse deleteLessonById(@PathVariable Long id) {
        return lessonService.deleteLessonById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    List<LessonResponse> getAllLessons() {
        return lessonService.getAllLessons();
    }
}
