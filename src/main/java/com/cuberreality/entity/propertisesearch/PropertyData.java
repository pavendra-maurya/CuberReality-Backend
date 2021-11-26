package com.cuberreality.entity.propertisesearch;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class PropertyData {

    @Field("Id")
    private String id;

    @Field("Address")
    private String address;
}
