package com.vinu.streamvideo.service;

import com.vinu.streamvideo.dto.RecommendationResponseDTO;
import com.vinu.streamvideo.dto.WatchEventRequestDTO;

import java.util.List;

public interface UserInteractionService {

    void saveWatchEvent(WatchEventRequestDTO dto);

    List<RecommendationResponseDTO> recommendVideos(String userId);
}
