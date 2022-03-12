package com.cuberreality.repository.leads;

import com.cuberreality.entity.leads.InquiryLeadSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryLeadRepository extends MongoRepository<InquiryLeadSchema, String> {
    InquiryLeadSchema findByCrmLeadId(String id);

    Optional<InquiryLeadSchema> findById(String id);

    @Query(value= "{Reseller_ID : ?0}")
    List<InquiryLeadSchema> findByReseller_ID(String Reseller_ID);

}
