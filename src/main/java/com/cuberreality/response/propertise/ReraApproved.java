package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ReraApproved {

    @JsonProperty("Phase One")
    private String phaseOne;

    @JsonProperty("Phase Two")
    private String phaseTwo;
}
