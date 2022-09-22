package com.api;

import com.dto.requests.VideoRequest;
import com.dto.response.VideoResponse;
import com.entities.Video;
import com.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
@PreAuthorize("hasAuthority('ADMIN')")
public class VideoController {
    private final VideoService videoService;


    @PostMapping("/saveVideo")
    public VideoResponse saveVideo(@RequestBody VideoRequest videoRequest) {
        return videoService.saveVideo(videoRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public VideoResponse getVideoById(@PathVariable Long id) {
        return videoService.getVideoById(id);
    }
    @PutMapping("/{id}")
    public VideoResponse updateVideoById(@PathVariable Long id,
                                         @RequestBody VideoRequest videoRequest) {
        return videoService.updateVideoById(id, videoRequest);
    }

    @DeleteMapping("/{id}")
    public VideoResponse deleteVideoById(@PathVariable Long id) {
        return videoService.deleteById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public List<VideoResponse> getAllVideos() {
        return videoService.getAllVideos();
    }
}
