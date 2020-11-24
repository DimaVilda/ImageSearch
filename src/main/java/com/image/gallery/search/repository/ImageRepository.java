package com.image.gallery.search.repository;

import com.image.gallery.search.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, String> {

    @Query("select i from Image i where i.metaTeg LIKE CONCAT('%',:tag,'%')")
    List<ImageEntity> getAllImagesMatchingWithMatchingTag(@Param("tag") String tag);
}
