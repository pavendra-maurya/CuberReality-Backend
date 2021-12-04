package com.cuberreality.repository.leads;


import com.cuberreality.entity.leads.LeadsSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadsRepository extends MongoRepository<LeadsSchema, String> {

    LeadsSchema findByLeadId(String id);

//    List<LeadsSchema> findByreseller_ID(String id);
   // List<LeadsSchema> findByReseller_ID(String Reseller_ID);

    @Query(value= "{Reseller_ID : ?0}")
    List<LeadsSchema> findByReseller_ID(String Reseller_ID);

    @Query(value= "{Buyer_Mobile_Number : ?0}")
    List<LeadsSchema> findByBuyer_Mobile_Number(String Buyer_Mobile_Number);





}
