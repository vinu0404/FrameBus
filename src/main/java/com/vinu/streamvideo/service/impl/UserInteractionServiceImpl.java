package com.vinu.streamvideo.service.impl;

import com.vinu.streamvideo.dto.RecommendationResponseDTO;
import com.vinu.streamvideo.dto.WatchEventRequestDTO;
import com.vinu.streamvideo.entity.UserWatchHistory;
import com.vinu.streamvideo.repository.UserWatchHistoryRepository;
import com.vinu.streamvideo.service.UserInteractionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.util.List;
import java.util.Map;


@Service
public class UserInteractionServiceImpl implements UserInteractionService {


    private final UserWatchHistoryRepository watchHistoryRepository;
    private final RestTemplate restTemplate;

    @Value("${ml.service.url}")
    private String mlServiceUrl;

    public UserInteractionServiceImpl(UserWatchHistoryRepository watchHistoryRepository
            ,RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
        this.watchHistoryRepository=watchHistoryRepository;
    }

    @Override
    public void saveWatchEvent(WatchEventRequestDTO dto) {
        UserWatchHistory history = UserWatchHistory.builder()
                .userId(dto.getUserId())
                .videoId(dto.getVideoId())
                .watchDurationSeconds(dto.getWatchDurationSeconds())
                .watchedAt(Instant.now())
                .build();

        watchHistoryRepository.save(history);
    }

    @Override
    public List<RecommendationResponseDTO> recommendVideos(String userId) {
        List<String> watchedVideoIds =
                watchHistoryRepository.findByUserId(userId)
                        .stream()
                        .map(UserWatchHistory::getVideoId)
                        .distinct()
                        .toList();

        Map<String, Object> payload = Map.of(
                "userId", userId,
                "watchedVideoIds", watchedVideoIds
        );

        ResponseEntity<RecommendationResponseDTO[]> response =
                restTemplate.postForEntity(
                        mlServiceUrl + "/ml/recommend",
                        payload,
                        RecommendationResponseDTO[].class
                );
        RecommendationResponseDTO[] body = response.getBody();

        if (body == null || body.length == 0) {
            return List.of();
        }

        return List.of(body);


    }
}
