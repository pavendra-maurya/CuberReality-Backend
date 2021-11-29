package com.cuberreality.response.login;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SignUpResponse {

    private String message;

    private int statusCode;
}
