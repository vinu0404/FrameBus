package com.vinu.streamvideo.controllers;

import com.vinu.streamvideo.dto.VideoResponseDTO;
import com.vinu.streamvideo.dto.VideoUploadRequestDTO;
import com.vinu.streamvideo.entity.Video;
import com.vinu.streamvideo.service.UserInteractionService;
import com.vinu.streamvideo.service.VideoService;
import com.vinu.streamvideo.service.impl.VideoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin("*")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }




    @PostMapping("/uploadVideo")
    public ResponseEntity<VideoResponseDTO> upload(
            @RequestPart("metadata") VideoUploadRequestDTO dto,
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(videoService.uploadVideo(dto, file));
    }




    @GetMapping("/watchSpecificVideo/{videoId}")
    public ResponseEntity<Resource> stream(
            @PathVariable String videoId,
            @RequestHeader(value = "Range", required = false) String range
    ) throws Exception {

        Video video = videoService.getVideoEntity(videoId);
        Path path = Paths.get(video.getVideoPath());

        long fileLength = Files.size(path);
        long start = 0;
        long end = fileLength - 1;

        if (range != null) {
            start = Long.parseLong(range.replace("bytes=", "").split("-")[0]);
            end = Math.min(start + 1024 * 1024 - 1, fileLength - 1);
        }

        InputStream inputStream = Files.newInputStream(path);
        inputStream.skip(start);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(video.getContentType()));
        headers.set("Accept-Ranges", "bytes");
        headers.setContentLength(end - start + 1);
        headers.set("Content-Range",
                "bytes " + start + "-" + end + "/" + fileLength);

        return new ResponseEntity<>(
                new InputStreamResource(inputStream),
                headers,
                HttpStatus.PARTIAL_CONTENT
        );
    }

    @GetMapping("/getAllVideos")
    public List<VideoResponseDTO> getAll(){
        return videoService.getAllVideos();
    }
}
