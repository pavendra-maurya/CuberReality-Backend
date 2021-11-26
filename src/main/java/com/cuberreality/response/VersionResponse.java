package com.cuberreality.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VersionResponse {

    @JsonProperty("version")
    private int version;

}
