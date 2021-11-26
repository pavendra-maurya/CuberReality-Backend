package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class _2BHKUrl {
    @JsonProperty("Type1")
    private List<String> type1;
    @JsonProperty("Type2")
    private List<String> type2;
    @JsonProperty("Type3")
    private List<String> type3;
    @JsonProperty("Type4")
    private List<String> type4;
    @JsonProperty("Type5")
    private List<String> type5;
    @JsonProperty("Type6")
    private List<String> type6;
}
