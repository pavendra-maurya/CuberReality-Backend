package com.cuberreality.entity.user;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("VisitedProperties")
public class VisitedProperties {

    @Field(value = "_id")
    private String mongoId;

    @Field(value = "reseller_id")
    private  String resellerId;

    @Field(value = "property_id")
    private  String propertyId;

    @Field(value = "visit_count")
    private  int visitCount;


}
