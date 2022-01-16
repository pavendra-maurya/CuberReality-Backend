package com.cuberreality.entity.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class UnitSpecification{
    public String facing;
    public String unitStatus;
    public String unitPhotos;
    public String ownershipStatus;
    public String water;
    public String individualUnitFeatures;
    public String noOfBathroom;
    public String noOfBalconies;
    public String utilityArea;
    public String superBuiltUpArea;
    public String noOfFloor;
    public String carpetArea;
    public String noOfCarParking;
    public String miscRooms;
    @Field("Furnishing")
    public String Furnishing;
    public String unitInFloor;
    public String unitInTower;
    public String plotDimession;
    public String area;
}