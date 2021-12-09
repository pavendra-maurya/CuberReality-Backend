package com.cuberreality.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {

    @JsonProperty("Email")
    public String email;

    @JsonProperty("User_Type")
    public List<String> userType;

    @JsonProperty("First_Name")
    public String firstName;

    @JsonProperty("Reseller_Is")
    public List<String> resellerIs;

    @JsonProperty("Mobile")
    public String mobile;

    @JsonProperty("User_Status")
    public String userStatus;

    @JsonProperty("Last_Name")
    public String lastName;

    @JsonProperty("PAN_Number")
    public Object pANNumber;
}
