package com.cuberreality.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
 class Details{
    @JsonProperty("Modified_Time")
    public Date modified_Time;
    @JsonProperty("Modified_By") 
    public CreatedBy modified_By;
    @JsonProperty("Created_Time") 
    public Date created_Time;
    public String id;
    @JsonProperty("Created_By") 
    public CreatedBy created_By;
}