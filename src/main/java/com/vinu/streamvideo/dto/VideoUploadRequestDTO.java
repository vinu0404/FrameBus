package com.vinu.streamvideo.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoUploadRequestDTO {

    private String title;
    private String description;
    private String category;
    private List<String> tags;
    private String language;
}
