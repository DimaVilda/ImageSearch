package com.image.gallery.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "Image")
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "url")
    private String url;
    @Column(name = "meta_teg")
    private String metaTeg;

}
