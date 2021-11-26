package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class FloorPlansUrl {
    @JsonProperty("2BHK")
    private _2BHKUrl _2bhkUrl;

    @JsonProperty("3BHK")
    private _3BHKUrl _3bhkUrl;
}
