package com.cuberreality.entity.user;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.cuberreality.constant.Schema.VISITED_PROPERTIES;

@Data
@Document(VISITED_PROPERTIES)
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
