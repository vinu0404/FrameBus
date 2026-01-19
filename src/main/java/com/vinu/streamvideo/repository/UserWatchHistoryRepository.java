package com.vinu.streamvideo.repository;

import com.vinu.streamvideo.entity.UserWatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWatchHistoryRepository
        extends JpaRepository<UserWatchHistory, Long> {
    List<UserWatchHistory> findByUserId(String userId);
    List<UserWatchHistory> findTop10ByUserIdOrderByWatchedAtDesc(String userId);
    boolean existsByUserIdAndVideoId(String userId, String videoId);
}
