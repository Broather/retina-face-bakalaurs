package com.bakalaurs.spring.repos;

import org.springframework.data.repository.CrudRepository;

import com.bakalaurs.spring.models.Image;

public interface IImageRepo extends CrudRepository<Image, Long> {
    Image findImageByName(String name);

    boolean existsImageByName(String name);
}
