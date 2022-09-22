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
        return response(video1);
    }

    public VideoResponse getVideoById(Long  id) {
        Video video = videoRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Video with =%s id not found", id)));
        return response(video);
    }

    public VideoResponse updateVideoById(Long id ,VideoRequest videoRequest) {
        Video video = videoRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Video with =%s id not found", id)));
        Video video1 = update(video, videoRequest);
        return response(video1);
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
        video.setLesson(null);
        videoRepo.delete(video);
        return response(video);
    }

    public List<VideoResponse> getAllVideos() {
        return videoRepo.getAllVideos();
    }

    public VideoResponse response(Video video) {
        VideoResponse response= new VideoResponse();
        response.setId(video.getId());
        response.setVideoName(video.getVideoName());
        response.setLink(video.getLink());
        return response;

    }
}
