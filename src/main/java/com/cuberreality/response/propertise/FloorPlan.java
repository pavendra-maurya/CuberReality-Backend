package com.cuberreality.response.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class FloorPlan {
    @Field("2BHK")
    private com.cuberreality.response.propertise._2BHK _2BHK;

    @Field("3BHK")
    private com.cuberreality.response.propertise._3BHK _3BHK;
}
