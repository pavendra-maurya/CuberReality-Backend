package com.cuberreality.service.impl;

import com.cuberreality.entity.leads.LeadsSchema;
import com.cuberreality.entity.leads.ReferLeadSchema;
import com.cuberreality.mapper.leads.LeadsMapper;
import com.cuberreality.repository.leads.LeadsRepository;
import com.cuberreality.repository.leads.ReferLeadRepository;
import com.cuberreality.request.leads.*;
import com.cuberreality.response.leads.*;
import com.cuberreality.service.LeadService;
import com.cuberreality.util.ApiClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LeadServiceImpl implements LeadService {


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
    public CreateLeadResponseModel createLead(CreateLeadModel createLeadRequest) throws Exception {

        CreateLeadRequest crmCreateLeadRequest = getCrmLeadRequest(createLeadRequest);


        CreateLeadResponse  createLeadResponse = apiClient.post(crmCreateLeadRequest, CreateLeadResponse.class,"/bigin/v1/Deals"  );

        log.info("lead created in crm");
        GetLeadResponse  lead = apiClient.get( GetLeadResponse.class,"/bigin/v1/Deals/"+createLeadResponse.getData().get(0).getDetails().getId()  );

        LinkProductLeadRequest linkProductLeadRequest = createLinkProductLeadRequest(createLeadRequest.getCrmPropertyId());

        String url="/bigin/v1/Deals/"+lead.getData().get(0).getId()+"/Products";

        LinkLeadResponse linkLeadResponse = apiClient.put(linkProductLeadRequest, LinkLeadResponse.class, url );
        log.info("lead linked with product in crm");

        LeadsSchema leadResponseModelToLeadsSchema = leadsMapper.getLeadResponseModelToLeadsSchema(lead.getData().get(0));
        leadResponseModelToLeadsSchema.setUserPropertyId(createLeadRequest.getProperty_ID());
        leadResponseModelToLeadsSchema.setProperty_ID(createLeadRequest.getCrmPropertyId());
        leadsRepository.save(leadResponseModelToLeadsSchema);
        log.info("lead saved in db");

        return createLeadResponse.getData().get(0);
    }


    @Override
    public CreateLeadResponseModel referLead(ReferLeadModel createLeadRequest) throws Exception {

        ReferLeadCrmRequest crmReferLeadRequest = getReferLeadCrmRequest(createLeadRequest);


        CreateLeadResponse  createLeadResponse = apiClient.post(crmReferLeadRequest, CreateLeadResponse.class,"/bigin/v1/Deals"  );

        log.info("lead created inn crm");
        GetLeadResponse  lead = apiClient.get( GetLeadResponse.class,"/bigin/v1/Deals/"+createLeadResponse.getData().get(0).getDetails().getId()  );

//        LinkProductLeadRequest linkProductLeadRequest = createLinkProductLeadRequest(createLeadRequest);
//
//        String url="/bigin/v1/Deals/"+lead.getData().get(0).getId()+"/Products";
//
//        LinkLeadResponse linkLeadResponse = apiClient.put(linkProductLeadRequest, LinkLeadResponse.class, url );
//        log.info("lead linked with product in crm");

        ReferLeadSchema leadResponseModelToReferLeadsSchema = leadsMapper.getLeadResponseModelToReferLeadsSchema(lead.getData().get(0));
      //  leadResponseModelToReferLeadsSchema.setUserPropertyId(createLeadRequest.getUserPropertyId());
        referLeadRepository.save(leadResponseModelToReferLeadsSchema);
        log.info("lead saved in db");

        return createLeadResponse.getData().get(0);
    }

    @Override
    public Object getReferLead(String id) {
        return null;
    }


    private CreateLeadRequest getCrmLeadRequest(CreateLeadModel createLeadRequest) {
        CreateLeadRequest crmCreateLeadRequest = new CreateLeadRequest();
        List<CreateLeadModel> createLeadModelList = new ArrayList<>();
        createLeadModelList.add(createLeadRequest);
        crmCreateLeadRequest.setData(createLeadModelList);
        return crmCreateLeadRequest;
    }

    private ReferLeadCrmRequest getReferLeadCrmRequest(ReferLeadModel referLeadModel) {
        ReferLeadCrmRequest crmReferLeadRequest = new ReferLeadCrmRequest();
        List<ReferLeadModel> createLeadModelList = new ArrayList<>();
        createLeadModelList.add(referLeadModel);
        crmReferLeadRequest.setData(createLeadModelList);
        return crmReferLeadRequest;
    }



    private LinkProductLeadRequest createLinkProductLeadRequest(String createLeadModel) {
        LinkProductLeadRequest linkProductLeadRequest = new LinkProductLeadRequest();
        LeadProductPojo leadProductPojo = new LeadProductPojo();
        leadProductPojo.setId(createLeadModel);

        List<LeadProductPojo> productPojos = new ArrayList<>();
        productPojos.add(leadProductPojo);
        linkProductLeadRequest.setData(productPojos);
        return linkProductLeadRequest;
    }








    @Override
    public GetLeadResponseModel getLead(String leadId) throws Exception {

        Optional<LeadsSchema> lead = leadsRepository.findById(leadId);

        GetLeadResponseModel getLeadResponseModel = leadsMapper.leadsSchemaToGetLeadResponseModel(lead.get());

        return    getLeadResponseModel;

    }





    @Override
    public List<GetLeadResponseModel> getLeads()  {

        List<LeadsSchema> leadsSchemas = leadsRepository.findAll();


        sortLeadsByCreatedTime(leadsSchemas);

        List<GetLeadResponseModel> getLeadResponseModels = leadsMapper.leadSchemasToLeads(leadsSchemas);

        return getLeadResponseModels;



    }

    private void sortLeadsByCreatedTime(List<LeadsSchema> leadsSchemas) {
        Collections.sort(leadsSchemas, (o1, o2) -> {
    String input1 = o1.getCreated_Time();
    Instant output1 = ZonedDateTime.parse(input1).toInstant();
            String input2 = o2.getCreated_Time();
            Instant output2 = ZonedDateTime.parse(input2).toInstant();

            if(output1.toEpochMilli()-output2.toEpochMilli()==0){
                return 0;
            }else if(output1.toEpochMilli()-output2.toEpochMilli()>0){
                return -1;
            }else{
                return 1;
            }
        });
    }


    public int findLeadsCountByReseller(String resellerId)  {

        List<LeadsSchema> leadsSchemas = leadsRepository.findByReseller_ID(resellerId);

   return leadsSchemas.size();


    }

    public List<GetLeadResponseModel> findLeadsByReseller(String resellerId)  {

        List<LeadsSchema> leadsSchemas = leadsRepository.findByReseller_ID(resellerId);
        sortLeadsByCreatedTime(leadsSchemas);

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


      //  LeadsSchema lead = leadsRepository.findByCrmLeadId(updateLeadResponse.getData().get(0).getDetails().getId());


        //TODO discuss and set the modifiable fields


        update(updateLeadRequest,leadId);
        return updateLeadResponse;
    }




    public LeadsSchema update(UpdateLeadModel updateLeadRequest,String leadId){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(leadId));
        Update update = new Update();
        update.set("Buyer_Mobile_Number", updateLeadRequest.getBuyer_Mobile_Number());
        update.set("Buyer_Name", updateLeadRequest.getBuyer_Name());
        update.set("Description", updateLeadRequest.getDescription());
        update.set("Deal_Name", updateLeadRequest.getDeal_Name());
        update.set("Reseller_Comments", updateLeadRequest.getReseller_Comments());

        return mongoTemplate.findAndModify(query, update, LeadsSchema.class);
    }


}
