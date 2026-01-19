package com.vinu.streamvideo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(
        name = "videos",
        indexes = {
                @Index(name = "idx_video_category", columnList = "category"),
                @Index(name = "idx_video_language", columnList = "language")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    private String videoId;

    private String title;

    @Column(length = 2000)
    private String description;

    private String category;

    @ElementCollection
    @CollectionTable(
            name = "video_tags",
            joinColumns = @JoinColumn(name = "video_id")
    )
    @Column(name = "tag")
    private List<String> tags;

    private String videoPath;

    private String language;

    private String contentType;

    private Instant createdAt;
}
