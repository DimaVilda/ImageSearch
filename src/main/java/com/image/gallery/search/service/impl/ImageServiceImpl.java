package com.image.gallery.search.service.impl;

import com.image.gallery.search.entity.ImageEntity;
import com.image.gallery.search.model.Image;
import com.image.gallery.search.repository.ImageRepository;
import com.image.gallery.search.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = Objects.requireNonNull(imageRepository);
    }

    @Override
    public List<Image> searchImage(String searchTerm) {
        log.info("[ImageServiceImpl] : searchImage triggered");
        return imageRepository.getAllImagesMatchingWithMatchingTag(searchTerm).stream()
                .filter(Objects::nonNull)
                .map(this::mapImage)
                .collect(Collectors.toList());
    }

    @Override
    public boolean saveAll(List<Image> list) {
        log.info("[ImageServiceImpl] : saveAll triggered");
        final List<ImageEntity> entityList = list.stream()
                .filter(Objects::nonNull)
                .map(this::mapImageEntity)
                .collect(Collectors.toList());
        final List<ImageEntity> savedImgList = imageRepository.saveAll(entityList);
        return savedImgList.size() != 0;
    }

    private Image mapImage(ImageEntity imageEntity) {
        log.info("[ImageServiceImpl] : mapImage triggered");
        final Image image = new Image();
        image.setId(imageEntity.getId());
        image.setUrl(imageEntity.getUrl());
        return image;
    }

    private ImageEntity mapImageEntity(Image image) {
        log.info("[ImageServiceImpl] : mapImageEntity triggered");
        final ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(image.getId());
        imageEntity.setUrl(image.getUrl());
        if (image.getMeta() != null) {
            imageEntity.setMetaTeg(image.getMeta().keySet().stream()
                    .map(key -> image.getMeta().get(key))
                    .collect(Collectors.joining(";")));
        }
        return imageEntity;
    }
}
