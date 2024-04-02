package com.otsc.backend.repositories;

import com.otsc.backend.entities.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> { }
