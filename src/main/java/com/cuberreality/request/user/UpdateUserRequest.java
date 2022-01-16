package com.cuberreality.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class UpdateUserRequest {

    @JsonProperty("Email")
    public String Email;

    @JsonProperty("name")
    public String name;

    @JsonProperty("First_Name")
    public String First_Name;
//
//    @JsonProperty("Last_Name")
//    public String Last_Name;


    @JsonProperty("Reseller_Is")
    public List<String> Reseller_Is;



}
