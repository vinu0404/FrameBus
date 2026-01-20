package com.vinu.streamvideo.controllers;

import com.vinu.streamvideo.dto.RecommendationResponseDTO;
import com.vinu.streamvideo.dto.WatchEventRequestDTO;
import com.vinu.streamvideo.service.UserInteractionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WatchEventController {

    private final UserInteractionService interactionService;

    public WatchEventController(UserInteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping("/watchEvent")
    public ResponseEntity<Void> recordWatchEvent(
            @RequestBody WatchEventRequestDTO dto
    ) {

        interactionService.saveWatchEvent(dto);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/recommend/{userId}")
    public ResponseEntity<List<RecommendationResponseDTO>> recommend(
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(
                interactionService.recommendVideos(userId)
        );
    }
}
