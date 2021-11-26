package com.cuberreality.entity.propertisesearch;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Country {

    @Field("State")
    private List<State> state;

    @Field("Country_Name")
    private String countryName;
}
