package com.cuberreality.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class RecordAlreadyExistException extends RuntimeException
{
    public RecordAlreadyExistException(String message) {

        super(message);
    }
}