package com.cuberreality.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class State {

    @Field("City")
    private List<City> city;

    @Field("State_Name")
    private String stateName;
}
