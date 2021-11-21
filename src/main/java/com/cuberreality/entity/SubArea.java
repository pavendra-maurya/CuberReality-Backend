package com.cuberreality.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class SubArea {

    @Field("Area")
    private List<Area> area;

    @Field("Sub_Area_Name")
    private String subAreaName;

}
