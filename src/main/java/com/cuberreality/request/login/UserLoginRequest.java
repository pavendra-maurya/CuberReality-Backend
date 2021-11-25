package com.cuberreality.request.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserLoginRequest {

	  @NotNull(message = "mobile_number can't  be null")
	  @JsonProperty("mobile_number")
	  private String phoneNumber;
  
}
