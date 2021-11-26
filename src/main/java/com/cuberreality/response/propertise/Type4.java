package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Type4 {

    private String price;
    private String superBuiltUpArea;
    @JsonProperty("UDS")
    private String uDS;
    @JsonProperty("CarpetArea")
    private String carpetArea;
    private String plotSize;
    private String plotAreaSqft;
    @JsonProperty("Available")
    private String available;
}
