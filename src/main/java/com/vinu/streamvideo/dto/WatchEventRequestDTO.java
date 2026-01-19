package com.vinu.streamvideo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchEventRequestDTO {

    private String videoId;
    private Long watchDurationSeconds;
}
