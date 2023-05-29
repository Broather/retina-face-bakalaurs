package com.bakalaurs.spring.repos;

import org.springframework.data.repository.CrudRepository;

import com.bakalaurs.spring.models.Face;

public interface IFaceRepo extends CrudRepository<Face, Long> {

}
