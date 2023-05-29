package com.bakalaurs.spring.repos;

import org.springframework.data.repository.CrudRepository;

import com.bakalaurs.spring.models.Identity;

public interface IIdentityRepo extends CrudRepository<Identity, Long> {

}
