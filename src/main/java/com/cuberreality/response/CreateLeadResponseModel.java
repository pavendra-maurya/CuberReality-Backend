package com.cuberreality.response;

import lombok.Data;

@Data
public class CreateLeadResponseModel{
    public String code;
    public Details details;
    public String message;
    public String status;
}