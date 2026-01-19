package com.vinu.streamvideo.repository;
import com.vinu.streamvideo.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VideoRepository extends JpaRepository<Video, String> {
        List<Video> findByCategory(String category);
        List<Video> findByLanguage(String language);
        List<Video> findByCategoryAndLanguage(String category, String language);
}

