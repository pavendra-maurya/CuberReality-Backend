package com.cuberreality.repository.leads;

import com.cuberreality.entity.leads.LeadsSchema;
import com.cuberreality.entity.leads.ReferLeadSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReferLeadRepository extends MongoRepository<ReferLeadSchema, String> {
    ReferLeadSchema findByCrmLeadId(String id);
    Optional<ReferLeadSchema> findById(String id);

    @Query(value= "{Reseller_ID : ?0}")
    List<ReferLeadSchema> findByReseller_ID(String Reseller_ID);

}
