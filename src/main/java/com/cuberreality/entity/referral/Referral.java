package com.cuberreality.entity.referral;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Document("Referral")
@Data
public class Referral {

    @Field("_id")
    private String mongoId;

    @Field(value = "referral_code")
    private String referralCode;

    @Field(value = "referral_uuid")
    private String referralUuid;

}

