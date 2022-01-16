package com.cuberreality.service.impl;


import com.cuberreality.entity.leads.LeadsSchema;
import com.cuberreality.entity.leads.ReferLeadSchema;
import com.cuberreality.mapper.leads.LeadsMapper;
import com.cuberreality.repository.leads.LeadsRepository;
import com.cuberreality.repository.leads.ReferLeadRepository;
import com.cuberreality.request.leads.*;
import com.cuberreality.response.leads.*;
import com.cuberreality.service.LeadService;
import com.cuberreality.service.ReferLeadService;
import com.cuberreality.util.ApiClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReferLeadServiceImpl implements ReferLeadService {

    @Autowired
    LeadsMapper leadsMapper;
    @Autowired
    LeadsRepository leadsRepository;
    @Autowired
    ReferLeadRepository referLeadRepository;
    @Autowired
    MongoTemplate mongoTemplate;


    @Autowired
    private ApiClient apiClient;


    @Override
    public CreateLeadResponseModel referLead(ReferLeadModel createLeadRequest) throws Exception {

        ReferLeadCrmRequest crmReferLeadRequest = getReferLeadCrmRequest(createLeadRequest);


        CreateLeadResponse createLeadResponse = apiClient.post(crmReferLeadRequest, CreateLeadResponse.class,"/bigin/v1/Deals"  );

        log.info("lead created inn crm");
        ReferLeadResponse referLead = apiClient.get( ReferLeadResponse.class,"/bigin/v1/Deals/"+createLeadResponse.getData().get(0).getDetails().getId()  );


        ReferLeadSchema leadResponseModelToReferLeadsSchema = leadsMapper.getReferLeadResponseModelToReferLeadsSchema(referLead.getData().get(0));
        referLeadRepository.save(leadResponseModelToReferLeadsSchema);
        log.info("lead saved in db");

        return createLeadResponse.getData().get(0);
    }




    @Override
    public List<GetLeadResponseModel> getLeads() throws Exception {
        return null;
    }

    @Override
    public UpdateLeadResponse updateLead(UpdateLeadModel updateLeadRequest, String leadId) throws Exception {
        return null;
    }

    @Override
    public GetReferLeadResponseModel getReferLead(String id) {
        Optional<ReferLeadSchema> lead = referLeadRepository.findById(id);

        GetReferLeadResponseModel getLeadResponseModel = leadsMapper.getReferLeadsSchemaToLeadResponseModel(lead.get());

        return    getLeadResponseModel;    }

    @Override
    public UpdateLeadResponse updateReferLead(UpdateReferLeadModel updateReferLeadModel, String id) throws Exception {


        UpdateReferLeadRequest updateReferLeadRequest = new UpdateReferLeadRequest();

        List<UpdateReferLeadModel> updateLeadModelList = new ArrayList<>();
        updateLeadModelList.add(updateReferLeadModel);
        updateReferLeadRequest.setData(updateLeadModelList);

        UpdateLeadResponse updateLeadResponse = apiClient.put(updateReferLeadRequest, UpdateLeadResponse.class, "/bigin/v1/Deals/"+id);


        //  LeadsSchema lead = leadsRepository.findByCrmLeadId(updateLeadResponse.getData().get(0).getDetails().getId());


        //TODO discuss and set the modifiable fields


        update(updateReferLeadModel,id);
        return updateLeadResponse;
    }

    @Override
    public List<GetReferLeadResponseModel> getReferLeadByResellerId(String resellerId) {

        List<ReferLeadSchema> leads = referLeadRepository.findByReseller_ID(resellerId);

        //referLeadSchemasToLeads

        List<GetReferLeadResponseModel> referLeadResponseModelList = leadsMapper.referLeadSchemasToLeads(leads);

        return    referLeadResponseModelList;
    }

    public ReferLeadSchema update(UpdateReferLeadModel updateReferLeadModel,String leadId){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(leadId));
        Update update = new Update();
        update.set("Owner_Mobile", updateReferLeadModel.getOwner_Mobile());
        update.set("Owner_Name", updateReferLeadModel.getOwner_Name());
        update.set("Description", updateReferLeadModel.getDescription());
        update.set("Deal_Name", updateReferLeadModel.getDeal_Name());
        update.set("Reseller_Comments", updateReferLeadModel.getReseller_Comments());


        return mongoTemplate.findAndModify(query, update, ReferLeadSchema.class);
    }


    private ReferLeadCrmRequest getReferLeadCrmRequest(ReferLeadModel referLeadModel) {
        ReferLeadCrmRequest crmReferLeadRequest = new ReferLeadCrmRequest();
        List<ReferLeadModel> createLeadModelList = new ArrayList<>();
        createLeadModelList.add(referLeadModel);
        crmReferLeadRequest.setData(createLeadModelList);
        return crmReferLeadRequest;
    }
}
