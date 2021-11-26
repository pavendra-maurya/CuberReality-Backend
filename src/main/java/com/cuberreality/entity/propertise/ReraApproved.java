package com.cuberreality.entity.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ReraApproved {
    @Field("Phase One")
    private String phaseOne;
    @Field("Phase Two")
    private String phaseTwo;
}
