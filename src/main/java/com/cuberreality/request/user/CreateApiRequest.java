package com.cuberreality.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateApiRequest <T>{

    @JsonProperty(value = "data")
    private List<T> createRequest;
}
