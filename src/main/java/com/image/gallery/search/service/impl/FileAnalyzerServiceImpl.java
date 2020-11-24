package com.image.gallery.search.service.impl;

import com.image.gallery.search.model.Image;
import com.image.gallery.search.service.FileAnalyzerService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;

@Service
public class FileAnalyzerServiceImpl implements FileAnalyzerService {

    //// TODO: 21.11.20  download by url. store it into local file system. Get meta
    private File downloadByURL(String url) throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileUtils.copyURLToFile(new URL(url), jfc.getSelectedFile());
        return jfc.getSelectedFile();
    }

    private HashMap<String, String> getMetaData(File file) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        HashMap<String, String> tag = new HashMap<>();
        tag.put("creation time",attr.creationTime().toString());
        tag.put("last access time", attr.lastAccessTime().toString());
        tag.put("last modifien time", attr.lastModifiedTime().toString());
        tag.put("size", String.valueOf(attr.size()));
        tag.put("symbolicLink", String.valueOf(attr.isSymbolicLink()));
        return tag;
    }

    @Override
    public List<Image> interceptImageListWithMeta(List<Image> imageList) throws IOException {
        for (Image image : imageList) {
            File f = downloadByURL(image.getUrl());
            image.setMeta(getMetaData(f));
        }
        return imageList;
    }
}
