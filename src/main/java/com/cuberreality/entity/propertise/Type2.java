package com.cuberreality.entity.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Type2 {

    private String price;
    private String superBuiltUpArea;
    @Field("UDS")
    private String uDS;
    @Field("CarpetArea")
    private String carpetArea;
    private String plotSize;
    private String plotAreaSqft;
    @Field("Available")
    private String available;
}
