package com.cuberreality.repository;

import com.cuberreality.entity.UserProfilesSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfilesSchema, String> {

       Optional<UserProfilesSchema> findById(String id);

       Optional<UserProfilesSchema> findByUserUuid(String uuid);

}
