package com.cuberreality.mapper.leads;


import com.cuberreality.entity.leads.LeadsSchema;
import com.cuberreality.entity.leads.ReferLeadSchema;
import com.cuberreality.response.leads.GetLeadResponseModel;
import com.cuberreality.response.leads.GetReferLeadResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeadsMapper {



    List<GetLeadResponseModel> leadSchemasToLeads(List<LeadsSchema> leads);


    List<GetReferLeadResponseModel> referLeadSchemasToLeads(List<ReferLeadSchema> leads);



    // @Mapping(source="id",target="crmLeadId")
    @Mapping(source="leadId",target="userLeadId")
    LeadsSchema getLeadResponseModelToLeadsSchema(GetLeadResponseModel getLeadResponseModel);

   // @Mapping(source="id",target="crmLeadId")
    @Mapping(source="leadId",target="userLeadId")
    ReferLeadSchema getLeadResponseModelToReferLeadsSchema(GetLeadResponseModel getLeadResponseModel);


    @Mapping(source="leadId",target="userLeadId")
    ReferLeadSchema getReferLeadResponseModelToReferLeadsSchema(GetReferLeadResponseModel getLeadResponseModel);

    @Mapping(source="userLeadId",target="leadId")
    GetReferLeadResponseModel getReferLeadsSchemaToLeadResponseModel(ReferLeadSchema referLeadSchema);




    //@Mapping(source="id",target="id")
    @Mapping(source="userLeadId",target="leadId")
    GetLeadResponseModel leadsSchemaToGetLeadResponseModel(LeadsSchema leadsSchema);



}
