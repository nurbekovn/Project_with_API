package com.services;

import com.dto.requests.VideoRequest;
import com.dto.response.VideoResponse;
import com.entities.Lesson;
import com.entities.Video;
import com.exceptions.NotFoundException;
import com.repository.LessonRepository;
import com.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepo;
    private final LessonRepository lessonRepo;

    public VideoResponse saveVideo(VideoRequest videoRequest) {
        Video video = new Video();
        video.setVideoName(videoRequest.getVideoName());
        video.setLink(videoRequest.getLink());
        Lesson lesson = lessonRepo.findById(videoRequest.getLessonId()).orElseThrow(
                () -> new NotFoundException(String.format("Lesson with =%s id not found", videoRequest.getLessonId())));
        video.setLesson(lesson);
        lesson.addVideo(video);
        Video video1 = videoRepo.save(video);
        return mapToResponse(video1);
    }

    public VideoResponse getVideoById(Long id) {
        Video video = videoRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Video with =%s id not found", id)));
        return mapToResponse(video);
    }

    public VideoResponse updateVideoById(Long id, VideoRequest videoRequest) {
        Video video = videoRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Video with =%s id not found", id)));
        Video video1 = update(video, videoRequest);
        return mapToResponse(video1);
    }

    private Video update(Video video, VideoRequest videoRequest) {
        video.setVideoName(videoRequest.getVideoName());
        video.setLink(videoRequest.getLink());
        Lesson lesson = lessonRepo.findById(videoRequest.getLessonId()).orElseThrow(
                () -> new NotFoundException(String.format("Video with =%s id not found", videoRequest.getLessonId())));
        video.setLesson(lesson);
        lesson.addVideo(video);
        return videoRepo.save(video);
    }

    public VideoResponse deleteById(Long id) {
        Video video = videoRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Video with =%s id not found", id)));
        Long lessonId = video.getLesson().getId();
        String lessonName = video.getLesson().getLessonName();
        video.setLesson(null);
        videoRepo.delete(video);
        return new VideoResponse(video.getId(), video.getVideoName(), video.getLink(), lessonId,lessonName);
    }

    public List<VideoResponse> getAllVideos() {
        return videoRepo.getAllVideos();
    }

    public VideoResponse mapToResponse(Video video) {
        VideoResponse response = new VideoResponse();
        response.setId(video.getId());
        response.setVideoName(video.getVideoName());
        response.setLink(video.getLink());
        response.setLessonId(video.getLesson().getId());
        response.setLessonName(video.getLesson().getLessonName());
        return response;

    }
}
