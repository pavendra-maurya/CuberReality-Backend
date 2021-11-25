package com.cuberreality.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsApiResponse {

    @JsonProperty(value = "Mobile")
    private String phoneNumber;

    @JsonProperty(value = "Full_Name")
    private String userName;

    @JsonProperty("user_uuid")
    private String userUuid;

    @JsonProperty(value = "PAN_Number")
    private String PANNumber;

    @JsonProperty(value = "User_Status")
    private String userStatus;

    @JsonProperty(value = "Client_ID")
    private String clientID;

    @JsonProperty(value = "Wallet")
    private int Wallet;

    @JsonProperty(value = "Total_Earned")
    private int totalEarned;

    @JsonProperty(value = "User_Type")
    private List<String> userType;

    @JsonProperty(value = "Reseller_Is")
    private List<String> userOccupationList;

    @JsonProperty(value = "id")
    private String crmUserId;


}
