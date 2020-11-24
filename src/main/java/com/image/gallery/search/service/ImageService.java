package com.image.gallery.search.service;

import com.image.gallery.search.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> searchImage(String searchTerm);

    boolean saveAll(List<Image> list);
}
