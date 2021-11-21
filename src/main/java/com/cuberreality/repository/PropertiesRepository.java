package com.cuberreality.repository;

import com.cuberreality.entity.PropertiesSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface PropertiesRepository extends MongoRepository<PropertiesSchema, Long> {

       PropertiesSchema findById(String id);

}
