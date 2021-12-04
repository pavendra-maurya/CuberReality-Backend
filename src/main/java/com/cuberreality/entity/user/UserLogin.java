package com.cuberreality.entity.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document("UserLogin")
@Data
public class UserLogin {

    @Id
    private String id;

    @Field(value = "phone_number")
    private String phoneNumber;

    @Field(value = "user_uuid")
    private String userUuid;

    @Field(value = "active")
    private Boolean active;

    @Field(value = "device_token")
    private String deviceToken;

    @Field(value = "user_type")
    private String userType;

    @Field(value = "referral_code")
    private String referralCode;

    @Field(value = "user_registered")
    private boolean userRegistered;

    @Field(value = "user_created_date")
    private LocalDateTime userCreatedDate;

    @Field(value = "user_updated_date")
    private LocalDateTime userUpdatedDate;

    @Field(value = "last_login_date")
    private LocalDateTime lastLoginDate;
}
