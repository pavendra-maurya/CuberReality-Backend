package com.cuberreality.entity.user;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

import static com.cuberreality.constant.Schema.USER_PROFILE_SCHEMA;


@Document(USER_PROFILE_SCHEMA)
@Data
public class UserProfilesSchema {

    @Field("_id")
    private String mongoId;

    @Field(value = "Mobile")
    private String phoneNumber;

    @Field(value = "First_Name")
    private String userName;

    @Field(value = "user_uuid")
    private String userUuid;

    @Field(value = "PAN_Number")
    private String PANNumber;

    @Field(value = "User_Status")
    private String userStatus;

    @Field(value = "Client_ID")
    private String clientID;

    @Field(value = "Wallet")
    private int Wallet;

    @Field(value = "Total_Earned")
    private int totalEarned;

    @Field(value = "Email")
    private String Email;

    @Field(value = "user_type")
    private List<String> userType;

    @Field(value = "Reseller_Is")
    private List<String> userOccupationList;

    @Field(value = "id")
    private String crmUserId;

    @Field(value = "referral_code")
    private String referralCode;

    @Field(value = "is_referral_eligible_cashback")
    private boolean isReferralEligibleCashback;


}

