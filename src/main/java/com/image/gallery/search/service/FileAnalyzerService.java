package com.image.gallery.search.service;

import com.image.gallery.search.model.Image;

import java.io.IOException;
import java.util.List;

public interface FileAnalyzerService {

    List<Image> interceptImageListWithMeta(List<Image> imageList, int page);
}
