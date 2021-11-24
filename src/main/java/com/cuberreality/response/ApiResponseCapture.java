package com.cuberreality.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponseCapture<T> {

    @JsonProperty("data")
    private List<T> data;
}
