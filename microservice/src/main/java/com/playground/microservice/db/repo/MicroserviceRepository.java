package com.playground.microservice.db.repo;

import com.playground.microservice.db.model.Microservice;
import org.springframework.data.repository.CrudRepository;

public interface MicroserviceRepository extends CrudRepository<Microservice, Integer> {
}
