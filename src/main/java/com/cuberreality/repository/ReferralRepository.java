package com.cuberreality.repository;

import com.cuberreality.entity.propertisesearch.SearchSchema;
import com.cuberreality.entity.referral.Referral;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralRepository extends MongoRepository<Referral, String> {

}


