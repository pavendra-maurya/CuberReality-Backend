package com.cuberreality.repository;


import com.cuberreality.entity.propertisesearch.SearchSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends MongoRepository<SearchSchema, String> {

}
