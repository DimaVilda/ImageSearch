package com.image.gallery.search.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String id;
    @JsonProperty("cropped_picture")
    private String url;

    @JsonIgnore
    private Map<String, String> meta;
}
