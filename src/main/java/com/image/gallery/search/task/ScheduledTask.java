package com.image.gallery.search.task;

import com.image.gallery.search.model.Image;
import com.image.gallery.search.model.Page;
import com.image.gallery.search.service.FileAnalyzerService;
import com.image.gallery.search.service.ImageService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ScheduledTask {

    private final ImageService imageService;
    private final FileAnalyzerService fileAnalyzerService;
    private final RestTemplate restTemplate;
    private final String url;

    public ScheduledTask(ImageService imageService, FileAnalyzerService fileAnalyzerService, RestTemplate restTemplate, String url) {
        this.imageService = imageService;
        this.fileAnalyzerService = fileAnalyzerService;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Scheduled(fixedRateString = "${sync.time}")
    public void scheduledMethod() {

        //// TODO: 21.11.20  multiple page impl
        final ResponseEntity<Page> response =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        Page.class);
        final Page responseBody = response.getBody();
        for (int i = 0; i <= responseBody.getPageCount(); i++) {
            if (response.getStatusCode().is2xxSuccessful() && responseBody != null) {
                final List<Image> images = fileAnalyzerService.interceptImageListWithMeta(responseBody.getPictures());
                imageService.saveAll(images);
            }
        }
    }
}
