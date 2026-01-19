package com.vinu.streamvideo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(
        name = "user_watch_history",
        indexes = {
                @Index(name = "idx_user_watch_user", columnList = "userId"),
                @Index(name = "idx_user_watch_video", columnList = "videoId")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWatchHistory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String userId;

        private String videoId;

        private Instant watchedAt;

        private Long watchDurationSeconds;
}
