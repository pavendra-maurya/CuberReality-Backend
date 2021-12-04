package com.cuberreality.mapper.leads;


import com.cuberreality.entity.leads.LeadsSchema;
import com.cuberreality.response.leads.GetLeadResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeadsMapper {



    List<GetLeadResponseModel> leadSchemasToLeads(List<LeadsSchema> leads);


    @Mapping(source="id",target="leadId")
    LeadsSchema getLeadResponseModelToLeadsSchema(GetLeadResponseModel getLeadResponseModel);




    @Mapping(source="leadId",target="id")
    GetLeadResponseModel leadsSchemaToGetLeadResponseModel(LeadsSchema leadsSchema);



}
