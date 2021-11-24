package com.cuberreality.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Document("UserProfile")
@Data
public class UserProfilesSchema {

    @Id
    private String mongoId;

    @Field(value = "phone_number")
    private String phoneNumber;

    @Field(value = "user_uuid")
    private String userUuid;

    @Field(value = "user_type")
    private String userType;

    @Field(value = "user_occupation")
    private List<String> userOccupationList;

    @Field(value = "id")
    private String crmUserId;


}
