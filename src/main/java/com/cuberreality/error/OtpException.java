package com.cuberreality.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OtpException extends Exception {
    public OtpException(String message) {
        super(message);
    }
}
