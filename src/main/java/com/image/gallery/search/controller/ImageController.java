package com.image.gallery.search.controller;

import com.image.gallery.search.model.Image;
import com.image.gallery.search.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@Slf4j
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{searchTerm}")
    private ResponseEntity<List<Image>> search(@PathVariable String searchTerm) {
        log.info("[ImageController] : search method triggered");
        return ResponseEntity.ok(imageService.searchImage(searchTerm));
    }
}
