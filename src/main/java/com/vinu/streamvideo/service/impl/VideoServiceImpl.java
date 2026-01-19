package com.vinu.streamvideo.service.impl;

import com.vinu.streamvideo.dto.VideoResponseDTO;
import com.vinu.streamvideo.dto.VideoUploadRequestDTO;
import com.vinu.streamvideo.entity.Video;
import com.vinu.streamvideo.repository.VideoRepository;
import com.vinu.streamvideo.service.VideoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;


    @Value("${files.video}")
    private String DIR;


    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository=videoRepository;
    }

    @PostConstruct
    public void init(){
        File file = new File(DIR);
        if (!file.exists()){
            file.mkdir();
            System.out.println("folder created DIR");
        }else{
            System.out.println("folder already there");
        }
    }



    @Override
    public VideoResponseDTO uploadVideo(VideoUploadRequestDTO dto, MultipartFile file) {
        String videoId = UUID.randomUUID().toString();
        Path path = Paths.get(DIR, videoId + "_" + file.getOriginalFilename());

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Video upload failed");
        }

        Video video = Video.builder()
                .videoId(videoId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .tags(dto.getTags())
                .language(dto.getLanguage())
                .contentType(file.getContentType())
                .videoPath(path.toString())
                .createdAt(Instant.now())
                .build();

        videoRepository.save(video);
        return mapToDTO(video);
    }



    @Override
    public Video getVideoEntity(String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }




    @Override
    public List<VideoResponseDTO> getAllVideos() {
        return videoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }



    private VideoResponseDTO mapToDTO(Video video) {
        return VideoResponseDTO.builder()
                .videoId(video.getVideoId())
                .title(video.getTitle())
                .description(video.getDescription())
                .category(video.getCategory())
                .tags(video.getTags())
                .language(video.getLanguage())
                .contentType(video.getContentType())
                .build();
    }
}
