package com.cuberreality.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VersionResponse {

    @JsonProperty("soft_update_version")
    private int softUpdateVersion;

    @JsonProperty("hard_update_version")
    private int hardUpdateVersion;



}
