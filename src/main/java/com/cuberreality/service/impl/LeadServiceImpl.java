package com.cuberreality.service.impl;

import com.cuberreality.entity.leads.LeadsSchema;
import com.cuberreality.mapper.leads.LeadsMapper;
import com.cuberreality.repository.leads.LeadsRepository;
import com.cuberreality.request.leads.*;
import com.cuberreality.response.leads.*;
import com.cuberreality.service.LeadService;
import com.cuberreality.util.ApiClient;

import com.mongodb.DBObject;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Description;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LeadServiceImpl implements LeadService {


    @Autowired
    LeadsMapper leadsMapper;
    @Autowired
    LeadsRepository leadsRepository;
    @Autowired
    MongoTemplate mongoTemplate;


    @Autowired
    private ApiClient apiClient;


    @Override
    public CreateLeadResponseModel createLead(CreateLeadModel createLeadRequest) throws Exception {

        CreateLeadRequest crmCreateLeadRequest = getCrmLeadRequest(createLeadRequest);


        CreateLeadResponse  createLeadResponse = apiClient.post(crmCreateLeadRequest, CreateLeadResponse.class,"/bigin/v1/Deals"  );

        log.info("lead created inn crm");
        GetLeadResponse  lead = apiClient.get( GetLeadResponse.class,"/bigin/v1/Deals/"+createLeadResponse.getData().get(0).getDetails().getId()  );

        LinkProductLeadRequest linkProductLeadRequest = createLinkProductLeadRequest(createLeadRequest);

        String url="/bigin/v1/Deals/"+lead.getData().get(0).getId()+"/Products";

        LinkLeadResponse linkLeadResponse = apiClient.put(linkProductLeadRequest, LinkLeadResponse.class, url );
        log.info("lead linked with product in crm");

        LeadsSchema leadResponseModelToLeadsSchema = leadsMapper.getLeadResponseModelToLeadsSchema(lead.getData().get(0));
        leadsRepository.save(leadResponseModelToLeadsSchema);
        log.info("lead saved in db");

        return createLeadResponse.getData().get(0);
    }

    private CreateLeadRequest getCrmLeadRequest(CreateLeadModel createLeadRequest) {
        CreateLeadRequest crmCreateLeadRequest = new CreateLeadRequest();
        List<CreateLeadModel> createLeadModelList = new ArrayList<>();
        createLeadModelList.add(createLeadRequest);
        crmCreateLeadRequest.setData(createLeadModelList);
        return crmCreateLeadRequest;
    }

    private LinkProductLeadRequest createLinkProductLeadRequest(CreateLeadModel createLeadModel) {
        LinkProductLeadRequest linkProductLeadRequest = new LinkProductLeadRequest();
        LeadProductPojo leadProductPojo = new LeadProductPojo();
        leadProductPojo.setId(createLeadModel.getProperty_ID());

        List<LeadProductPojo> productPojos = new ArrayList<>();
        productPojos.add(leadProductPojo);
        linkProductLeadRequest.setData(productPojos);
        return linkProductLeadRequest;
    }








    @Override
    public GetLeadResponseModel getLead(String leadId) throws Exception {
        LeadsSchema lead = leadsRepository.findByLeadId(leadId);

        GetLeadResponseModel getLeadResponseModel = leadsMapper.leadsSchemaToGetLeadResponseModel(lead);

        return    getLeadResponseModel;

    }





    @Override
    public List<GetLeadResponseModel> getLeads()  {

        List<LeadsSchema> leadsSchemas = leadsRepository.findAll();

        List<GetLeadResponseModel> getLeadResponseModels = leadsMapper.leadSchemasToLeads(leadsSchemas);

        return getLeadResponseModels;



    }


    public int findLeadsCountByReseller(String resellerId)  {

        List<LeadsSchema> leadsSchemas = leadsRepository.findByReseller_ID(resellerId);

   return leadsSchemas.size();


    }

    public List<GetLeadResponseModel> findLeadsByReseller(String resellerId)  {

        List<LeadsSchema> leadsSchemas = leadsRepository.findByReseller_ID(resellerId);
        List<GetLeadResponseModel> getLeadResponseModels = leadsMapper.leadSchemasToLeads(leadsSchemas);

        return getLeadResponseModels;


    }

    public List<GetLeadResponseModel> searchLeads(SearchLeadRequest searchLeadRequest) throws Exception {

        List<LeadsSchema> leadsSchemas = leadsRepository.findByBuyer_Mobile_Number(searchLeadRequest.getMobNo());

        List<GetLeadResponseModel> getLeadResponseModels = leadsMapper.leadSchemasToLeads(leadsSchemas);

return getLeadResponseModels;



    }



    @Override
    public UpdateLeadResponse updateLead(UpdateLeadModel updateLeadRequest, String leadId) throws Exception {


        UpdateLeadRequest crmUpdateLeadRequest = new UpdateLeadRequest();

        List<UpdateLeadModel> updateLeadModelList = new ArrayList<>();
        updateLeadModelList.add(updateLeadRequest);
        crmUpdateLeadRequest.setData(updateLeadModelList);

        UpdateLeadResponse updateLeadResponse = apiClient.put(crmUpdateLeadRequest, UpdateLeadResponse.class, "/bigin/v1/Deals/"+leadId);


        LeadsSchema lead = leadsRepository.findByLeadId(updateLeadResponse.getData().get(0).getDetails().getId());


        //TODO discuss and set the modifiable fields


        update(updateLeadRequest,leadId);
        return updateLeadResponse;
    }


    public LeadsSchema update(UpdateLeadModel updateLeadRequest,String leadId){
        Query query = new Query();
        query.addCriteria(Criteria.where("leadId").is(leadId));
        Update update = new Update();
        update.set("Buyer_Mobile_Number", updateLeadRequest.getBuyer_Mobile_Number());
        return mongoTemplate.findAndModify(query, update, LeadsSchema.class);
    }


}
