package com.bakalaurs.spring.repos;

import org.springframework.data.repository.CrudRepository;

import com.bakalaurs.spring.models.Picture;

public interface IPictureRepo extends CrudRepository<Picture, Long> {

}
