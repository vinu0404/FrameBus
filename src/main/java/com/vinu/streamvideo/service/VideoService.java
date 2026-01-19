package com.vinu.streamvideo.service;

import com.vinu.streamvideo.dto.VideoResponseDTO;
import com.vinu.streamvideo.dto.VideoUploadRequestDTO;
import com.vinu.streamvideo.entity.Video;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface VideoService {

    VideoResponseDTO uploadVideo(VideoUploadRequestDTO dto, MultipartFile file);

    Video getVideoEntity(String videoId);

    List<VideoResponseDTO> getAllVideos();
}
