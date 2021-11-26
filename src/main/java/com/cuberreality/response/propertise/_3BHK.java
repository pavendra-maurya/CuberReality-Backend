package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class _3BHK {
    @JsonProperty("Type1")
    private Type1 type1;
    @JsonProperty("Type2")
    private Type2 type2;
    @JsonProperty("Type3")
    private Type3 type3;
    @JsonProperty("Type4")
    private Type4 type4;
    @JsonProperty("Type5")
    private Type5 type5;
    @JsonProperty("Type6")
    private Type6 type6;
}
