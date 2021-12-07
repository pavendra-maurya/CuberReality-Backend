package com.cuberreality.response.leads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
 public class Details{
    @JsonProperty("Modified_Time")
    private LocalDateTime modified_Time;
    @JsonProperty("Modified_By")
    private CreatedBy modified_By;
    @JsonProperty("Created_Time")
    private LocalDateTime created_Time;
   private String id;
    @JsonProperty("Created_By")
    private CreatedBy created_By;
}