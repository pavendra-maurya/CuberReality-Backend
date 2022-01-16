package com.cuberreality.response.user;


import com.cuberreality.response.leads.Details;
import lombok.Data;

@Data
public class UpdateUserDetailsResponse {
    private String code;
    private Details details;
    private String message;
    private String status;

}
