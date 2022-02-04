package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PropertiesSearchResponse {

    private List<PropertiesSearchDetailsAppResponse> regularProperties;

    private List<PropertiesSearchDetailsAppResponse> focusedProperties;
}
