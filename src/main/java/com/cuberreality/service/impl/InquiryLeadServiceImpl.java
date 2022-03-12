package com.cuberreality.service.impl;


import com.cuberreality.entity.leads.InquiryLeadSchema;
import com.cuberreality.mapper.leads.LeadsMapper;
import com.cuberreality.repository.leads.InquiryLeadRepository;
import com.cuberreality.request.leads.*;
import com.cuberreality.response.leads.*;
import com.cuberreality.service.InquiryLeadService;
import com.cuberreality.util.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InquiryLeadServiceImpl implements InquiryLeadService {

    @Autowired
    LeadsMapper leadsMapper;

    @Autowired
    InquiryLeadRepository inquiryLeadRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private ApiClient apiClient;


    @Override
    public CreateLeadResponseModel inquiryLead(InquiryLeadModel createLeadRequest) throws Exception {

        InquiryLeadCrmRequest inquiryLeadCrmRequest = getInquiryLeadCrmRequest(createLeadRequest);

        CreateLeadResponse createLeadResponse = apiClient.post(inquiryLeadCrmRequest, CreateLeadResponse.class,"/bigin/v1/Deals"  );

        log.info("lead created inn crm");
        InquiryLeadResponse inquiryLead = apiClient.get(InquiryLeadResponse.class,"/bigin/v1/Deals/"+createLeadResponse.getData().get(0).getDetails().getId()  );

        InquiryLeadSchema inquiryLeadResponseModelToInquiryLeadsSchema = leadsMapper.getInquiryLeadResponseModelToInquiryLeadsSchema(inquiryLead.getData().get(0));
        inquiryLeadRepository.save(inquiryLeadResponseModelToInquiryLeadsSchema);
        log.info("lead saved in db");

        return createLeadResponse.getData().get(0);
    }

    @Override
    public List<GetLeadResponseModel> getLeads() throws Exception {
        return null;
    }


    @Override
    public GetInquiryLeadResponseModel getInquiryLead(String id) {
        Optional<InquiryLeadSchema> lead = inquiryLeadRepository.findById(id);

        GetInquiryLeadResponseModel getLeadResponseModel =
                leadsMapper.getInquiryLeadsSchemaToLeadResponseModel(lead.get());
        return    getLeadResponseModel;    }

    @Override
    public UpdateLeadResponse updateInquiryLead(UpdateInquiryLeadModel updateInquiryLeadModel, String id) throws Exception {

        UpdateInquiryLeadRequest updateInquiryLeadRequest = new UpdateInquiryLeadRequest();

        List<UpdateInquiryLeadModel> updateLeadModelList = new ArrayList<>();
        updateLeadModelList.add(updateInquiryLeadModel);
        updateInquiryLeadRequest.setData(updateLeadModelList);
        UpdateLeadResponse updateLeadResponse =
                apiClient.put(updateInquiryLeadRequest,
                        UpdateLeadResponse.class, "/bigin/v1/Deals/"+id);

        //  LeadsSchema lead = leadsRepository.findByCrmLeadId(updateLeadResponse.getData().get(0).getDetails().getId());

        //TODO discuss and set the modifiable fields

        update(updateInquiryLeadModel,id);
        return updateLeadResponse;
    }

    @Override
    public List<GetInquiryLeadResponseModel> getInquiryLeadByResellerId(String resellerId) {

        List<InquiryLeadSchema> leads = inquiryLeadRepository.findByReseller_ID(resellerId);

        List<GetInquiryLeadResponseModel> inquiryLeadResponseModelList = leadsMapper.inquiryLeadSchemasToLeads(leads);

        return inquiryLeadResponseModelList;
    }

    public InquiryLeadSchema update(UpdateInquiryLeadModel updateInquiryLeadModel,String leadId){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(leadId));
        Update update = new Update();
        update.set("Owner_Mobile", updateInquiryLeadModel.getOwner_Mobile());
        update.set("Owner_Name", updateInquiryLeadModel.getOwner_Name());
        update.set("Modified_Time", LocalDateTime.now().toString());
        update.set("Description", updateInquiryLeadModel.getDescription());
        update.set("Deal_Name", updateInquiryLeadModel.getDeal_Name());
        update.set("Reseller_Comments", updateInquiryLeadModel.getReseller_Comments());
        return mongoTemplate.findAndModify(query, update, InquiryLeadSchema.class);
    }


    private InquiryLeadCrmRequest getInquiryLeadCrmRequest(InquiryLeadModel inquiryLeadModel) {
        InquiryLeadCrmRequest inquiryLeadCrmRequest = new InquiryLeadCrmRequest();
        List<InquiryLeadModel> createLeadModelList = new ArrayList<>();
        createLeadModelList.add(inquiryLeadModel);
        inquiryLeadCrmRequest.setData(createLeadModelList);
        return inquiryLeadCrmRequest;
    }
}
