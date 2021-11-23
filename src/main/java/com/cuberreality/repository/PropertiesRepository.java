package com.cuberreality.repository;

import com.cuberreality.entity.PropertiesSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertiesRepository extends MongoRepository<PropertiesSchema, String> {

       Optional<PropertiesSchema> findById(String id);

}
