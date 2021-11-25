package com.cuberreality.request.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OtpRequest {

	  @NotNull(message = "Mobile Number can't  be null")
	  @JsonProperty("mobile_number")
	  private String phoneNumber;
	  
	  @NotNull(message = "Otp can't  be null")
	  @JsonProperty("otp")
	  private String otp;
	  	
}
