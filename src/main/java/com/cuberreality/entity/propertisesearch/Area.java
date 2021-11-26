package com.cuberreality.entity.propertisesearch;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Area {
    @Field("Property_Data")
    private List<PropertyData> propertyData;

    @Field("Area_Name")
    private String areaName;
}
