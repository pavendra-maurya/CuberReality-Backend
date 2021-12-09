package com.cuberreality.repository;


import com.cuberreality.entity.occupations.OccupationsSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupationsRepository extends MongoRepository<OccupationsSchema, String> {

}
