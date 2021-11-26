package com.cuberreality.repository;

import com.cuberreality.entity.user.VisitedProperties;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitedPropertiesRepository extends MongoRepository<VisitedProperties, String> {

       Optional<VisitedProperties> findByPropertyIdAndResellerId(String property_id,String reseller_id);

}
