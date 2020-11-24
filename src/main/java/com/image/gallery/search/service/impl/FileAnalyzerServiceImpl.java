package com.image.gallery.search.service.impl;

import com.image.gallery.search.model.Image;
import com.image.gallery.search.service.FileAnalyzerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class FileAnalyzerServiceImpl implements FileAnalyzerService {

    public static final String CREATION_TIME = "creation time";
    public static final String LAST_ACCESS_TIME = "last access time";
    public static final String LAST_MODIFIEN_TIME = "last modifien time";
    public static final String SIZE = "size";
    public static final String SYMBOLIC_LINK = "symbolicLink";

    @Override
    public List<Image> interceptImageListWithMeta(List<Image> imageList) {
        for (Image image : imageList) {
            try {
                final File file = downloadByURL(image.getUrl());
                image.setMeta(getMetaData(file));
            } catch (IOException e) {
                log.error("[FileAnalyzerServiceImpl] error analyzing image {} ", image.getId(), e);
            }
        }
        return imageList;
    }

    private File downloadByURL(String url) throws IOException {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        final File file = new File("images/" + fileName);
        FileUtils.copyURLToFile(new URL(url.replace(" ", "%20")), file);
        return file;
    }

    private HashMap<String, String> getMetaData(File file) throws IOException {
        final BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        final HashMap<String, String> tag = new HashMap<>();
        tag.put(CREATION_TIME, attr.creationTime().toString());
        tag.put(LAST_ACCESS_TIME, attr.lastAccessTime().toString());
        tag.put(LAST_MODIFIEN_TIME, attr.lastModifiedTime().toString());
        tag.put(SIZE, String.valueOf(attr.size()));
        tag.put(SYMBOLIC_LINK, String.valueOf(attr.isSymbolicLink()));
        return tag;
    }
}
