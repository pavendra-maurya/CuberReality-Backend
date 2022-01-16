package com.cuberreality.entity.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Type {
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

    @Field("imageUrls")
    private List<String> imageUrls;

}
