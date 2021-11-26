package com.cuberreality.entity.propertisesearch;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public  class City {

    @Field("City_Name")
    private String cityName;

    @Field("Sub_Area")
    private List<SubArea> subArea;
}
