package com.image.gallery.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private List<Image> pictures;
    private Integer pageCount;
    private Boolean hasMore;
}
