package com.cuberreality.response.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class FloorPlansUrl {
    @Field("2BHK")
    private _2BHKUrl _2bhkUrl;

    @Field("3BHK")
    private _3BHKUrl _3bhkUrl;
}
