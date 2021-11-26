package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class FloorPlan {
    @JsonProperty("2BHK")
    private com.cuberreality.response.propertise._2BHK _2BHK;

    @JsonProperty("3BHK")
    private com.cuberreality.response.propertise._3BHK _3BHK;
}
