package com.cuberreality.repository;


import com.cuberreality.entity.SearchSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends MongoRepository<SearchSchema, String> {

}
