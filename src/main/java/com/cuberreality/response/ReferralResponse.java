package com.cuberreality.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class ReferralResponse {

    private String referralCode;

    private String referralId;
}
